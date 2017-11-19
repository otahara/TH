package talkhub.com.br.th.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import talkhub.com.br.th.R;

/**
 * Created by ferna on 31/10/2017.
 */

public class UsuarioViewHolder extends RecyclerView.ViewHolder {

    final public TextView mNomeUsuario;
    final public TextView mSobrenomeUsuario;
    final public TextView mEmailUsuario;
    final public TextView mNomeReferenciaUsuario;
    final public TextView mMembro;


    public UsuarioViewHolder(View itemView) {
        super(itemView);
        mNomeUsuario = (TextView) itemView.findViewById(R.id.tv_addMembro_nome);
        mSobrenomeUsuario = (TextView) itemView.findViewById(R.id.tv_addMembro_sobrenome);
        mEmailUsuario = (TextView) itemView.findViewById(R.id.tv_addMembro_email);
        mNomeReferenciaUsuario = (TextView) itemView.findViewById(R.id.tv_addMembro_nomeReferencia);
        mMembro = (TextView) itemView.findViewById(R.id.tv_membro);



    }
}
