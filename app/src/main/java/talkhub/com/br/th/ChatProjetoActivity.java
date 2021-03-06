package talkhub.com.br.th;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.text.Editable;
        import android.text.TextUtils;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageButton;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

        import talkhub.com.br.th.Adapter.ChatListAdapter;
        import talkhub.com.br.th.Entities.MensagemChat;
        import talkhub.com.br.th.Entities.Usuario;

public class ChatProjetoActivity extends AppCompatActivity {

    private String idProjeto;
    private String nomeProjeto;
    private String descProjeto;
    private String idEquipe;
    private String nomeEquipe;



    private ImageButton mButtonEnviarMensagem;
    private EditText mEditTextMensagem;

    private Usuario usuario = new Usuario();
    private FirebaseAuth mAuth;
    private DatabaseReference mRefUsuario;
    private DatabaseReference mRefMsg;
    private List<MensagemChat> mensagens = new ArrayList<MensagemChat>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Log.d("backbutton", "cheguei");
                Intent intent = new Intent(ChatProjetoActivity.this, ProjetosEquipeActivity.class);
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
        setContentView(R.layout.activity_chat_projeto);

        Bundle bundle = getIntent().getExtras();
        idProjeto = bundle.getString("idProjeto");
        nomeProjeto = bundle.getString("nomeProjeto");
        descProjeto = bundle.getString("descProjeto");

        idEquipe = bundle.getString("idEquipe");
        nomeEquipe = bundle.getString("nomeEquipe");


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tb_muralequipe);
        toolbar.setTitle(nomeProjeto);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.inflateMenu(R.menu.menu_projeto);
        
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                
                switch (item.getItemId()){
                    
                    case R.id.menuAdicionarMembros:
                                Intent intent = new Intent(ChatProjetoActivity.this, AddMembroProjetoActivity.class);
                                intent.putExtra("idEquipe", idEquipe);
                                intent.putExtra("nomeEquipe", nomeEquipe);
                                intent.putExtra("idProjeto", idProjeto);
                                intent.putExtra("nomeProjeto", nomeProjeto);
                                intent.putExtra("descProjeto", descProjeto);
                                startActivity(intent);



                        return true;
                    
                }
                
                
                
                return false;

            }
        });



        mButtonEnviarMensagem = (ImageButton) findViewById(R.id.bt_enviar_msg);
        mEditTextMensagem = (EditText) findViewById(R.id.et_Mensagem);
        //O boitão deve ficar bloqueado até que o firebase retorne o id do usuario logado
        mButtonEnviarMensagem.setEnabled(false);



        //Código necessário para conexão e inserção de dados no firebase
        mAuth = FirebaseAuth.getInstance();
        String emailUsuario = mAuth.getCurrentUser().getEmail().toString();
        mRefUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        Query queryUsuario = mRefUsuario.orderByChild("email").equalTo(emailUsuario);

        queryUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    usuario.setId(item.getKey());
                    usuario.setNome(item.child("nome").getValue().toString());
                    usuario.setSobrenome(item.child("sobrenome").getValue().toString());
                    preencheChat(usuario.getId());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mButtonEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mensagem = mEditTextMensagem.getText().toString();
                MensagemChat mensagemChat = new MensagemChat(mensagem, usuario);
                mensagemChat.novaMensagemProjeto(idProjeto, idEquipe);
                mEditTextMensagem.setText("");

            }
        });

        mEditTextMensagem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(mEditTextMensagem.getText().toString()))
                    mButtonEnviarMensagem.setEnabled(true);
                else
                    mButtonEnviarMensagem.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }

    public void preencheChat(String idUsuario){

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);

        final ChatListAdapter chatListAdapter = new ChatListAdapter(mensagens, this);
        recyclerView.setAdapter(chatListAdapter);

        confirmaLeituraMensagem();


        mRefMsg = FirebaseDatabase.getInstance().getReference().child("mensagens").child("mensagens_projeto").child(idProjeto);


        mRefMsg.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals("leitura_usuarios_pendentes")) {

                    MensagemChat msg = new MensagemChat();
                    msg.setId(dataSnapshot.getKey());
                    msg.setIdUsuario(dataSnapshot.child("idUsuario").getValue().toString());
                    msg.setNomeUsuario(dataSnapshot.child("nomeUsuario").getValue().toString());
                    msg.setSobrenomeUsuario(dataSnapshot.child("sobrenomeUsuario").getValue().toString());
                    msg.setTexto(dataSnapshot.child("texto").getValue().toString());
                    msg.setHoraMsg(Long.valueOf(dataSnapshot.child("horaMsg").getValue().toString()));
                    mensagens.add(msg);
                    chatListAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(mensagens.size() - 1);
                }



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void confirmaLeituraMensagem(){

        DatabaseReference mRefUsuarioPendente = FirebaseDatabase.getInstance().getReference().child("mensagens")
                .child("mensagens_projeto").child(idProjeto).child("leitura_usuarios_pendentes").child(LoginActivity.idUsuario);



        mRefUsuarioPendente.removeValue();

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_projeto, menu);
        return true;
    }
}

