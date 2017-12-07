package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import talkhub.com.br.th.Entities.MensagemChat;
import talkhub.com.br.th.LoginActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.ChatViewHolder;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter {


    private List<MensagemChat> mensagens;
    private Context context;
    private String idUsuario = LoginActivity.idUsuario;


    public ChatListAdapter(List<MensagemChat> mensagens, Context context) {
        this.mensagens = mensagens;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.row_mensagem, parent, false);

        final ChatViewHolder chatViewHolder = new ChatViewHolder(view);

        return chatViewHolder;




    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;

        MensagemChat mensagemChat = mensagens.get(position);
        chatViewHolder.mMensagem.setText(mensagemChat.getTexto());



        //Pega o float da data e transforma em uma string do tipo data
        Date date = new Date(mensagemChat.getHoraMsg());
        String dataFormatada = new SimpleDateFormat("dd-MM-yy HH:mm").format(date);

        chatViewHolder.mTimeMsg.setText(dataFormatada);


        //Verifica se a mensagem é do usuário logado, caso seja, o balão da mensagem é movido para a direita, caso não, ele fica na esquerda
        if(this.idUsuario.equals(mensagemChat.getIdUsuario())) {
            chatViewHolder.mMsgEu.setVisibility(View.VISIBLE);
            chatViewHolder.mMsgTerceiro.setVisibility(View.GONE);
            chatViewHolder. mLayout.setBackgroundColor(Color.rgb(133, 255,161));

            chatViewHolder.mNomeUsuario.setText("Eu");


        } else{
            chatViewHolder.mMsgEu.setVisibility(View.GONE);
            chatViewHolder.mMsgTerceiro.setVisibility(View.VISIBLE);
            chatViewHolder.mLayout.setBackgroundColor(Color.rgb(227, 228, 228));
            chatViewHolder.mNomeUsuario.setText(mensagemChat.getNomeUsuario());


        }

    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
