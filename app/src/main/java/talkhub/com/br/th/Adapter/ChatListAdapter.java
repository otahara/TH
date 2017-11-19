package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import talkhub.com.br.th.CadastroActivity;
import talkhub.com.br.th.Entities.ChatMensagem;
import talkhub.com.br.th.LoginActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.ChatViewHolder;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter {


    private List<ChatMensagem> mensagens;
    private Context context;
    private String idUsuario = LoginActivity.idUsuario;


    public ChatListAdapter(List<ChatMensagem> mensagens, Context context) {
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

        ChatMensagem chatMensagem = mensagens.get(position);
        chatViewHolder.mMensagem.setText(chatMensagem.getTexto());

        //Pega o float da data e transforma em uma string do tipo data
        Date date = new Date(chatMensagem.getHoraMsg());
        String dataFormatada = new SimpleDateFormat("dd-MM-yy HH:mm").format(date);

        chatViewHolder.mTimeMsg.setText(dataFormatada);


        //Verifica se a mensagem é do usuário logado, caso seja, o balão da mensagem é movido para a direita, caso não, ele fica na esquerda
        if(this.idUsuario.equals(chatMensagem.getIdUsuario())) {
            chatViewHolder.mMsgEu.setVisibility(View.VISIBLE);
            chatViewHolder.mMsgTerceiro.setVisibility(View.GONE);
            chatViewHolder.mLayout.setBackgroundColor(Color.rgb(133, 255,161));
            chatViewHolder.mNomeUsuario.setText("Eu");


        } else{
            chatViewHolder.mMsgEu.setVisibility(View.GONE);
            chatViewHolder.mMsgTerceiro.setVisibility(View.VISIBLE);
            chatViewHolder.mNomeUsuario.setText(chatMensagem.getNomeUsuario());


        }

    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
