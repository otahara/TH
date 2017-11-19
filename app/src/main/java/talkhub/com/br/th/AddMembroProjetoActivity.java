package talkhub.com.br.th;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import talkhub.com.br.th.Adapter.InserirUsuarioProjetoListAdapter;
import talkhub.com.br.th.Entities.Usuario;

public class AddMembroProjetoActivity extends AppCompatActivity {


    private EditText mPesquisa;
    private Button mBtnPesquisar;
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private Long membrosCount ;



    private String idProjeto;
    private String nomeProjeto;
    private String descProjeto;

    private String idEquipe;
    private String nomeEquipe;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(AddMembroProjetoActivity.this, ChatProjetoActivity.class);
                intent.putExtra("idEquipe", idEquipe);
                intent.putExtra("nomeEquipe", nomeEquipe);
                intent.putExtra("idProjeto", idProjeto);
                intent.putExtra("nomeProjeto", nomeProjeto);
                startActivity(intent);
                break;

        }
        return true;


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_projeto, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membro_projeto);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idProjeto = bundle.getString("idProjeto");
            nomeProjeto = bundle.getString("nomeProjeto");
            descProjeto = bundle.getString("descProjeto");
            idEquipe = bundle.getString("idEquipe");
            nomeEquipe = bundle.getString("nomeEquipe");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_addMembrosProjeto);
        toolbar.setTitle("Add membro em " + nomeProjeto);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        mPesquisa = (EditText) findViewById(R.id.et_pesquisa_projeto);
        mBtnPesquisar = (Button) findViewById(R.id.bt_pesquisar_projeto);







        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_pesquisa_usuarios_projeto);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final InserirUsuarioProjetoListAdapter usuarioListAdapter = new InserirUsuarioProjetoListAdapter(usuarios, this
                , idEquipe, idProjeto, nomeProjeto, descProjeto);

        recyclerView.setAdapter(usuarioListAdapter);
        mBtnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuarios.clear();
                String stringPesquisa = mPesquisa.getText().toString();
                pesquisaUsuarios(stringPesquisa, usuarioListAdapter);

            }
        });


    }



    public void pesquisaUsuarios(final String stringPesquisa, final InserirUsuarioProjetoListAdapter usuarioListAdapter){

        DatabaseReference mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");

        mRefUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot item : dataSnapshot.getChildren()){

                    if(item.child("nomeReferenciaUsuario").getValue().toString().contains(stringPesquisa)){
                        Usuario usuario = new Usuario();
                        usuario.setId(item.getKey());
                        usuario.setNome(item.child("nome").getValue().toString());
                        usuario.setSobrenome(item.child("sobrenome").getValue().toString());
                        usuario.setEmail(item.child("email").getValue().toString());
                        usuario.setNomeReferenciaUsuario(item.child("nomeReferenciaUsuario").getValue().toString());
                        usuarios.add(usuario);
                        usuarioListAdapter.notifyDataSetChanged();

                    }

                }
                if(usuarios.isEmpty())
                    Toast.makeText(AddMembroProjetoActivity.this, "Nenhum Usuário encontrado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
