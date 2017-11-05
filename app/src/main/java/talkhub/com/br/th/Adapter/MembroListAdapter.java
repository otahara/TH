package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import talkhub.com.br.th.Entities.Usuario;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.MembroViewHolder;

/**
 * Created by ferna on 04/11/2017.
 */

public class MembroListAdapter extends RecyclerView.Adapter {

    private List<Usuario> usuarios;
    private Context context;
    private String idEquipe;


    public MembroListAdapter(List<Usuario> usuarios, Context context, String idEquipe) {
        this.usuarios = usuarios;
        this.context = context;
        this.idEquipe = idEquipe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.row_membro_list,
                parent, false);

        final MembroViewHolder membroViewHolder = new MembroViewHolder(view);


        return membroViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MembroViewHolder membroViewHolder = (MembroViewHolder) holder;

        Usuario usuario = usuarios.get(position);
        membroViewHolder.mNomeMembro.setText(usuario.getNome().toString());
        membroViewHolder.mSobreNomeMembro.setText(usuario.getSobrenome().toString());
        membroViewHolder.mEmailMembro.setText(usuario.getEmail().toString());
        membroViewHolder.mNomeReferenciaMembro.setText(usuario.getNomeReferenciaUsuario().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }

        });



    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
