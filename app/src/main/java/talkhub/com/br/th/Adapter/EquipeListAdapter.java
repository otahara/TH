package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import talkhub.com.br.th.Entities.Equipe;
import talkhub.com.br.th.ProjetosEquipeActivity;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.EquipeViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

public class EquipeListAdapter extends RecyclerView.Adapter {
//    private List<HashMap<String, String>> equipes;
    private  List<Equipe> equipes;
    private Context context;

    //Esta string deve ser usada quando precisar do id da Equipe que da view
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


//        HashMap<String, String> equipe = equipes.get(position);
        Equipe equipe = equipes.get(position);
//        equipeViewHolder.mNomeEquipe.setText(equipe.values().toString().replaceAll("\\p{P}",""));
        equipeViewHolder.mNomeEquipe.setText(equipe.getNome());
        equipeViewHolder.mDescEquipe.setText(equipe.getDescricao());
        holder.itemView.setTag(equipe.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProjetosEquipeActivity.class);
                intent.putExtra("idEquipe", view.getTag().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }
}
