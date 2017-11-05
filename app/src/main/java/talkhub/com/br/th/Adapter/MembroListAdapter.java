package talkhub.com.br.th.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import talkhub.com.br.th.Entities.Equipe;
import talkhub.com.br.th.Entities.Usuario;
import talkhub.com.br.th.R;
import talkhub.com.br.th.ViewHolder.MembroViewHolder;

import static android.view.Gravity.CENTER;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        MembroViewHolder membroViewHolder = (MembroViewHolder) holder;

        final Usuario usuario = usuarios.get(position);
        membroViewHolder.mNomeMembro.setText(usuario.getNome().toString());
        membroViewHolder.mSobreNomeMembro.setText(usuario.getSobrenome().toString());
        membroViewHolder.mEmailMembro.setText(usuario.getEmail().toString());
        membroViewHolder.mNomeReferenciaMembro.setText(usuario.getNomeReferenciaUsuario().toString());
        //Caso o usuário que está sendo listado for um adiministrador, o textview de adm será ativado
        if(usuario.getAdministrador() == true)
            membroViewHolder.mAdministrador.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.menu_membroclick);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.menuPermissaoAdm:
//                                Toast.makeText(context, "Testando bixo", Toast.LENGTH_SHORT).show();
                                Equipe equipe = new Equipe();
                                equipe.darPrivilegioAdm(idEquipe, usuario.getId(), usuario.getEmail());
                                Toast.makeText(context, usuario.getEmail(), Toast.LENGTH_SHORT).show();


                                return true;



                        }
                        return false;




                    }
                });





            }

        });



    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
