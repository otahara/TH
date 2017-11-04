package talkhub.com.br.th;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membro_equipe);

        Bundle bundle = getIntent().getExtras();
        idEquipe = bundle.getString("idEquipe");
        nomeEquipe = bundle.getString("nomeEquipe");
        descEquipe = bundle.getString("descEquipe");


        mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");

        mTextoPesquisa = (EditText) findViewById(R.id.et_pesquisa);
        mPesquisar = (Button) findViewById(R.id.bt_pesquisar);

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

                Query query = mRefUsuario.orderByChild("nomeReferenciaUsuario").startAt(stringPesquisa);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            Usuario usuario = new Usuario();
                            usuario.setId(item.getKey());
                            usuario.setEmail(item.child("email").getValue().toString());
                            usuario.setNome(item.child("nome").getValue().toString());
                            usuario.setNomeReferenciaUsuario(item.child("nomeReferenciaUsuario").getValue().toString());
                            usuario.setSobrenome(item.child("sobrenome").getValue().toString());
                            usuarios.add(usuario);
                            usuarioListAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });








    }
}
