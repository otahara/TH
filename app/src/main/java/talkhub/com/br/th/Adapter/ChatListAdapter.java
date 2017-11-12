package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import talkhub.com.br.th.Entities.ChatMensagem;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.ChatViewHolder;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter {


    private List<ChatMensagem> mensagens;
    private Context context;
    private String idUsuario;


    public ChatListAdapter(List<ChatMensagem> mensagens, Context context, String idUsuario) {
        this.mensagens = mensagens;
        this.context = context;
        this.idUsuario = idUsuario;

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
        chatViewHolder.mBubbleMsg.setText(chatMensagem.getTexto());
        chatViewHolder.mNomeUsuario.setText(chatMensagem.getNomeUsuario() + " " + chatMensagem.getSobrenomeUsuario());

        //Verifica se a mensagem é do usuário logado, caso seja, o balão da mensagem é movido para a direita, caso não, ele fica na esquerda
        if(this.idUsuario.equals(chatMensagem.getIdUsuario())) {
            chatViewHolder.mLLayoutMsg.setGravity(Gravity.RIGHT);
        } else{
            chatViewHolder.mLLayoutMsg.setGravity(Gravity.LEFT);

        }

    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
