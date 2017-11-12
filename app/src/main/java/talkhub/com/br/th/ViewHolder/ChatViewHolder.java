package talkhub.com.br.th.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;

import talkhub.com.br.th.R;

/**
 * Created by ferna on 11/11/2017.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    final public BubbleTextView mBubbleMsg;
    final public TextView mNomeUsuario;
    final public TextView mTimeMsg;
    final public LinearLayout mLLayoutMsg;

    public ChatViewHolder(View itemView) {
        super(itemView);

        mBubbleMsg = (BubbleTextView) itemView.findViewById(R.id.bbtv_mensagem);
        mNomeUsuario = (TextView) itemView.findViewById(R.id.tv_nomeUsuario_msg);
        mTimeMsg = (TextView) itemView.findViewById(R.id.tv_time_msg);
        mLLayoutMsg = (LinearLayout) itemView.findViewById(R.id.linear_layout_mensagem);

    }
}
