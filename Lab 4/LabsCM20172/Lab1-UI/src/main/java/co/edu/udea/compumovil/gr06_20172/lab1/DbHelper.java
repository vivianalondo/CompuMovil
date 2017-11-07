package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Viviana Londoño on 22/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context){
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlLogin = String.format(
                "create table %s(%s int primary key, %s text unique,  %s text)",
                StatusContract.TABLE_LOGIN,
                StatusContract.Column_Login.ID,
                StatusContract.Column_Login.EMAIL,
                StatusContract.Column_Login.PASS);
        db.execSQL(sqlLogin);
        String sqlKeep = String.format(
                "create table %s(%s INTEGER primary key autoincrement, %s text unique)",
                StatusContract.TABLE_KEEP,
                StatusContract.Column_keep.ID,
                StatusContract.Column_keep.KEEP);
        db.execSQL(sqlKeep);
        String sqlUser = String.format(
                "create table %s(%s int primary key, %s text unique, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s blob)",
                StatusContract.TABLE_USER,
                StatusContract.Column_User.ID,
                StatusContract.Column_User.MAIL,
                StatusContract.Column_User.NAME,
                StatusContract.Column_User.LASTNAME,
                StatusContract.Column_User.GENDER,
                StatusContract.Column_User.DATE,
                StatusContract.Column_User.PHONE,
                StatusContract.Column_User.ADDRESS,
                StatusContract.Column_User.PASS,
                StatusContract.Column_User.CITY,
                StatusContract.Column_User.PICTURE);
        db.execSQL(sqlUser);
        String sqlApartment = String
                .format("create table %s (%s int primary key, %s text, %s text, %s text, %s text, %s text, %s text, %s blob)",
                        StatusContract.TABLE_APARTMENT,
                        StatusContract.Column_Apartment.ID,
                        StatusContract.Column_Apartment.NAME,
                        StatusContract.Column_Apartment.TYPE,
                        StatusContract.Column_Apartment.DESCRIPTION,
                        StatusContract.Column_Apartment.AREA,
                        StatusContract.Column_Apartment.ADDRESS,
                        StatusContract.Column_Apartment.VALUE,
                        StatusContract.Column_Apartment.PICTURE);
        //db.execSQL(sqlApartment);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists" + StatusContract.TABLE_USER);
        db.execSQL("drop table if exists" + StatusContract.TABLE_LOGIN);
        db.execSQL("drop table if exists" + StatusContract.TABLE_KEEP);
        db.execSQL("drop table if exists" + StatusContract.TABLE_APARTMENT);
    }

    public void changeKeep(){
        String selectQuery = "select keep from " + StatusContract.TABLE_KEEP;
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ContentValues values = new ContentValues();
        int uno = 1;
        if (cursor.getCount() == 0){
            values.put(StatusContract.Column_keep.KEEP, "0");
            db2.insert(StatusContract.TABLE_KEEP, null, values);
            db2.close();
        } else {
            cursor.moveToFirst();
            if (cursor.getString(0).equals("0")){
                Log.d("TAG", "cambié a 1");
                values.put(StatusContract.Column_keep.KEEP, "1");
                db2.update(StatusContract.TABLE_KEEP, values, StatusContract.Column_keep.ID+"= 1", null);
                db2.close();
            } else {
                Log.d("TAG", "cambié a 0");
                values.put(StatusContract.Column_keep.KEEP, 0);
                db2.update(StatusContract.TABLE_KEEP, values, StatusContract.Column_keep.ID+"= 1", null);
                db2.close();
            }
        }
        db.close();
    }

    public boolean mantener(){
        boolean mantener = false;
        String selectQuery = "select " +StatusContract.Column_keep.KEEP+" from " + StatusContract.TABLE_KEEP + " where " +
                StatusContract.Column_keep.ID + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0 && cursor.getString(0).equals("1")) {
            mantener = true;
        }
        db.close();
        return mantener;
    }
}
