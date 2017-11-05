package talkhub.com.br.th;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import talkhub.com.br.th.Adapter.MembroListAdapter;
import talkhub.com.br.th.Adapter.UsuarioListAdapter;
import talkhub.com.br.th.Entities.Usuario;

public class InfoEquipeActivity extends AppCompatActivity {


    private List<Usuario> usuarioMembros = new ArrayList<Usuario>();
    private String idEquipe;
    private DatabaseReference mRef;
    private MembroListAdapter membroListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_equipe);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        idEquipe = bundle.getString("idEquipe");




        mRef = FirebaseDatabase.getInstance().getReference();

        membroListAdapter = new MembroListAdapter(usuarioMembros, this,
                idEquipe);

        Query query = mRef.child("equipes").child(idEquipe).child("membros");

//        mRefMembros = FirebaseDatabase.getInstance().getReference().child("equipes")
//                .child(idEquipe).child("membros");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){

                    Query query = mRef.child("usuarios").child(item.getKey());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                Usuario usuario = new Usuario();
                                usuario.setId(dataSnapshot.getKey().toString());
                                usuario.setNome(dataSnapshot.child("nome").getValue().toString());
                                usuario.setSobrenome(dataSnapshot.child("sobrenome").getValue().toString());
                                usuario.setEmail(dataSnapshot.child("email").getValue().toString());
                                usuario.setNomeReferenciaUsuario(dataSnapshot.child("nomeReferenciaUsuario").getValue().toString());
                                usuarioMembros.add(usuario);
                                membroListAdapter.notifyDataSetChanged();
                                Log.d("log", "cheguei " + usuario.getNome());
                            }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_membros_equipe);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);



        recyclerView.setAdapter(membroListAdapter);
















    }
}
