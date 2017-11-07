package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.User;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Viviana Londoño on 21/08/2017.
 */

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE=1;
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    DbHelper dbHelper;
    SQLiteDatabase db;
    private static final String STRING_PREFERENCES = "co.edu.udea.compumovil.gr06_20172.lab1";
    private static final String PREFERENCES_STATE_BUTTON_SESION = "state.button.sesion";
    private Session session;
    private Button btnIngresar;
    private Button Registrar;

    private String email;
    private String password;

    private String eGoogle;
    private String pGoogle;

    //Login con google
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private static final int SIGN_IN_CODE = 777;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;

    //Login Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;

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
        session = new Session(this);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        Registrar = (Button) findViewById(R.id.btnregistrar) ;

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.signinFacebook);


        if (session.loggedin()&& dbHelper.mantener()){
            //session.setLoggedin(true,db.get);
            Intent newActivity = new Intent(Login.this, MainActivity.class);
            newActivity.putExtra(MainActivity.EXTRA_EMAIL, email);
            newActivity.putExtra(MainActivity.EXTRA_PASS, password);
            startActivity(newActivity);
            finish();
        }else if (!dbHelper.mantener()){
            session.logout();
        }

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            //Activado
            @Override
            public void onClick(View v) {
                try {
                    sigInLogin(v);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new  GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signinGoogle);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });


        //Facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goMainScreen();
                }
            }
        };

        //progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }
                //progressBar.setVisibility(View.GONE);
                //loginButton.setVisibility(View.VISIBLE);
            }
        });
    }


    private void goLogInScreen() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean geteStateButton(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_STATE_BUTTON_SESION,false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        /*if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
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
        }*/
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, R.string.not_login_google, Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        //progressBar.setVisibility(View.VISIBLE);
        //signInButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //progressBar.setVisibility(View.GONE);
                //signInButton.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.not_login_google, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainActivity(String em, String nam) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.EXTRA_EMAIL, em);
        intent.putExtra(MainActivity.EXTRA_PASS, "google");
        intent.putExtra(MainActivity.EXTRA_NAME, nam);
        startActivity(intent);
    }


    public void register(View v){
        Intent newActivity = new Intent(this, Register.class);
        startActivityForResult(newActivity, REQUEST_CODE);
    }

    public void sigInLogin(View v) throws InterruptedException{
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
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


        email = emailView.getText().toString();
        password = passwordView.getText().toString();

        values.put(StatusContract.Column_Login.ID,(1));
        values.put(StatusContract.Column_Login.EMAIL, email);
        values.put(StatusContract.Column_Login.PASS, password);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.loginPost(email, password);

        System.out.println("El usuario y contrasela son: "+email+ password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    Log.i("TAG", "post submitted to API." + response.body().toString());
                    Log.d("tag",emailView.getText().toString());
                    db.insertWithOnConflict(StatusContract.TABLE_LOGIN, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                    session.setLoggedin(true,response.body());
                    db.close();

                    Intent newActivity = new Intent(Login.this, MainActivity.class);
                    newActivity.putExtra(MainActivity.EXTRA_EMAIL, email);
                    newActivity.putExtra(MainActivity.EXTRA_PASS, password);
                    Log.d("TAGO",  email);

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra(MainActivity.EXTRA_EMAIL, em);
        intent.putExtra(MainActivity.EXTRA_PASS, "google");
        //intent.putExtra(MainActivity.EXTRA_NAME, nam);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
