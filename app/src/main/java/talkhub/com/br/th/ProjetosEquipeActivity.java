package talkhub.com.br.th;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class ProjetosEquipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projetos_equipe);
        android.support.v7.widget.Toolbar tbProjetos = (android.support.v7.widget.Toolbar) findViewById(R.id.tb_projetos);
        tbProjetos.setTitle("NOME DA EQUIPE");
        setSupportActionBar(tbProjetos);

        tbProjetos.inflateMenu(R.menu.menu_equipe);
    }
}
