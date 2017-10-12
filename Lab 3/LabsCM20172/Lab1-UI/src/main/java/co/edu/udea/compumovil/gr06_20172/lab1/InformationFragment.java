package co.edu.udea.compumovil.gr06_20172.lab1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.User;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Viviana Londoño on 24/08/2017.
 */

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    private ImageView targetImageR;
    DbHelper dbHelper;
    SQLiteDatabase db;
    TextView[] txtValidateR = new TextView[8];
    View view;
    public static final String EXTRA_EMAIL = "emailE";
    public static final String EXTRA_PASS = "passE";
    byte[] avat = new byte[0];



    public InformationFragment() {//activity que enseña información

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dbHelper =new DbHelper(getActivity().getBaseContext());

        view=inflater.inflate(R.layout.fragment_information, container, false);
        txtValidateR[0]=(TextView)view.findViewById(R.id.viewName);
        txtValidateR[1]=(TextView)view.findViewById(R.id.viewLastName);
        txtValidateR[2]=(TextView)view.findViewById(R.id.viewEmail);
        txtValidateR[3]=(TextView)view.findViewById(R.id.viewGender);
        txtValidateR[4]=(TextView)view.findViewById(R.id.viewDate);
        txtValidateR[5]=(TextView)view.findViewById(R.id.viewPhone);
        txtValidateR[6]=(TextView)view.findViewById(R.id.viewAddress);
        txtValidateR[7]=(TextView)view.findViewById(R.id.viewCity);
        targetImageR=(ImageView)view.findViewById(R.id.viewProfile);

        String str = getActivity().getIntent().getStringExtra("myString");
        String emailEx = getActivity().getIntent().getStringExtra(EXTRA_EMAIL);
        String passEx = getActivity().getIntent().getStringExtra(EXTRA_PASS);

        System.out.println("Extra email: "+ emailEx);
        System.out.println("Extra pass: "+ passEx);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.loginPost(emailEx, passEx);

        System.out.println("El usuario y contrasela son: "+emailEx+ passEx);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    System.out.println(response.body().toString());
                    txtValidateR[0].setText("Nombre: "+user.getName());
                    txtValidateR[1].setText("Apellido: "+user.getLastName());
                    txtValidateR[2].setText("E-mail: "+user.getEmail());
                    txtValidateR[3].setText("Género: "+user.getGender());
                    txtValidateR[4].setText("Fecha de nacimiento: "+user.getDate());
                    txtValidateR[5].setText("Teléfono: "+user.getPhone());
                    txtValidateR[6].setText("Dirección: "+user.getAddress());
                    txtValidateR[7].setText("Ciudad: "+user.getCity());

                    avat = user.getPicture().getBytes();
                    byte[] decodedString = Base64.decode(avat, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    targetImageR.setImageBitmap(decodedByte);

                }else{
                    System.out.println("Respuesta post no exitosa");
                    System.out.println(response.message());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });


        return view;
    }
}
