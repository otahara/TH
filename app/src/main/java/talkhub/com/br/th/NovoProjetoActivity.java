package talkhub.com.br.th;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import talkhub.com.br.th.Entities.Projeto;

public class NovoProjetoActivity extends AppCompatActivity {

    private EditText mNomeProjeto;
    private EditText mDescProjeto;
    private Button mSalvarProjeto;
    private String idEquipe;
    private String nomeEquipe;
    private String descEquipe;

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

        mSalvarProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomeProjeto = mNomeProjeto.getText().toString();
                final String descProjeto = mDescProjeto.getText().toString();
                Projeto projeto = new Projeto();
                projeto.setNome(nomeProjeto);
                projeto.setDescricao(descProjeto);
                projeto.novoProjeto(idEquipe);
                Intent intent = new Intent(NovoProjetoActivity.this, ProjetosEquipeActivity.class);
                intent.putExtra("idEquipe", idEquipe);
                intent.putExtra("nomeEquipe", nomeEquipe);
                intent.putExtra("descEquipe", descEquipe);
                startActivity(intent);
            }
        });



    }
}
