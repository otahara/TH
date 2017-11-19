package talkhub.com.br.th.Entities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import talkhub.com.br.th.MainActivity;
import talkhub.com.br.th.ProjetosEquipeActivity;

/**
 * Created by ferna on 26/08/2017.
 */
public class Equipe {

    private String id;
    private String nome;
    private String descricao;
    private Usuario usuarioCriador;




    public Equipe() {
    }

    public Equipe(String id, String nome, String descricao,
                  Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioCriador = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(Usuario usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    public void novaEquipe(Usuario usuario){

        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes");
        DatabaseReference
                mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");



        //Preenche o hashmap "membros" inserindo o usuário que está logado, pois este usuário já vai ser considerado
        //um membro do projeto



        //Cria um id para a equipe
        this.id = mRefEquipe.push().getKey();
        //Adiciona a nova equipe no documento de equipes
        mRefEquipe.child(this.id).setValue(this);
        mRefEquipe.child(this.id).child("membros").child(usuario.getId()).setValue(usuario);

        //Embeda no documento "usuario" o id e o nome da equipe
        mRefUsuario.child(usuario.getId()).child("equipes").child(this.id).child("nome").setValue(this.nome);
        mRefUsuario.child(usuario.getId()).child("equipes").child(this.id).child("descricao").setValue(this.descricao);



    }



    public void novoMembro(String idEquipe, String nomeEquipe, String descEquipe,
                           String emailUsuario, String idUsuario ){


        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes")
                .child(idEquipe).child("membros").child(idUsuario);
        mRefEquipe.setValue(emailUsuario);

//        Embeda no documento "usuario" o id e o nome da equipe
        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(idUsuario).child("equipes").child(idEquipe);

        mRefUsuario.child("nome").setValue(nomeEquipe);
        mRefUsuario.child("descricao").setValue(descEquipe);

    }

    public void darPrivilegioAdm(String idEquipe, String idUsuario, String emailUsuario){

        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes")
                .child(idEquipe).child("administradores").child(idUsuario);

        mRefEquipe.setValue(emailUsuario);

        mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes").child(idEquipe).child("membros")
                .child(idUsuario);

        mRefEquipe.removeValue();




    }







}
