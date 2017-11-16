package com.appaccounting.compumovil.projectappaccounting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;

public class LoginActivity extends AppCompatActivity {
    DbHelper dbH;
    AutoCompleteTextView txtEmail;
    EditText txtPassword;
    private static final int REQUEST_CODE=1;

    @Override
    protected void onResume(){
        super.onResume();
        txtEmail.setError(null);
        txtPassword.setError(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbH = new DbHelper(this);
        txtEmail = (AutoCompleteTextView) findViewById(R.id.emailLogin);
        txtPassword = (EditText) findViewById(R.id.passwordLogin);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                txtEmail.setText(u);
                txtPassword.setText(p);
                Toast.makeText(this, getString(R.string.ok_register), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,getString(R.string.erro_register),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void singInLogin(View v) throws InterruptedException {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(email)){
            txtEmail.setError(getString(R.string.field_required));
            focusView = txtEmail;
            cancel = true;
        } else if (TextUtils.isEmpty(password)){
            txtPassword.setError(getString(R.string.field_required));
            focusView = txtPassword;
            cancel = true;
        } else if (!dbH.validatePassword(email,password)){
            txtPassword.setError(getString(R.string.field_incorrect));
            focusView = txtPassword;
            cancel = true;
        }
        if (cancel){
            focusView.requestFocus();
        }else {
            singInApp();
        }

    }

    private void singInApp() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        dbH.addLogin(email, password);

        Intent newActivity = new Intent(this, PrincipalActivity.class);
        newActivity.putExtra(PrincipalActivity.EXTRA_EMAIL, email);
        newActivity.putExtra(PrincipalActivity.EXTRA_PASS, password);
        startActivity(newActivity);
        finish();
    }
}
