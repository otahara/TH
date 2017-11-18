package talkhub.com.br.th.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import talkhub.com.br.th.AddMembroEquipeActivity;
import talkhub.com.br.th.Entities.Equipe;
import talkhub.com.br.th.Entities.Projeto;
import talkhub.com.br.th.Entities.Usuario;
import talkhub.com.br.th.ProjetosEquipeActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.UsuarioViewHolder;

/**
 * Created by ferna on 18/11/2017.
 */

public class InserirUsuarioProjetoListAdapter extends RecyclerView.Adapter {


    private List<Usuario> usuarios ;
    private Context context;
    private String idEquipe;
    private String idProjeto;
    private String nomeProjeto;
    private String descProjeto;





    public InserirUsuarioProjetoListAdapter(List<Usuario> usuarios, Context context, String idEquipe, String idProjeto, String nomeProjeto, String descProjeto) {
        this.usuarios = usuarios;
        this.context = context;
        this.idEquipe =idEquipe;
        this.idProjeto = idProjeto;
        this.nomeProjeto = nomeProjeto;
        this.descProjeto = descProjeto;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.row_usuario_list, parent, false);
        final UsuarioViewHolder usuarioViewHolder  = new UsuarioViewHolder(view);

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
                                Projeto projeto = new Projeto();
                                projeto.setId(idProjeto);
                                projeto.setNome(nomeProjeto);
                                projeto.novoMembro(idEquipe, usuario);

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
