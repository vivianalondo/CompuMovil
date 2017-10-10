package co.edu.udea.compumovil.gr06_20172.lab1;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddApartmentFragment extends Fragment {
    Bitmap pict;
    private static final int REQUEST_CODE_GALLERY=1;
    private ImageView targetImageR;
    DbHelper dbH;
    SQLiteDatabase db;
    static final int PICK_REQUEST =1337;
    Uri contact = null;
    Button btnR;
    EditText[] txtValidateR = new EditText[6];
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbH=new DbHelper(getActivity().getBaseContext());
        view=inflater.inflate(R.layout.fragment_add_apartment,container,false);
        pict = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_apart);
        targetImageR = (ImageView)view.findViewById(R.id.ImageApartment);

        targetImageR.setImageBitmap(pict);
        txtValidateR[0]=(EditText)view.findViewById(R.id.NombreApartamento);
        txtValidateR[1]=(EditText)view.findViewById(R.id.tipoApartamento);
        txtValidateR[2]=(EditText)view.findViewById(R.id.DescripciónApartamento);
        txtValidateR[3]=(EditText)view.findViewById(R.id.AreaApartamento);
        txtValidateR[4]=(EditText)view.findViewById(R.id.addressApartamento);
        txtValidateR[5]=(EditText)view.findViewById(R.id.valorApartamento);
        btnR = (Button)view.findViewById(R.id.buttonApartamento);
        btnR.setEnabled(false);
        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(verificarVaciosSinMessageR(txtValidateR)){btnR.setEnabled(true);}
                else{btnR.setEnabled(false);}
            }
        };
        for (int n = 0; n < txtValidateR.length; n++)
        {
            txtValidateR[n].addTextChangedListener(btnActivation);
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_REQUEST){
            if(resultCode == getActivity().RESULT_OK){
                contact = data.getData();
            }
        }
        if (resultCode == getActivity().RESULT_OK && (requestCode==REQUEST_CODE_GALLERY )){
            try {
                Uri targetUri = data.getData();
                pict = redimensionarImagenMaximo(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri)),400,350);
                targetImageR.setImageBitmap(pict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para verificar vacíos
     * @param txtValidate
     * @return
     */
    public boolean verificarVaciosSinMessageR(EditText[] txtValidate)
    {
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Convertir imagen a un array de bytes
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Método para crear un nuevo apartamento
     * @throws JSONException
     */
    public void ValidarApartamentos() {//valida los apartamentos, que ya esten en la BD

        /*db = dbH.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor search = db.rawQuery("select count(*) from " + StatusContract.TABLE_APARTMENT, null);
        search.moveToFirst();
        int aux=Integer.parseInt(search.getString(0));
        values.put(StatusContract.Column_Apartment.ID,(aux+1));
        values.put(StatusContract.Column_Apartment.NAME,txtValidateR[0].getText().toString());
        values.put(StatusContract.Column_Apartment.TYPE,txtValidateR[1].getText().toString());
        values.put(StatusContract.Column_Apartment.DESCRIPTION,txtValidateR[2].getText().toString());
        values.put(StatusContract.Column_Apartment.AREA,txtValidateR[3].getText().toString());
        values.put(StatusContract.Column_Apartment.ADDRESS,txtValidateR[4].getText().toString());
        values.put(StatusContract.Column_Apartment.VALUE,txtValidateR[5].getText().toString());
        search = db.rawQuery("select * from " + StatusContract.TABLE_LOGIN, null);
        search.moveToFirst();
        values.put(StatusContract.Column_Apartment.PICTURE,getBitmapAsByteArray(pict));
        db.insertWithOnConflict(StatusContract.TABLE_APARTMENT, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();*/

        int user_id = 1;
        String name = txtValidateR[0].getText().toString();
        String apType = txtValidateR[1].getText().toString();
        String description = txtValidateR[2].getText().toString();
        Float area = Float.parseFloat(txtValidateR[3].getText().toString());
        Float value = Float.parseFloat(txtValidateR[5].getText().toString());
        String address = txtValidateR[4].getText().toString();
        //String picture = getBitmapAsByteArray(pict).toString();


        // get the base 64 string
        String imgString = Base64.encodeToString(getBitmapAsByteArray(pict),
                Base64.NO_WRAP);

        System.out.println("El arreglo de img: "+ getBitmapAsByteArray(pict));
        System.out.println("El arreglo de img en string: "+ getBitmapAsByteArray(pict).toString());
        System.out.println("La variable picture vale: "+ imgString);
        //values.put(StatusContract.Column_Apartment.PICTURE,getBitmapAsByteArray(pict));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //Apartment apartment = new Apartment(user_id, name ,apType, description, area, value, address);
        Apartment apartment = new Apartment(1, name, apType, description,
                area, value, address, imgString);
        Call<Apartment> call = apiService.createApartment(apartment);
        call.enqueue(new Callback<Apartment>() {
            @Override
            public void onResponse(Call<Apartment> call, Response<Apartment> response) {

                if(response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    Log.i("TAG", "post submitted to API." + response.body().toString());
                }else{
                    System.out.println("Respuesta post no exitosa");
                }
            }

            @Override
            public void onFailure(Call<Apartment> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
            }
        });

    }

    /**
     * Método para abrir la galería
     */
    public void ClickGalleryR() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    /**
     * Método para redimensionar imagen
     * @param mBitmap
     * @param newWidth
     * @param newHeigth
     * @return
     */
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

}
