package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.content.Intent;
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

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFacebookFragment extends Fragment {
    private ImageView imageProfileF;
    TextView txtEmailViewF;
    TextView txtNameViewF;
    View view;
    public static final String EXTRA_EMAIL = "emailE";
    public static final String EXTRA_NAME = "nameE";
    private GoogleApiClient googleApiClient;
    Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btnlogoutF;

    public ProfileFacebookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile_facebook, container, false);
        context = getActivity().getApplicationContext();
        txtEmailViewF =(TextView)view.findViewById(R.id.viewNameF);
        txtNameViewF =(TextView)view.findViewById(R.id.viewEmailF);
        imageProfileF=(ImageView)view.findViewById(R.id.viewProfileF);

        btnlogoutF = (Button)view.findViewById(R.id.btnlog_outF);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();

            txtNameViewF.setText(name);
            txtEmailViewF.setText(email);
            //uidTextView.setText(uid);
        } else {
            goLoginScreen();
        }


        btnlogoutF.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                logout(view);
            }
        });


        return view;

    }

    private void goLoginScreen() {
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
