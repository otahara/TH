package talkhub.com.br.th.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import talkhub.com.br.th.R;

/**
 * Created by ferna on 04/11/2017.
 */

public class MembroViewHolder extends RecyclerView.ViewHolder {

    final public TextView mNomeMembro;
    final public TextView mSobreNomeMembro;
    final public TextView mEmailMembro;
    final public TextView mNomeReferenciaMembro;
    final public TextView mAdministrador;


    public MembroViewHolder(View itemView) {
        super(itemView);

        mNomeMembro = (TextView) itemView.findViewById(R.id.tv_nome_membro);
        mSobreNomeMembro = (TextView) itemView.findViewById(R.id.tv_sobrenome_membro);
        mEmailMembro =  (TextView) itemView.findViewById(R.id.tv_email_membro);
        mNomeReferenciaMembro =  (TextView) itemView.findViewById(R.id.tv_nomeReferencia_membro);
        mAdministrador = (TextView) itemView.findViewById(R.id.tv_administrador);

        mAdministrador.setVisibility(View.INVISIBLE);





    }
}
