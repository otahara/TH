package talkhub.com.br.th;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText mNome;
    private EditText mSobreNome;
    private EditText mEmail;
    private EditText mCompanhia;
    private EditText mSenha;
    private EditText mConfirmarSenha;
    private Button mCadastrar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mNome = (EditText) findViewById(R.id.etNome);
        mSobreNome = (EditText) findViewById(R.id.etSobreNome);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mCompanhia = (EditText) findViewById(R.id.etCompanhia);
        mSenha = (EditText) findViewById(R.id.etSenha);
        mConfirmarSenha = (EditText) findViewById(R.id.etConfirmaSenha);
        mCadastrar = (Button) findViewById(R.id.btnCadastrar);

        mAuth = FirebaseAuth.getInstance();


        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = mNome.getText().toString();
                String sobreNome = mSobreNome.getText().toString();
                String email = mEmail.getText().toString();
                String companhia = mCompanhia.getText().toString();
                String senha = mSenha.getText().toString();
                String confirmarSenha = mConfirmarSenha.getText().toString();

                if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(sobreNome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(companhia)
                        || TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmarSenha)) {
                    Toast.makeText(CadastroActivity.this, "Campos em branco", Toast.LENGTH_SHORT).show();
                } else if (senha.equals(confirmarSenha)) {
                    cadastrar(nome, sobreNome, email, companhia, senha);

                } else {
                    Toast.makeText(CadastroActivity.this, "Senhas não conferem", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void cadastrar(String nome, String sobreNome, String email, String companhia, String senha) {

        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Algo deu errado", Toast.LENGTH_SHORT).show();
                } else {

                    startActivity(new Intent(CadastroActivity.this, MainActivity.class));

                }

            }
        });
    }
}
