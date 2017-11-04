package talkhub.com.br.th.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import talkhub.com.br.th.AddMembroEquipeActivity;
import talkhub.com.br.th.Entities.Equipe;
import talkhub.com.br.th.Entities.Usuario;
import talkhub.com.br.th.ProjetosEquipeActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.UsuarioViewHolder;

/**
 * Created by ferna on 31/10/2017.
 */

public class UsuarioListAdapter extends RecyclerView.Adapter {

    private List<Usuario> usuarios;
    private Context context;
    private String idEquipe;
    private String nomeEquipe;
    private String descEquipe;

    public UsuarioListAdapter(List<Usuario> usuarios, Context context,
                              String idEquipe, String nomeEquipe, String descEquipe) {
        this.usuarios = usuarios;
        this.context = context;
        this.idEquipe = idEquipe;
        this.nomeEquipe = nomeEquipe;
        this.descEquipe = descEquipe;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.row_usuario_list,
                                                                            parent, false);

        final UsuarioViewHolder usuarioViewHolder   = new UsuarioViewHolder(view);

        return usuarioViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UsuarioViewHolder usuarioViewHolder = (UsuarioViewHolder) holder;

        final Usuario usuario = usuarios.get(position);
        usuarioViewHolder.mNomeUsuario.setText(usuario.getNome());
        usuarioViewHolder.mSobrenomeUsuario.setText(usuario.getSobrenome());
        usuarioViewHolder.mEmailUsuario.setText(usuario.getEmail());
        usuarioViewHolder.mNomeReferenciaUsuario.setText(usuario.getNomeReferenciaUsuario());

        holder.itemView.setTag(usuario.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Adicionar membro")
                        .setMessage("Deseja realmente adicionar este usuário?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Equipe equipe = new Equipe();
                                equipe.novoMembro(idEquipe, nomeEquipe, descEquipe, usuario.getEmail(), usuario.getId());

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Não vai adicionar", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();




            }
        });




    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
