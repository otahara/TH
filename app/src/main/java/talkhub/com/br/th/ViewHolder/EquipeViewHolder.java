package talkhub.com.br.th.ViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import talkhub.com.br.th.R;

public class EquipeViewHolder extends RecyclerView.ViewHolder {

    final public TextView mNomeEquipe;

    public EquipeViewHolder(final View itemView) {
        super(itemView);
        mNomeEquipe = (TextView)itemView.findViewById(R.id.tv_nome_equipe);
    }
}
