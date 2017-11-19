package talkhub.com.br.th.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import talkhub.com.br.th.Entities.Equipe;
import talkhub.com.br.th.LoginActivity;
import talkhub.com.br.th.ProjetosEquipeActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.EquipeViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

public class EquipeListAdapter extends RecyclerView.Adapter {
    private  List<Equipe> equipes;
    private Context context;

    private String idEquipe;

    public EquipeListAdapter(List<Equipe> equipes, Context context) {
        this.equipes = equipes;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_equipe_list, parent, false);

        final EquipeViewHolder equipeViewHolder = new EquipeViewHolder(view);





        return equipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EquipeViewHolder equipeViewHolder = (EquipeViewHolder) holder;


        final Equipe equipe = equipes.get(position);
        equipeViewHolder.mNomeEquipe.setText(equipe.getNome());
        equipeViewHolder.mDescEquipe.setText(equipe.getDescricao());
        holder.itemView.setTag(equipe.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProjetosEquipeActivity.class);
                intent.putExtra("idEquipe", view.getTag().toString());
                intent.putExtra("nomeEquipe", equipe.getNome());
                intent.putExtra("descEquipe", equipe.getDescricao());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Excluir Equipe " + equipe.getNome())
                        .setMessage("Você deseja realmente excluir esta equipe?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

                                mRef.child("equipes").child(equipe.getId()).removeValue();
                                mRef.child("usuarios").child(LoginActivity.idUsuario).child("equipes").child(equipe.getId()).removeValue();



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
        return equipes.size();
    }
}
