package talkhub.com.br.th;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

import talkhub.com.br.th.Adapter.ProjetoListAdapter;
import talkhub.com.br.th.Entities.Projeto;

public class ProjetosEquipeActivity extends AppCompatActivity {

    private DatabaseReference mRefProjetos;
    private String idEquipe;
    private String nomeEquipe;
    private String descEquipe;
    private List<Projeto> projetos = new ArrayList<Projeto>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projetos_equipe);
        android.support.v7.widget.Toolbar tbProjetos = (android.support.v7.widget.Toolbar) findViewById(R.id.tb_projetos);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null) {
            idEquipe = bundle.getString("idEquipe");
            nomeEquipe = bundle.getString("nomeEquipe");
            descEquipe = bundle.getString("descEquipe");
        }
        tbProjetos.setTitle(nomeEquipe + " - Projetos");

        setSupportActionBar(tbProjetos);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tbProjetos.inflateMenu(R.menu.menu_equipe);


        tbProjetos.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menuCriarProjeto:
                        Intent intentCriarProjeto = new Intent(ProjetosEquipeActivity.this, NovoProjetoActivity.class);
                        intentCriarProjeto.putExtra("idEquipe", idEquipe);
                        intentCriarProjeto.putExtra("nomeEquipe", nomeEquipe);
                        intentCriarProjeto.putExtra("descEquipe", descEquipe);

                        startActivity(intentCriarProjeto);
                        return true;

                    case R.id.menuAdicionarMembros:
                        Intent intentAddMembros = new Intent(ProjetosEquipeActivity.this, AddMembroEquipeActivity.class);
                        intentAddMembros.putExtra("idEquipe", idEquipe);
                        intentAddMembros.putExtra("nomeEquipe", nomeEquipe);
                        intentAddMembros.putExtra("descEquipe", descEquipe);
//                        startActivity(intentAddMembros);
                        //Na linha abaixo eu chamo a outra acitivity enviando o número 1, este é o request code, será usado
                        //para manter o id da equipe corrente, assim quando o usuário pressionar o botão de voltar,
                        //esta actitivy irá funcionar corretamente
                        startActivityForResult(intentAddMembros, 1);
                        return true;

                    case R.id.menuInformacoesEquipe:
                        Intent intentInfoEquipe = new Intent(ProjetosEquipeActivity.this, InfoEquipeActivity.class);
                        intentInfoEquipe.putExtra("idEquipe", idEquipe);
                        intentInfoEquipe.putExtra("nomeEquipe", nomeEquipe);
                        startActivity(intentInfoEquipe);
                        return true;


                    default:
                        return false;


                }

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleProjetos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final ProjetoListAdapter projetoListAdapter = new ProjetoListAdapter(projetos, this);
        recyclerView.setAdapter(projetoListAdapter);



        mRefProjetos = FirebaseDatabase.getInstance().getReference().child("equipes")
                .child(idEquipe).child("projetos");
        Query query =  mRefProjetos;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projetos.clear();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Projeto projeto = new Projeto();
                    if(item.hasChild("nome") && item.hasChild("descricao")) {
                        projeto.setId(item.getKey());
                        projeto.setNome(item.child("nome").getValue().toString());
                        projeto.setDescricao(item.child("descricao").getValue().toString());
                        projetos.add(projeto);
                        projetoListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_equipe, menu);
        return true;
    }


}
