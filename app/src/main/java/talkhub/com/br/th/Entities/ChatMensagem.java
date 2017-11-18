package talkhub.com.br.th.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

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

    public void novaMensagem(String idProjeto, String idEquipe){

        DatabaseReference mRefEnviaMsg = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRefMensagensPendente = FirebaseDatabase.getInstance().getReference().child("mensagens")
                .child("mensagens_projeto").child("usuarios_pendente");
        DatabaseReference mRefMembrosProjeto = FirebaseDatabase.getInstance().getReference().child("equipes").
                child(idEquipe).child("projetos").child(idProjeto);

        mRefEnviaMsg.child("mensagens").child("mensagens_projeto").child(idProjeto).push().setValue(this);


    }


}
