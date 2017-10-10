package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.User;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Viviana Londoño on 21/08/2017.
 */

public class Login extends AppCompatActivity {

    private static final int REQUEST_CODE=1;
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    DbHelper dbHelper;
    SQLiteDatabase db;
    private RadioButton rbSesion;
    private boolean isActivateRadioButton;
    private static final String STRING_PREFERENCES = "co.edu.udea.compumovil.gr06_20172.lab1";
    private static final String PREFERENCES_STATE_BUTTON_SESION = "state.button.sesion";

    @Override
    protected void onResume(){
        super.onResume();
        emailView.setError(null);
        passwordView.setError(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailView = (AutoCompleteTextView) findViewById(R.id.txtLoginEmail);
        passwordView = (EditText) findViewById(R.id.txtLoginPassword);
        //rbSesion = (RadioButton) findViewById(R.id.rbSesion);
        dbHelper = new DbHelper(this);

        /*isActivateRadioButton = rbSesion.isChecked();//Desactivado

        rbSesion.setOnClickListener(new View.OnClickListener(){
            //Activado
            @Override
            public void onClick(View v) {
                if (isActivateRadioButton){
                    rbSesion.setChecked(false);
                }
                isActivateRadioButton = rbSesion.isChecked();
            }

        });*/
    }

    /*public void saveStateButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_STATE_BUTTON_SESION,rbSesion.isChecked()).apply();
    }*/

    public boolean geteStateButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_STATE_BUTTON_SESION,false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String u="",p="";
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("email") && data.hasExtra("pass")) {
                u = data.getExtras().getString("email");
                p = data.getExtras().getString("pass");
            }
            if(!u.equals(".")) {
                emailView.setText(u);
                passwordView.setText(p);
                Toast.makeText(this, getString(R.string.ok_register), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,getString(R.string.erro_register),Toast.LENGTH_LONG).show();
            }
        }
    }


    public void register(View v){
        Intent newActivity = new Intent(this, Register.class);
        startActivityForResult(newActivity, REQUEST_CODE);
    }

    public void sigInLogin(View v) throws InterruptedException{
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(email)){
            emailView.setError(getString(R.string.field_required));
            focusView = emailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)){
            passwordView.setError(getString(R.string.field_required));
            focusView = passwordView;
            cancel = true;
        }/* else if (!isPassword(email,password)){
            passwordView.setError(getString(R.string.field_incorrect));
            focusView = passwordView;
            cancel = true;
        }*/
        if (cancel){
            focusView.requestFocus();
        }else {
            //saveStateButton();
            saveLoginSharedPrferences(email);
            register();
        }
    }

    private boolean isPassword(String sEmail, String pass){

        db = dbHelper.getWritableDatabase();
        Cursor search=db.rawQuery("select * from "+StatusContract.TABLE_USER+" where email='"+sEmail+"'",null);
        String validation="";
        if (search.moveToFirst()){
            do {
                validation = search.getString(8);
            }while (search.moveToNext());
        }
        db.close();
        if (pass.equals(validation)){return true;}
        return false;
    }

    private void register() throws InterruptedException{
        db = dbHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(StatusContract.Column_Login.ID,(1));
        values.put(StatusContract.Column_Login.EMAIL, emailView.getText().toString());

        String email = emailView.getText().toString();
        String pass = passwordView.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.loginPost(email, pass);

        System.out.println("El usuario y contrasela son: "+email+ pass);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    Log.i("TAG", "post submitted to API." + response.body().toString());
                    Log.d("tag",emailView.getText().toString());
                    db.insertWithOnConflict(StatusContract.TABLE_LOGIN, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                    db.close();
                    //saveStateButton();
                    Intent newActivity = new Intent(Login.this, MainActivity.class);
                    startActivity(newActivity);
                    finish();
                }else{
                    System.out.println("Respuesta post no exitosa");
                    System.out.println(response.message());
                    Toast.makeText(Login.this, "Please check your data, email or password incorrect", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Toast.makeText(Login.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });

    }

    //guardar configuración aplicación Android usando SharedPreferences
    public void saveLoginSharedPrferences(String email){
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean("preferenciasGuardadas", true);
        editor.putString("preferencia1", email);
        //editor.putString("preferencia2", "y tambien esto");
        //editor.commit();
        editor.apply();
        //Toast.makeText(this, "guardando preferencias", Toast.LENGTH_SHORT).show();
    }

}
