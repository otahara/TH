package talkhub.com.br.th.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;

import org.w3c.dom.Text;

import talkhub.com.br.th.R;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    final public LinearLayout mLayout;
    final public ImageView mMsgEu;
    final public ImageView mMsgTerceiro;
    final public TextView mMensagem;
    final public TextView mNomeUsuario;
    final public TextView mTimeMsg;
    final public LinearLayout mLLayoutMsg;

    public ChatViewHolder(View itemView) {
        super(itemView);

        mLayout = (LinearLayout) itemView.findViewById(R.id.layout_mensagem);
        mMsgEu = (ImageView) itemView.findViewById(R.id.msg_eu);
        mMsgTerceiro = (ImageView) itemView.findViewById(R.id.msg_terceiro);
        mMensagem = (TextView) itemView.findViewById(R.id.bbtv_mensagem);
        mNomeUsuario = (TextView) itemView.findViewById(R.id.tv_nomeUsuario_msg);
        mTimeMsg = (TextView) itemView.findViewById(R.id.tv_time_msg);
        mLLayoutMsg = (LinearLayout) itemView.findViewById(R.id.linear_layout_mensagem);

    }
}
