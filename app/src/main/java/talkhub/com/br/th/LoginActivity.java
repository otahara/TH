package talkhub.com.br.th;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static Button btnLogin;
    private static TextView tvCadastro;
    private static EditText mEmail;
    private static EditText mSenha;
    public static String idUsuario;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;


    public void logar(final String email, String senha) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {



            Toast.makeText(this, "Algum campo ficou em branco", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Entrando");
            progressDialog.setMessage("Verificando credenciais");
            progressDialog.show();
            btnLogin.setEnabled(false);
            mEmail.setEnabled(false);
            mSenha.setEnabled(false);
            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        mEmail.setEnabled(true);
                        mSenha.setEnabled(true);
                        btnLogin.setEnabled(true);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Algo deu errado", Toast.LENGTH_SHORT).show();

                    } else {

                        progressDialog.setMessage("Preparando ambiente");
                        progressDialog.show();
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("usuarios");
                        Query query = mRef.orderByChild("email").equalTo(email);


                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot item : dataSnapshot.getChildren()){
                                    idUsuario = item.getKey();
                                    progressDialog.dismiss();
                                }
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


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
        progressDialog = new ProgressDialog(this);
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
