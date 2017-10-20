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

import com.google.android.gms.common.SignInButton;
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


    public void logar(String email, String senha) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {

            Toast.makeText(this, "Algum campo ficou em branco", Toast.LENGTH_SHORT).show();
        } else {


            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Algo deu errado", Toast.LENGTH_SHORT).show();

                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }
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
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvCadastro = (TextView) findViewById(R.id.tvCadastro);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String senha = mSenha.getText().toString();

                logar(email, senha);
            }
        });

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));

            }
        });


    }
}
