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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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


        final UsuarioViewHolder usuarioViewHolder = (UsuarioViewHolder) holder;
        final Usuario usuario = usuarios.get(position);
        usuarioViewHolder.mNomeUsuario.setText(usuario.getNome());
        usuarioViewHolder.mSobrenomeUsuario.setText(usuario.getSobrenome());
        usuarioViewHolder.mEmailUsuario.setText(usuario.getEmail());
        usuarioViewHolder.mNomeReferenciaUsuario.setText(usuario.getNomeReferenciaUsuario());


        //O código abaixo irá verificar se o usuário já é membro do projeto
        DatabaseReference mRefMembro = FirebaseDatabase.getInstance().getReference().child("equipes").child(idEquipe)
                .child("projetos").child(idProjeto).child("membros");


        mRefMembro.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    if(item.getKey().equals(usuario.getId()) )
                        usuarioViewHolder.mMembro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.itemView.setTag(usuario.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!usuarioViewHolder.mMembro.isShown()){
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
                                    usuarioViewHolder.mMembro.setVisibility(View.VISIBLE);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Não vai adicionar", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).show();
                } else{
                    Toast.makeText(context, "Usuário já membro", Toast.LENGTH_SHORT).show();
                }






            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
