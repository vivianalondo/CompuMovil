package udea.edu.co.app_accounting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import udea.edu.co.app_accounting.helpers.DbHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtname;
    private EditText txtemail;
    private EditText txtpassword;
    private EditText txtpasswordconf;
    private Button btnRegister;
    private boolean control=false;

    DbHelper dbH;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname=(EditText)findViewById(R.id.register_name);

        txtemail=(EditText)findViewById(R.id.registerEmail);
        txtpassword =(EditText)findViewById(R.id.register_password);
        txtpasswordconf =(EditText)findViewById(R.id.register_passwordconfirm);
        btnRegister = (Button)findViewById(R.id.register_button);


        btnRegister.setEnabled(false);
        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //if(verificarVaciosSinMessage(txtValidate)){btn.setEnabled(true);}
                //else{btnRegister.setEnabled(false);}
            }
        };

    }

    @Override
    public void finish() {//terminar la operación activity validando los dos campos usuario y password
        Intent data = new Intent();
        if(control) {
            data.putExtra("email", txtemail.getText().toString());
            data.putExtra("pass", txtpassword.getText().toString());
        } else{
            data.putExtra("email", ".");
            data.putExtra("pass", ".");
        }
        setResult(RESULT_OK,data);
        super.finish();
    }

    public void RegisterUser(View v){


        // Store values at the time of the login attempt.
        String name = txtname.getText().toString();
        String email = txtemail.getText().toString();
        String pass = txtpassword.getText().toString();
        String passconf = txtpasswordconf.getText().toString();

        txtname.setError(null);
        txtemail.setError(null);
        txtpassword.setError(null);
        txtpasswordconf.setError(null);


        boolean cancel = false;
        View focusView=null;

        // Check for a valid password, if the user entered one.
        if (pass != passconf) {
            txtpasswordconf.setError(getString(R.string.error_invalid_password));
            focusView = txtpasswordconf;
            cancel = true;
        }

        // Check for password confirmation, if the user entered one.
        if (!TextUtils.isEmpty(pass) && !isPasswordValid(pass)) {
            txtpassword.setError(getString(R.string.error_invalid_password));
            focusView = txtpassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            txtemail.setError(getString(R.string.error_field_required));
            focusView = txtemail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            txtemail.setError(getString(R.string.error_invalid_email));
            focusView = txtemail;
            cancel = true;
        }

        // Check for a valid name address.
        if (TextUtils.isEmpty(name)) {
            txtname.setError(getString(R.string.error_field_required));
            focusView = txtname;
            cancel = true;
        }


        /*if (!verificarVacios(txtValidate)){
        }else if(!txtValidate[0].getText().toString().contains("@")){
            txtValidate[0].setError(getString(R.string.invalid_mail));
            focusView = txtValidate[0];
        }else{
            if (!txtValidate[2].getText().toString().equals(txtValidate[1].getText().toString())){
                txtValidate[2].setError(getString(R.string.pass_no_equals));
                focusView = txtValidate[2];
            }else{

                String email = txtValidate[0].getText().toString();
                String pass = txtValidate[1].getText().toString();
                String name = txtValidate[3].getText().toString();
                String lastname = txtValidate[4].getText().toString();
                String date = txtValidate[5].getText().toString();
                String phone = txtValidate[6].getText().toString();
                String address = txtValidate[7].getText().toString();
                String city = txtValidate[8].getText().toString();
                String gender = optionSelect;

                System.out.println("Picture vale: "+ picture);
                // get the base 64 string
                String imgString = Base64.encodeToString(getBitmapAsByteArray(picture),
                        Base64.NO_WRAP);
                //values.put(StatusContract.Column_User.PICTURE, getBitmapAsByteArray(picture));

                //Llamado a la api, para registrar usuario en DB externa
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                User user = new User(email, name, lastname, gender, date,
                        address, pass, city, phone, imgString);
                Call<User> call = apiService.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.isSuccessful()) {
                            System.out.println(response.body().toString());
                            Log.i("TAG", "post submitted to API." + response.body().toString());
                            control=true;
                            finish();
                        }else{
                            System.out.println("Respuesta post no exitosa");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("TAG", "Unable to submit post to API.");
                    }
                });
            }

        }*/

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
