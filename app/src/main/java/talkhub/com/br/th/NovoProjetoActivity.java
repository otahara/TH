package talkhub.com.br.th;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import talkhub.com.br.th.Entities.Projeto;
import talkhub.com.br.th.Entities.Usuario;

public class NovoProjetoActivity extends AppCompatActivity {

    private EditText mNomeProjeto;
    private EditText mDescProjeto;
    private Button mSalvarProjeto;
    private String idEquipe;
    private String nomeEquipe;
    private String descEquipe;

    //Propriedades usadas para recuperação de dados do firebase
    private DatabaseReference mRefUsuario;
    private FirebaseAuth mAuth;
    private String emailUsuario;
    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_projeto);

        Bundle bundle = getIntent().getExtras();
        idEquipe = bundle.getString("idEquipe");
        nomeEquipe = bundle.getString("nomeEquipe");
        descEquipe = bundle.getString("descEquipe");

        mNomeProjeto = (EditText) findViewById(R.id.et_nome_projeto);
        mDescProjeto = (EditText) findViewById(R.id.et_desc_projeto);
        mSalvarProjeto = (Button) findViewById(R.id.bt_salvar_projeto);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail().toString();

        final Query query = mRefUsuario.orderByChild("email").equalTo(emailUsuario);


        mSalvarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nomeProjeto = mNomeProjeto.getText().toString();
                final String descProjeto = mDescProjeto.getText().toString();

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            Projeto projeto = new Projeto();
                            projeto.setNome(nomeProjeto);
                            projeto.setDescricao(descProjeto);
                            usuario.setId(item.getKey());
                            usuario.setNome(item.child("nome").getValue().toString());
                            usuario.setSobrenome(item.child("sobrenome").getValue().toString());
                            usuario.setNomeReferenciaUsuario(item.child("nomeReferenciaUsuario").getValue().toString());
                            usuario.setEmail(item.child("email").getValue().toString());
                            projeto.novoProjeto(idEquipe, usuario);
                            Intent intent = new Intent(NovoProjetoActivity.this, ProjetosEquipeActivity.class);
                            intent.putExtra("idEquipe", idEquipe);
                            intent.putExtra("nomeEquipe", nomeEquipe);
                            intent.putExtra("descEquipe", descEquipe);
                            startActivity(intent);

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
