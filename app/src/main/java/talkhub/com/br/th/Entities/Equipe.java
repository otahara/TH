package talkhub.com.br.th.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ferna on 26/08/2017.
 */
public class Equipe {

    private String id;
    private String nome;
    private String descricao;
    private String usuarioCriador;
    private List<String> administradores;
    private List<String> membros;

    public Equipe() {
    }

    public Equipe(String id, String nome, String descricao,
                  String usuarioCriador, List<String> administradores, List<String> membros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioCriador = usuarioCriador;
        this.administradores = administradores;
        this.membros = membros;
    }


    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsuarioCriador(){
        return usuarioCriador;
    }

    public List<String> getAdministradores() {
        return administradores;
    }

    public List<String> getMembros() {
        return membros;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void novaEquipe(String idUsuarioLogado){

        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes");
        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");

        //Cria um id para a equipe
        String keyEquipe = mRefEquipe.push().getKey();
        //Adiciona a nova equipe no documento de equipes
        mRefEquipe.child(keyEquipe).setValue(this);

        //Embeda no documento "usuario" o id e o nome da equipe

        mRefUsuario.child(idUsuarioLogado).child("equipes").child(keyEquipe).child("nome").setValue(this.nome);
        mRefUsuario.child(idUsuarioLogado).child("equipes").child(keyEquipe).child("descricao").setValue(this.descricao);



    }

    public void novoMembro(String idEquipe, String nomeEquipe, String descEquipe,
                           String emailUsuario, String idUsuario ){


        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes")
                .child(idEquipe).child("membros").child(idUsuario);
        mRefEquipe.setValue(emailUsuario);

//        Embeda no documento "usuario" o id e o nome da equipe
        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(idUsuario).child("equipes").child(idEquipe);

        mRefUsuario.setValue(nomeEquipe, descEquipe);







    }







}
