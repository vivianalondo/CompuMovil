package co.edu.udea.compumovil.gr06_20172.lab1;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;


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
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public void ValidarApartamentos() {//valida los apartamentos, que ya esten en la BD
        db = dbH.getWritableDatabase();
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
        db.close();
    }
    public void ClickGalleryR() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }
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
