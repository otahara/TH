package talkhub.com.br.th.Entities;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import talkhub.com.br.th.LoginActivity;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatMensagem {

    private String texto;
    private String id;
    private String idUsuario;
    private String nomeUsuario;
    private String sobrenomeUsuario;
    private long horaMsg;




    public ChatMensagem(String texto, Usuario usuario) {
        this.texto = texto;
        this.idUsuario = usuario.getId();
        this.nomeUsuario = usuario.getNome();
        this.sobrenomeUsuario = usuario.getSobrenome();

        horaMsg = new Date().getTime();
    }


    public ChatMensagem() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSobrenomeUsuario() {
        return sobrenomeUsuario;
    }

    public void setSobrenomeUsuario(String sobrenomeUsuario) {
        this.sobrenomeUsuario = sobrenomeUsuario;
    }

    public long getHoraMsg() {
        return horaMsg;
    }

    public void setHoraMsg(long horaMsg) {
        this.horaMsg = horaMsg;
    }

    public void novaMensagemProjeto(String idProjeto, String idEquipe){

        DatabaseReference mRefEnviaMsg = FirebaseDatabase.getInstance().getReference();



        mRefEnviaMsg.child("mensagens").child("mensagens_projeto").child(idProjeto).push().setValue(this);


    }

    public void novaMensagemEquipe(String idEquipe){

        //Insere nova mensagem no firebase
        DatabaseReference mRefEnviaMsg = FirebaseDatabase.getInstance().getReference();
        mRefEnviaMsg.child("mensagens").child("mensagens_equipe").child(idEquipe).push().setValue(this);

        //Insere usuários pendentes para leitura da mensagem
            //Referencia do nó que irá receber os usuários que devem ler a mensagem
        final DatabaseReference mRefMsgPendente = FirebaseDatabase.getInstance().getReference()
                .child("mensagens").child("mensagens_equipe").child(idEquipe).child("leitura_usuarios_pendentes");

        //Referencia que irá pegar os usuários da equipe e inserir em uma lista, esta será usada para inserir os dados em usuários pendentes
        DatabaseReference mRefUsuariosEquipe = FirebaseDatabase.getInstance().getReference()
                .child("equipes").child(idEquipe).child("membros");
        mRefUsuariosEquipe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    //Coloca o usuário lido como pendente
                    if(!item.getKey().equals(LoginActivity.idUsuario))
                        mRefMsgPendente.child(item.getKey()).setValue(item.getValue().toString());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }




}
