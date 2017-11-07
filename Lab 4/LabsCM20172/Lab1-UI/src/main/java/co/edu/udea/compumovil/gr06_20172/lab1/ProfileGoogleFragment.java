package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileGoogleFragment extends Fragment {

    private ImageView imageProfile;
    TextView txtEmailView;
    TextView txtNameView;
    View view;
    public static final String EXTRA_EMAIL = "emailE";
    public static final String EXTRA_NAME = "nameE";
    private GoogleApiClient googleApiClient;
    Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btnlogout;


    public ProfileGoogleFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile_google, container, false);
        context = getActivity().getApplicationContext();
        txtEmailView =(TextView)view.findViewById(R.id.viewEmailG);
        txtNameView =(TextView)view.findViewById(R.id.viewNameG);
        imageProfile=(ImageView)view.findViewById(R.id.viewProfileG);

        btnlogout = (Button)view.findViewById(R.id.btnlog_out);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

//        googleApiClient = new  GoogleApiClient.Builder(context)
//                .enableAutoManage(getActivity(), getActivity())
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                } else {
                    goLogInScreen();
                }
            }
        };


        btnlogout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                logOut(view);
            }
        });


        return view;

    }


    private void setUserData(FirebaseUser user) {
        txtNameView.setText(user.getDisplayName());
        txtEmailView.setText(user.getEmail());

        Glide.with(this).load(user.getPhotoUrl()).into(imageProfile);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }



    private void goLogInScreen() {
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();

        /*Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.not_login_google, Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        goLogInScreen();
    }



    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
