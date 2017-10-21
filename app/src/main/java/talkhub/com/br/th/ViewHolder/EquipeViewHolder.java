package talkhub.com.br.th.ViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import talkhub.com.br.th.R;

public class EquipeViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public EquipeViewHolder(View itemView) {
        super(itemView);
        this.mTextView = (TextView) itemView.findViewById(R.id.text_model);
    }
}
