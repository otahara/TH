package talkhub.com.br.th.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ferna on 26/08/2017.
 */
public class Projeto {

    private String nome;
    private String descricao;
    private String usuarioCriador;
    private List<String> administradores;
    private List<String> membros;

    public Projeto(String nome, String descricao,
                   String usuarioCriador,
                   List<String> administradores,
                   List<String> membros) {
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioCriador = usuarioCriador;
        this.administradores = administradores;
        this.membros  = membros;
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

    public String getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(String usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    public List<String> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<String> administradores) {
        this.administradores = administradores;
    }

    public List<String> getMembros() {
        return membros;
    }

    public void setMembros(List<String> membros) {
        this.membros = membros;
    }

    public void novoProjeto(String idUsuarioLogado, String idEquipe){
        DatabaseReference mRefProjeto = FirebaseDatabase.getInstance().getReference().child("projetos");
        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes");


        //Cria um id para o projeto
        String idProjeto = mRefProjeto.push().getKey();
        mRefProjeto.child(idProjeto).setValue(this);

        //Embeda no usuário logado, o id e o nome do novo projeto
        mRefUsuario.child(idUsuarioLogado).child("projetos").child(idProjeto).setValue(this.nome);

        //Embeda na equipe o id e o nome do novo projeto
        mRefEquipe.child(idEquipe).child("projetos").child(idProjeto).setValue(this.nome);




    }







}
