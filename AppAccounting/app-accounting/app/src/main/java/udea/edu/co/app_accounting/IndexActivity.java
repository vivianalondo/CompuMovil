package udea.edu.co.app_accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity {
    private Button btnlogin;
    private Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnlogin = (Button) findViewById(R.id.btnIngresar);
        btnregister = (Button) findViewById(R.id.btnregistrar);

        btnlogin.setOnClickListener(new View.OnClickListener(){
            //Activado
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(IndexActivity.this, LoginActivity.class);
                startActivity(newActivity);

            }

        });

        btnregister.setOnClickListener(new View.OnClickListener(){
            //Activado
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(IndexActivity.this, RegisterActivity.class);
                startActivity(newActivity);

            }

        });
    }
}
