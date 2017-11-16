package com.appaccounting.compumovil.projectappaccounting.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by viviana on 15/11/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private Context context;
    private static final int DB_VERSION = 2;
    private static User user = new User();
    File localFile;
    ArrayList<String> fotos;

    private static final String USER_TABLE = "users";
    private static final String LOGIN_TABLE = "login";
    private static final String DEBIT_TABLE = "debits";
    private static final String ENTRIE_TABLE = "entries";
    private static final String KEEP_TABLE = "mantener";
    private static final String CATEGORY_DEBIT_TABLE = "categoriesDebit";
    private static final String CATEGORY_ENTRIE_TABLE = "categoriesEntrie";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";
    private static final String COLUMN_PASS_CONF = "passwordConf";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_USER = "userFK";
    private static final String COLUMN_KEEP = "keep";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY_DEBIT = "categoryDebitFK";
    private static final String COLUMN_CATEGORY_ENTRIE = "categoryEntrieFK";
    //private StorageReference mStorageRef;



    private static final String CREATE_TABLE_KEEP = "CREATE TABLE " + KEEP_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_KEEP + " TEXT)";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT)";

    private static final String CREATE_TABLE_CATEGORY_DEBIT = "CREATE TABLE " + CATEGORY_DEBIT_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT)";

    private static final String CREATE_TABLE_CATEGORY_ENTRIE = "CREATE TABLE " + CATEGORY_ENTRIE_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT)";

    private static final String CREATE_TABLE_ENTRIE = "CREATE TABLE " + ENTRIE_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + "TEXT,"
            + COLUMN_CATEGORY_ENTRIE + " INTEGER REFERENCES " + CATEGORY_ENTRIE_TABLE + "(" + COLUMN_ID + "),"
            + COLUMN_USER + " INTEGER REFERENCES " + USER_TABLE + "(" + COLUMN_ID + ")"+ ")";

    private static final String CREATE_TABLE_DEBIT = "CREATE TABLE " + DEBIT_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + "TEXT,"
            + COLUMN_CATEGORY_DEBIT + " INTEGER REFERENCES " + CATEGORY_DEBIT_TABLE + "(" + COLUMN_ID + "),"
            + COLUMN_USER + " INTEGER REFERENCES " + USER_TABLE + "(" + COLUMN_ID + ")"+ ")";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + LOGIN_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT)";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "CREAR");
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_ENTRIE);
        db.execSQL(CREATE_TABLE_DEBIT);
        db.execSQL(CREATE_TABLE_KEEP);
        db.execSQL(CREATE_TABLE_CATEGORY_DEBIT);
        db.execSQL(CREATE_TABLE_CATEGORY_ENTRIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TAG", "Borrar");
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DEBIT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ENTRIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KEEP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_DEBIT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_ENTRIE_TABLE);
        onCreate(db);
    }

    public Integer insertUser(){
        Integer id;
        User usuario = new User();
        usuario.setName("desconocido");
        usuario.setEmail("desconocido");
        usuario.setPassword("desconocido");
        id = addUser(usuario);
        Log.d("TAG", "inserté usuario");
        return id;
    }


    public void changeKeep(){
        String selectQuery = "select * from " + KEEP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ContentValues values = new ContentValues();
        int uno = 1;
        if (cursor.getCount() == 0){
            values.put(COLUMN_KEEP, "1");
            db2.insert(KEEP_TABLE, null, values);
            db2.close();
        } else {
            cursor.moveToFirst();
            if (cursor.getString(1).equals("0")){
                values.put(COLUMN_KEEP, "1");
                db2.update(KEEP_TABLE, values, COLUMN_ID+"= "+uno, null);
                db2.close();
            } else {
                values.put(COLUMN_KEEP, "0");
                db2.update(KEEP_TABLE, values, COLUMN_ID+"="+uno, null);
                db2.close();
            }
        }
        db.close();
    }

    public boolean mantener(){
        boolean mantener = false;
        String selectQuery = "select " +COLUMN_KEEP+" from " + KEEP_TABLE + " where " +
                COLUMN_ID + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0 && cursor.getString(0).equals("1")) {
            mantener = true;
        }
        db.close();
        return mantener;
    }

    /**
     * Storing user details in database
     * */

    public void addLogin(String email, String pass){
        String selectQuery = "select * from " + LOGIN_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, pass);
        if (cursor.getCount() != 0){
            SQLiteDatabase db2 = this.getWritableDatabase();
            String cero = "0";
            db2.update(LOGIN_TABLE, values, COLUMN_ID+"="+cero, null);
            db2.close();
        } else {
            db.insert(LOGIN_TABLE, null, values);
            db.close();
        }
    }

    public void deleteLogin(){

        String selectQuery = "delete from " + LOGIN_TABLE ;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(selectQuery);
        db.close();

    }

    public void addDebit(Debit debit, String userID, String categoryDebytID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, debit.getName());
        values.put(COLUMN_AMOUNT, debit.getAmount());
        values.put(COLUMN_DESCRIPTION, debit.getDescription());
        values.put(COLUMN_DATE, debit.getDate());
        values.put(COLUMN_USER, Integer.parseInt(userID));
        values.put(COLUMN_CATEGORY_DEBIT, Integer.parseInt(categoryDebytID));

        long id = db.insert(DEBIT_TABLE, null, values);
        debit.setId((int)id);
        db.close();
    }

    public void addEntrie(Entrie entrie, String userID, String categoryEntrietID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, entrie.getName());
        values.put(COLUMN_AMOUNT, entrie.getAmount());
        values.put(COLUMN_DESCRIPTION, entrie.getDescription());
        values.put(COLUMN_DATE, entrie.getDate());
        values.put(COLUMN_USER, Integer.parseInt(userID));
        values.put(COLUMN_CATEGORY_ENTRIE, Integer.parseInt(categoryEntrietID));

        long id = db.insert(ENTRIE_TABLE, null, values);
        entrie.setId((int)id);
        db.close();
    }


    public boolean hayEntries(){
        boolean hay = false;
        String selectQuery = "select " +COLUMN_ID+" from " + ENTRIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            hay = true;
        }
        db.close();
        return hay;
    }

    public boolean hayDebits(){
        boolean hay = false;
        String selectQuery = "select " +COLUMN_ID+" from " + DEBIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            hay = true;
        }
        db.close();
        return hay;
    }

    public ArrayList<Debit> getAllDebits(){
        ArrayList <Debit> debits = new ArrayList<>();
        String selectQuery = "select " +COLUMN_ID+" from " + DEBIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    debits.add(getDebitByID(cursor.getString(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return debits;
    }

    public ArrayList<Entrie> getAllEntries(){
        ArrayList <Entrie> entries = new ArrayList<>();
        String selectQuery = "select " +COLUMN_ID+" from " + ENTRIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    entries.add(getEntrieByID(cursor.getString(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return entries;
    }

    public Debit getDebitByID(String DebitID) throws IOException {
        Debit debit = new Debit();
        String selectQuery = "select * from " + DEBIT_TABLE + " where " +
                COLUMN_ID + " = " + "'"+DebitID+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            debit.setId(Integer.valueOf(cursor.getString(0)));
            debit.setName(cursor.getString(1));
            debit.setAmount(Double.valueOf(cursor.getString(2)));
            debit.setDescription(cursor.getString(3));
            debit.setDate(cursor.getString(4));
            debit.setUserId(Integer.parseInt(cursor.getString(3)));
            debit.setCategoryDebit(Integer.parseInt(cursor.getString(3)));

            Log.d("Add debit", "El id del usuario es: "+cursor.getString(7));
        }
        db.close();
        return debit;
    }

    public Entrie getEntrieByID(String EntrieID) throws IOException {
        Entrie entrie = new Entrie();
        String selectQuery = "select * from " + ENTRIE_TABLE + " where " +
                COLUMN_ID + " = " + "'"+EntrieID+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            entrie.setId(Integer.valueOf(cursor.getString(0)));
            entrie.setName(cursor.getString(1));
            entrie.setAmount(Double.valueOf(cursor.getString(2)));
            entrie.setDescription(cursor.getString(3));
            entrie.setDate(cursor.getString(4));
            entrie.setUserId(Integer.parseInt(cursor.getString(3)));
            entrie.setCategoryDebit(Integer.parseInt(cursor.getString(3)));

            Log.d("Add entrie", "El id del usuario es: "+cursor.getString(7));
        }
        db.close();
        return entrie;
    }

    public ArrayList<Debit> getDebitByUser() throws IOException {
        ArrayList<Debit> debits = new ArrayList<>();
        String selectQuery = "select "+COLUMN_ID+" from " + DEBIT_TABLE + " where " +
                COLUMN_USER + " = " + "'"+user.getId()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                debits.add(getDebitByID(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        db.close();
        return debits;
    }

    public ArrayList<Entrie> getEntrieByUser() throws IOException {
        ArrayList<Entrie> entries = new ArrayList<>();
        String selectQuery = "select "+COLUMN_ID+" from " + ENTRIE_TABLE + " where " +
                COLUMN_USER + " = " + "'"+user.getId()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                entries.add(getEntrieByID(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        db.close();
        return entries;
    }



    /*public ArrayList<Debit> search (String busqueda){
        ArrayList<Debit> allDebits = getAllDebits();
        ArrayList<Debit> debits = new ArrayList<>();
        Debit debit;
        for (int i = 0; i < allDebits.size(); i++) {
            debit = allDebits.get(i);
            if (apto.getTitulo().contains(busqueda) || apto.getValor().equals(busqueda)
                    || apto.getArea().equals(busqueda) || apto.getDescripcion().contains(busqueda)
                    || apto.getHabitaciones().equals(busqueda) || apto.getUbicacion().contains(busqueda)
                    || apto.getTipo().equals(busqueda)){
                aptos.add(allAptos.get(i));
            }
        }
        return aptos;
    }*/

    public void getLogin(){
        String selectQuery = "select * from " + LOGIN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String email, pass;
        cursor.moveToFirst();
        email = cursor.getString(1);
        Log.d("TAG", "El correo del usuario es: "+email);
        pass = cursor.getString(2);
        getUser(email, pass);
        db.close();
    }

    public Integer addUser(User user2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user2.getName());
        values.put(COLUMN_EMAIL, user2.getEmail());
        values.put(COLUMN_PASS, user2.getPassword());
        long id = db.insert(USER_TABLE, null, values);
        user2.setId((int)id);
        db.close();
        return user2.getId();
    }

    public boolean getUser(String email, String pass){
        String selectQuery = "select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));

            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean getUserEmail(String email){
        String selectQuery = "select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public void updateUser(User user2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user2.getName());
        values.put(COLUMN_EMAIL, user2.getEmail());
        values.put(COLUMN_PASS, user2.getPassword());

        db.update(USER_TABLE, values, COLUMN_ID+"="+user2.getId(), null);
        db.close();
    }

    public boolean validatePassword(String sEmail, String pass){

        String selectQuery = "select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+sEmail+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String validation="";
        if (cursor.moveToFirst()){
            do {
                validation = cursor.getString(3);
                System.out.println("la contraseña es:"+validation);
            }while (cursor.moveToNext());
        }
        db.close();
        if (pass.equals(validation)){return true;}
        return false;
    }


    public User getUser2(){
        return user;
    }
}
