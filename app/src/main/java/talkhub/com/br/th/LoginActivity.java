package talkhub.com.br.th;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static Button btnLogin;
    private static TextView tvCadastro;
    private static EditText mEmail;
    private static EditText mSenha;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void cadastrar() {
        tvCadastro = (TextView)findViewById(R.id.tvCadastro);
        tvCadastro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void logar() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {





                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }



    public void logar(String email, String senha){
    //Verifica se algum campo ficou em branco
    if(TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){

        Toast.makeText(this, "Algum campo ficou em branco", Toast.LENGTH_SHORT).show();
    } else {


        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });


    }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.etEmail);
        mSenha = (EditText) findViewById(R.id.etSenha);
        mAuth = FirebaseAuth.getInstance();


        logar();
        cadastrar();
    }
}
