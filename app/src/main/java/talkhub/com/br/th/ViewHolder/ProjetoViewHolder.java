package talkhub.com.br.th.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import talkhub.com.br.th.R;

/**
 * Created by ferna on 28/10/2017.
 */

public class ProjetoViewHolder extends RecyclerView.ViewHolder {

    final public TextView mNomeProjeto;
    final public TextView mDescProjeto;

    public ProjetoViewHolder(View itemView) {
        super(itemView);
        mNomeProjeto = (TextView) itemView.findViewById(R.id.tv_nome_projeto);
        mDescProjeto = (TextView) itemView.findViewById(R.id.tv_desc_projeto);
    }
}
