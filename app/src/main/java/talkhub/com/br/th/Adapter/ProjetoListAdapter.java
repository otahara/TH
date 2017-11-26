package talkhub.com.br.th.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import talkhub.com.br.th.Entities.Projeto;
import talkhub.com.br.th.ChatProjetoActivity;
import talkhub.com.br.th.LoginActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.ProjetoViewHolder;

/**
 * Created by ferna on 28/10/2017.
 */

public class  ProjetoListAdapter extends RecyclerView.Adapter {

    private List<Projeto> projetos;
    private Context context;
    private String idProjeto;
    private String nomeProjeto;
    private String idEquipe;
    private String nomeEquipe;

    public ProjetoListAdapter(List<Projeto> projetos, Context context, String idEquipe, String nomeEquipe) {
        this.projetos = projetos;
        this.context = context;
        this.idEquipe = idEquipe;
        this.nomeEquipe = nomeEquipe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view  = LayoutInflater.from(context).inflate(R.layout.row_projetosequipe_list,
                parent, false);

        final ProjetoViewHolder projetoViewHolder = new ProjetoViewHolder(view);

        return projetoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ProjetoViewHolder projetoViewHolder = (ProjetoViewHolder) holder;

        final Projeto projeto = projetos.get(position);
        projetoViewHolder.mNomeProjeto.setText(projeto.getNome());
        projetoViewHolder.mDescProjeto.setText(projeto.getDescricao());

        //Verifica se o projeto possui alguma mensagem não lida pelo usuário
        DatabaseReference mRefUsuarioPendente = FirebaseDatabase.getInstance().getReference()
                .child("mensagens").child("mensagens_projeto").child(projeto.getId()).child("leitura_usuarios_pendentes");

        mRefUsuarioPendente.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(LoginActivity.idUsuario)){
                    projetoViewHolder.mAlert.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatProjetoActivity.class);
                intent.putExtra("idProjeto", projeto.getId());
                intent.putExtra("nomeProjeto", projeto.getNome());
                intent.putExtra("idEquipe", idEquipe);
                intent.putExtra("nomeEquipe", nomeEquipe);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Excluir Projeto " + projeto.getNome())
                        .setMessage("Você deseja realmente excluir este projeto?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

                                mRef.child("equipes").child(idEquipe).child("projetos").child(projeto.getId()).removeValue();
                                mRef.child("usuarios").child(LoginActivity.idUsuario).child("projetos").child(projeto.getId()).removeValue();



                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();

                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return projetos.size();
    }
}
