package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.EquipeViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

public class EquipeListAdapter extends RecyclerView.Adapter {
    private List<HashMap<String, String>> equipes;
    private Context context;

    public EquipeListAdapter(List<HashMap<String, String>> equipes, Context context) {
        this.equipes = equipes;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_equipe_list, parent, false);

        EquipeViewHolder equipeViewHolder = new EquipeViewHolder(view);

        return equipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EquipeViewHolder equipeViewHolder = (EquipeViewHolder) holder;

        HashMap<String, String> equipe = equipes.get(position);

        equipeViewHolder.mNomeEquipe.setText(equipe.values().toString());
    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }
}
