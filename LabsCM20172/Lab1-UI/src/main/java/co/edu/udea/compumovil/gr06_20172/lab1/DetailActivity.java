package co.edu.udea.compumovil.gr06_20172.lab1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    DbHelper dbHelper;
    SQLiteDatabase db;

    public static final String EXTRA_NOMBRE = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page

        String name = getIntent().getStringExtra(EXTRA_NOMBRE);

        dbHelper =new DbHelper(this);
        db= dbHelper.getReadableDatabase();

        Cursor search = db.rawQuery("select * from " + StatusContract.TABLE_LOGIN, null);

        search.moveToFirst();
        search=db.rawQuery("select * from "+StatusContract.TABLE_APARTMENT+" where "+ StatusContract.Column_User.NAME+"='"+name+"'", null);
        search.moveToFirst();

        collapsingToolbar.setTitle(search.getString(1));

        collapsingToolbar.setTitle(search.getString(1));

        TextView placeType = (TextView) findViewById(R.id.place_type);
        placeType.setText(search.getString(2));

        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(search.getString(3));

        TextView placeArea = (TextView) findViewById(R.id.place_area);
        placeArea.setText(search.getString(4));

        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(search.getString(5));

        TextView placeValues =  (TextView) findViewById(R.id.place_value);
        placeValues.setText(search.getString(6));

        ImageView placePictures = (ImageView) findViewById(R.id.image);

        byte[] auxx=search.getBlob(7);
        Bitmap pict= BitmapFactory.decodeByteArray(auxx, 0, (auxx).length);
        placePictures.setImageBitmap(pict);
        db.close();

    }
}
