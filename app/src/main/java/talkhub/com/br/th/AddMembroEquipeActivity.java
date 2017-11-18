package talkhub.com.br.th;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import talkhub.com.br.th.Adapter.UsuarioListAdapter;
import talkhub.com.br.th.Entities.Usuario;

public class AddMembroEquipeActivity extends AppCompatActivity {

    private String idEquipe;
    private String nomeEquipe;
    private String descEquipe;
    private DatabaseReference mRefUsuario;
    //os objetos serão usados para fazer uma query que vai buscar todos os membros da equipe
    //asssim, quando a listagem dos usuários para inserção como membro for feita, os usuários que já são membros serão destacados
    private DatabaseReference mRefMembrosEquipe;
    private List<String> idUsuariosJaMembros = new ArrayList<String>();
    private FirebaseAuth mAuth;
    private EditText mTextoPesquisa;
    private Button mPesquisar;
    //Texto que o usuário digitou no campo
    private String stringPesquisa;

    //Infos do usuário que está logado
    String emailUsuarioLogado;
    String idUsuarioLogado;

    //Lista que guarda os usuários recebidos da pesquisa
    private List<Usuario> usuarios = new ArrayList<Usuario>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Log.d("backbutton", "cheguei");
                Intent intent = new Intent(AddMembroEquipeActivity.this, ProjetosEquipeActivity.class);
                intent.putExtra("idEquipe", idEquipe);
                intent.putExtra("nomeEquipe", nomeEquipe);
                startActivity(intent);
                break;
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membro_equipe);
        android.support.v7.widget.Toolbar tbAddMembrosEquipe = (android.support.v7.widget.Toolbar) findViewById(R.id.tb_addMembrosEquipe);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            idEquipe = bundle.getString("idEquipe");
            nomeEquipe = bundle.getString("nomeEquipe");
            descEquipe = bundle.getString("descEquipe");
        }

        tbAddMembrosEquipe.setTitle("Adicionar Membros - " + nomeEquipe);

        mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        mRefMembrosEquipe = FirebaseDatabase.getInstance().getReference().child("equipes").child(idEquipe).child("membros");


        mTextoPesquisa = (EditText) findViewById(R.id.et_pesquisa);
        mPesquisar = (Button) findViewById(R.id.bt_pesquisar);

        setSupportActionBar(tbAddMembrosEquipe);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_pesquisa_usuarios);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        final UsuarioListAdapter usuarioListAdapter = new UsuarioListAdapter(usuarios, this,
                idEquipe, nomeEquipe, descEquipe);
        recyclerView.setAdapter(usuarioListAdapter);

        mPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringPesquisa = mTextoPesquisa.getText().toString();
                usuarios.clear();

                Query query = mRefUsuario;

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.child("nomeReferenciaUsuario").getValue().toString().contains(stringPesquisa)) {
                                final Usuario usuario = new Usuario();
                                usuario.setId(item.getKey());
                                usuario.setEmail(item.child("email").getValue().toString());
                                usuario.setNome(item.child("nome").getValue().toString());
                                usuario.setNomeReferenciaUsuario(item.child("nomeReferenciaUsuario").getValue().toString());
                                usuario.setSobrenome(item.child("sobrenome").getValue().toString());

                                usuarios.add(usuario);
                                usuarioListAdapter.notifyDataSetChanged();

//                            //O Bloco Abaixo é usado para verificar se o usuário já não é um membro comum
//                            usuario.setUsuarioJaMembro(false);
//                            DatabaseReference mRefVerificaMembroUsuario = FirebaseDatabase.getInstance().getReference()
//                                    .child(idEquipe).child("membros");
//                            mRefVerificaMembroUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for(DataSnapshot item : dataSnapshot.getChildren()){
//                                        if(usuario.getId() == item.getKey())
//                                            usuario.setUsuarioJaMembro(true);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
//
//                            //Caso o usuarío não tenha sido identificado do membro, ele irá para esta outra verificação abaixo
//                            if(!usuario.getUsuarioJaMembro()) {
//                                //O Bloco Abaixo é usado para verificar se o usuário já não é um membro administrador
//                                mRefVerificaMembroUsuario = FirebaseDatabase.getInstance().getReference()
//                                        .child(idEquipe).child("administradores");
//                                mRefVerificaMembroUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        for(DataSnapshot item : dataSnapshot.getChildren()){
//                                            if(usuario.getId() == item.getKey())
//                                                usuario.setUsuarioJaMembro(true);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//                                //De acordo com as verificações feitas, o usuário será mostrado na recyclerview ou não
//                                if(!usuario.getUsuarioJaMembro()) {
//
//                                }
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//
//
//                });
//            }
//
//        });
                            }
                            
                        }
                    if(usuarios.isEmpty())
                        Toast.makeText(AddMembroEquipeActivity.this, "Nenhum usuário encontrado!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}

