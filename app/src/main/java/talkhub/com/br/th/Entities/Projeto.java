package talkhub.com.br.th.Entities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by ferna on 26/08/2017.
 */
public class Projeto {

    private String id;
    private String nome;
    private String descricao;
    private String usuarioCriador;
    private String idEquipe;

    public Projeto() {
    }

    public Projeto(String id, String nome, String descricao, String usuarioCriador,
                    List<String> membros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuarioCriador = usuarioCriador;
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

    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(String usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }


    public void novoProjeto(final String idEquipe, final Usuario usuario) {
        final DatabaseReference mRefProjeto = FirebaseDatabase.getInstance().getReference().child("projetos");
        final DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        final DatabaseReference mRefEquipe = FirebaseDatabase.getInstance().getReference().child("equipes");
        //Para pegar dados do usuário logado
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String emailUsuarioLogado = mAuth.getCurrentUser().getEmail().toString();
        Query query = mRefUsuario.orderByChild("email").equalTo(emailUsuarioLogado);

        //É necessária a criação dessas strings para que o nome do projeto possa ser usado dentro da
        //async task na query
        final String nomeProjeto = this.nome;
        final String descProjeto = this.descricao;


        //Busca o id do usuário logado e então embeda a nova equipe no usuário e também em equipes
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String idUsuarioLogado = item.getKey();
                    //Cria um id para o projeto
                    String idProjeto = mRefProjeto.push().getKey();

                    //Embeda no usuário logado, o id e o nome do novo projeto, id da equipe e nome da equipe
                    mRefUsuario.child(idUsuarioLogado).child("projetos").child(idProjeto).child("nome").setValue(nomeProjeto);
                    mRefUsuario.child(idUsuarioLogado).child("projetos").child(idProjeto).child("descricao").setValue(descProjeto);
                    mRefUsuario.child(idUsuarioLogado).child("projetos").child(idProjeto).child("idEquipe").setValue(idEquipe);

                    //Embeda na equipe o id e o nome do novo projeto
                    mRefEquipe.child(idEquipe).child("projetos").child(idProjeto).child("nome")
                            .setValue(nomeProjeto);
                    mRefEquipe.child(idEquipe).child("projetos").child(idProjeto).child("descricao")
                            .setValue(descProjeto);

                    mRefEquipe.child(idEquipe).child("projetos").child(idProjeto).child("membros").child(idUsuarioLogado).setValue(usuario);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


        public void novoMembro(String  idEquipe,  Usuario usuario){


            //Embeda o usuário no projeto
            DatabaseReference mRefProjeto = FirebaseDatabase.getInstance().getReference()
                    .child("equipes").child(idEquipe).child("projetos").child(this.id).child("membros").child(usuario.getId());
            mRefProjeto.setValue(usuario);

            //Embeda o projeto no usuário
        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference()
                .child("usuarios").child(usuario.getId()).child("projetos").child(this.id);
        mRefUsuario.setValue(this);



    }










    }








