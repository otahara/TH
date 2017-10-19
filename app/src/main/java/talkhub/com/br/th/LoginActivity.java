package talkhub.com.br.th;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private static Button btnLogin;
    private static TextView tvCadastro;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logar();
        cadastrar();
    }
}
