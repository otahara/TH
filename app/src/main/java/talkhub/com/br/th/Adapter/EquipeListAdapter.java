package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.EquipeViewHolder;

/**
 * Created by gabri on 21/10/2017.
 */

public class EquipeListAdapter extends RecyclerView.Adapter<EquipeViewHolder> {
    @Override
    public EquipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View equipeView = inflater.inflate(R.layout.row_equipe_list, parent, false);
        return new EquipeViewHolder(equipeView);
    }

    @Override
    public void onBindViewHolder(EquipeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
