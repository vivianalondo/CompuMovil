package com.appaccounting.compumovil.projectappaccounting;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView txt;
    private Button btnRegistrarse;
    private boolean control=false;
    EditText[] txtValidate = new EditText[4];
    DbHelper dbH;
    User user;
    Integer newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtValidate[0]=(EditText)findViewById(R.id.register_name);
        txtValidate[1]=(EditText)findViewById(R.id.registerEmail);
        txtValidate[2]=(EditText)findViewById(R.id.register_password);
        txtValidate[3]=(EditText)findViewById(R.id.register_passwordconfirm);

        dbH = new DbHelper(this);
        btnRegistrarse = (Button)findViewById(R.id.register_button);
        //user = new User();

        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(verificarVaciosSinMessage(txtValidate)){btnRegistrarse.setEnabled(true);}
                else{btnRegistrarse.setEnabled(false);}
            }
        };
        for (int n = 0; n < txtValidate.length; n++)
        {
            txtValidate[n].addTextChangedListener(btnActivation);
        }
    }

    @Override
    public void finish() {//terminar la operación activity validando los dos campos usuario y password
        Intent data = new Intent();
        if(control) {
            data.putExtra("email", txtValidate[1].getText().toString());
            data.putExtra("pass", txtValidate[2].getText().toString());
        } else{
            data.putExtra("email", ".");
            data.putExtra("pass", ".");
        }
        setResult(RESULT_OK,data);
        super.finish();
    }


    /**
     * Método para guardar el registro de usuario
     * @param v
     */
    public void saveRegister(View v){
        View focusView=null;
        if (!verificarVacios(txtValidate)){

        }else if(!txtValidate[1].getText().toString().contains("@")){
            txtValidate[1].setError(getString(R.string.invalid_mail));
            focusView = txtValidate[0];
        }else{
            if (!txtValidate[3].getText().toString().equals(txtValidate[2].getText().toString())){
                txtValidate[3].setError(getString(R.string.pass_no_equals));
                focusView = txtValidate[3];
            }else if(!dbH.getUserEmail(txtValidate[1].getText().toString())){

                String name = txtValidate[0].getText().toString();
                String email = txtValidate[1].getText().toString();
                String pass = txtValidate[2].getText().toString();

                user = new User(name, email, pass);
                newUser = dbH.addUser(user);
                control=true;
                finish();

            }else{
                txtValidate[1].setError(getString(R.string.user_exists));
                focusView = txtValidate[1];
            }

        }

    }


    /**
     * Método para verificar vacíos
     * @param txtValidate
     * @return
     */
    public boolean verificarVacios(EditText[] txtValidate)//verificacion de campo requerido
    {
        View focus=null;
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                txtValidate[i].setError(getString(R.string.field_required));
                focus = txtValidate[i];
                return false;
            }
        }
        return true;
    }

    /**
     * Método para verificar vacíos sin mensaje
     * @param txtValidate
     * @return
     */
    public boolean verificarVaciosSinMessage(EditText[] txtValidate)
    {
        View focus=null;
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }
}
