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

import com.appaccounting.compumovil.projectappaccounting.Pojo.Budget;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryDebit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryEntrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;
import com.appaccounting.compumovil.projectappaccounting.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by viviana on 15/11/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "appaccounting2.db";
    private Context context;
    private static final int DB_VERSION = 3;
    private static User user = new User();
    File localFile;
    ArrayList<String> fotos;

    private static final String USER_TABLE = "users";
    private static final String LOGIN_TABLE = "login";
    private static final String DEBIT_TABLE = "debits";
    private static final String ENTRIE_TABLE = "entries";
    private static final String KEEP_TABLE = "mantener";
    private static final String BUDGET_TABLE = "presupuesto";
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
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_END_DATE = "endDate";
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
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_CATEGORY_ENTRIE + " INTEGER REFERENCES " + CATEGORY_ENTRIE_TABLE + "(" + COLUMN_ID + "),"
            + COLUMN_USER + " INTEGER REFERENCES " + USER_TABLE + "(" + COLUMN_ID + ")"+ ")";

    private static final String CREATE_TABLE_DEBIT = "CREATE TABLE " + DEBIT_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_CATEGORY_DEBIT + " INTEGER REFERENCES " + CATEGORY_DEBIT_TABLE + "(" + COLUMN_ID + "),"
            + COLUMN_USER + " INTEGER REFERENCES " + USER_TABLE + "(" + COLUMN_ID + "),"
            + COLUMN_DATE + " TEXT)";



    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + BUDGET_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_START_DATE + " TEXT,"
            + COLUMN_END_DATE + " TEXT,"
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
        db.execSQL(CREATE_TABLE_BUDGET);
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
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE);
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


    /**
     * Método para cambier el estado de mantener sesión
     */
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

    /**
     * Método para manterner o no la sesion iniciada
     * @return
     */
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

    /**
     * Método para eliminar el login
     */
    public void deleteLogin(){

        String selectQuery = "delete from " + LOGIN_TABLE ;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }

    /**
     * Método para adicionar débito
     * @param debit
     * @param userID
     * @param categoryDebytID
     */
    public void addDebit(Debit debit, Integer userID, Integer categoryDebytID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AMOUNT, debit.getAmount());
        values.put(COLUMN_DESCRIPTION, debit.getDescription());
        values.put(COLUMN_DATE, debit.getDate());
        values.put(COLUMN_USER, userID);
        values.put(COLUMN_CATEGORY_DEBIT, categoryDebytID);

        long id = db.insert(DEBIT_TABLE, null, values);
        debit.setId((int)id);
        db.close();
    }

    /**
     * Método para adicionar ingreso
     * @param entrie
     * @param userID
     * @param categoryEntrietID
     */
    public void addEntrie(Entrie entrie, Integer userID, Integer categoryEntrietID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AMOUNT, entrie.getAmount());
        values.put(COLUMN_DESCRIPTION, entrie.getDescription());
        values.put(COLUMN_DATE, entrie.getDate());
        values.put(COLUMN_USER, userID);
        values.put(COLUMN_CATEGORY_ENTRIE, categoryEntrietID);

        long id = db.insert(ENTRIE_TABLE, null, values);
        entrie.setId((int)id);
        db.close();
    }

    /**
     * Método para adicionar presupuesto
     * @param budget
     * @param userID
     * @param categoryDebitID
     */
    public void addBudget(Budget budget, String userID, String categoryDebitID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AMOUNT, budget.getAmount());
        values.put(COLUMN_DESCRIPTION, budget.getDescription());
        values.put(COLUMN_START_DATE, budget.getStartDate());
        values.put(COLUMN_END_DATE, budget.getEndDate());
        values.put(COLUMN_USER, Integer.parseInt(userID));
        values.put(COLUMN_CATEGORY_ENTRIE, Integer.parseInt(categoryDebitID));

        long id = db.insert(BUDGET_TABLE, null, values);
        budget.setId((int)id);
        db.close();
    }

    /**
     * Método para agregar una categoría de gasto
     * @param categoryDebit
     */
    public void addCategoryDebit(CategoryDebit categoryDebit) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, categoryDebit.getName());

        long id = db.insert(CATEGORY_DEBIT_TABLE, null, values);
        categoryDebit.setId((int)id);
        db.close();
    }

    /**
     * Método para agregar una categoría de ingreso
     * @param categoryEntrie
     */
    public void addCategoryEntrie(CategoryEntrie categoryEntrie) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, categoryEntrie.getName());

        long id = db.insert(CATEGORY_DEBIT_TABLE, null, values);
        categoryEntrie.setId((int)id);
        db.close();
    }


    /**
     * Método para verificar si hay ingresos
     * @return
     */
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

    /**
     * Método para verificar si hay débitos
     * @return
     */
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

    /**
     * Método para verificar si hay presupuesto
     * @return
     */
    public boolean hayBudgets(){
        boolean hay = false;
        String selectQuery = "select " +COLUMN_ID+" from " + BUDGET_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            hay = true;
        }
        db.close();
        return hay;
    }

    /**
     * Método para verificar si hay categorías de gastos
     * @return
     */
    public boolean hayCategoriesDebits(){
        boolean hay = false;
        String selectQuery = "select " +COLUMN_ID+" from " + CATEGORY_DEBIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            hay = true;
        }
        db.close();
        return hay;
    }

    /**
     * Método para verificar si hay categorías de gastos
     * @return
     */
    public boolean hayCategoriesEntrie(){
        boolean hay = false;
        String selectQuery = "select " +COLUMN_ID+" from " + CATEGORY_ENTRIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            hay = true;
        }
        db.close();
        return hay;
    }

    /**
     * Método para traer todos los gastos
     * @return
     */
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

    /**
     * Método para traer todos los ingresos
     * @return
     */
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

    /**
     * Método para traer todos los presupuestos
     * @return
     */
    public ArrayList<Budget> getAllBudgets(){
        ArrayList <Budget> budgets = new ArrayList<>();
        String selectQuery = "select " +COLUMN_ID+" from " + BUDGET_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    budgets.add(getBudgetByID(cursor.getString(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return budgets;
    }


    /**
     * Método para traer todos las categorías de gastos
     * @return
     */
    public ArrayList<CategoryDebit> getAllCategoriesDebits(){
        ArrayList <CategoryDebit> categoriesDebits = new ArrayList<>();
        String selectQuery = "select " +COLUMN_ID+" from " + CATEGORY_DEBIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    categoriesDebits.add(getCategoryDebitsByID(cursor.getString(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return categoriesDebits;
    }

    /**
     * Método para traer todos las categorías de ingresos
     * @return
     */
    public ArrayList<CategoryEntrie> getAllCategoriesEntries(){
        ArrayList <CategoryEntrie> categoriesEntries = new ArrayList<>();
        String selectQuery = "select " +COLUMN_ID+" from " + CATEGORY_ENTRIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    categoriesEntries.add(getCategoryEntriesByID(cursor.getString(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return categoriesEntries;
    }

    /**
     * Método para encontrar categoría de gastos por ID
     * @param CategoryId
     * @return
     * @throws IOException
     */
    public CategoryDebit getCategoryDebitsByID(String CategoryId) throws IOException {
        CategoryDebit categoryDebit = new CategoryDebit();
        String selectQuery = "select * from " + CATEGORY_DEBIT_TABLE + " where " +
                COLUMN_ID + " = " + "'"+CategoryId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryDebit.setId(Integer.valueOf(cursor.getString(0)));
            categoryDebit.setName(cursor.getString(1));

        }
        db.close();
        return categoryDebit;
    }

    /**
     * Método para encontrar categoría de gastos por ID
     * @param CategoryId
     * @return
     * @throws IOException
     */
    public CategoryEntrie getCategoryEntriesByID(String CategoryId) throws IOException {
        CategoryEntrie categoryEntrie= new CategoryEntrie();
        String selectQuery = "select * from " + CATEGORY_ENTRIE_TABLE + " where " +
                COLUMN_ID + " = " + "'"+CategoryId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryEntrie.setId(Integer.valueOf(cursor.getString(0)));
            categoryEntrie.setName(cursor.getString(1));

        }
        db.close();
        return categoryEntrie;
    }

    /**
     * Método para encontrar categoría de gastos por Nombre
     * @param CategoryName
     * @return
     * @throws IOException
     */
    public CategoryDebit getCategoryDebitsByName(String CategoryName) throws IOException {
        CategoryDebit categoryDebit = new CategoryDebit();
        String selectQuery = "select * from " + CATEGORY_DEBIT_TABLE + " where " +
                COLUMN_NAME + " = " + "'"+CategoryName+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryDebit.setId(Integer.valueOf(cursor.getString(0)));
            categoryDebit.setName(cursor.getString(1));

        }
        db.close();
        return categoryDebit;
    }

    /**
     * Método para encontrar categoría de ingresos por Nombre
     * @param CategoryName
     * @return
     * @throws IOException
     */
    public CategoryEntrie getCategoryEntriesByName(String CategoryName) throws IOException {
        CategoryEntrie categoryEntrie = new CategoryEntrie();
        String selectQuery = "select * from " + CATEGORY_ENTRIE_TABLE + " where " +
                COLUMN_NAME + " = " + "'"+CategoryName+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryEntrie.setId(Integer.valueOf(cursor.getString(0)));
            categoryEntrie.setName(cursor.getString(1));

        }
        db.close();
        return categoryEntrie;
    }

    /**
     * Método para encontrar presupuesto por ID
     * @param BudgetID
     * @return
     * @throws IOException
     */
    public Budget getBudgetByID(String BudgetID) throws IOException {
        Budget budget = new Budget();
        String selectQuery = "select * from " + BUDGET_TABLE + " where " +
                COLUMN_ID + " = " + "'"+BudgetID+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            budget.setId(Integer.valueOf(cursor.getString(0)));
            budget.setAmount(Double.valueOf(cursor.getString(1)));
            budget.setDescription(cursor.getString(2));
            budget.setStartDate(cursor.getString(3));
            budget.setEndDate(cursor.getString(4));
            budget.setUserId(Integer.parseInt(cursor.getString(5)));
            budget.setCategoryDebit(Integer.parseInt(cursor.getString(6)));

            Log.d("Add debit", "El id del usuario es: "+cursor.getString(5));
        }
        db.close();
        return budget;
    }

    /**
     * Método para encontrar gastos por ID
     * @param DebitID
     * @return
     * @throws IOException
     */
    public Debit getDebitByID(String DebitID) throws IOException {
        Debit debit = new Debit();
        String selectQuery = "select * from " + DEBIT_TABLE + " where " +
                COLUMN_ID + " = " + "'"+DebitID+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            debit.setId(Integer.valueOf(cursor.getString(0)));
            debit.setAmount(Double.valueOf(cursor.getString(1)));
            debit.setDescription(cursor.getString(2));
            debit.setDate(cursor.getString(5));
            debit.setUserId(Integer.parseInt(cursor.getString(4)));
            debit.setCategoryDebit(Integer.parseInt(cursor.getString(3)));

            Log.d("Add debit", "El id del usuario es: "+cursor.getString(4));
        }
        db.close();
        return debit;
    }


    /**
     * Método para encontrar ingresos por id
     * @param EntrieID
     * @return
     * @throws IOException
     */
    public Entrie getEntrieByID(String EntrieID) throws IOException {
        Entrie entrie = new Entrie();
        String selectQuery = "select * from " + ENTRIE_TABLE + " where " +
                COLUMN_ID + " = " + "'"+EntrieID+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            entrie.setId(Integer.valueOf(cursor.getString(0)));
            entrie.setAmount(Double.valueOf(cursor.getString(1)));
            entrie.setDescription(cursor.getString(2));
            entrie.setDate(cursor.getString(3));
            entrie.setUserId(Integer.parseInt(cursor.getString(4)));
            entrie.setCategoryEntrie(Integer.parseInt(cursor.getString(5)));

            Log.d("Add entrie", "El id del usuario es: "+cursor.getString(4));
        }
        db.close();
        return entrie;
    }

    /**
     * Método para encontrar grastos por usuario
     * @return
     * @throws IOException
     */
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

    /**
     * Método para encontrar ingresos por usuario
     * @return
     * @throws IOException
     */
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

    /**
     * Método para encontrar presupuesto por usuario
     * @return
     * @throws IOException
     */
    public ArrayList<Budget> getBudgetByUser() throws IOException {
        ArrayList<Budget> budgets = new ArrayList<>();
        String selectQuery = "select "+COLUMN_ID+" from " + BUDGET_TABLE + " where " +
                COLUMN_USER + " = " + "'"+user.getId()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                budgets.add(getBudgetByID(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        db.close();
        return budgets;
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

    /**
     * Método para obtener el login del usuario
     */
    public User getLogin(){
        User userLogin = new User();
        String selectQuery = "select * from " + LOGIN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String email, pass;
        cursor.moveToFirst();

        email = cursor.getString(1);
        Log.d("TAG", "El correo del usuario es: "+email);
        pass = cursor.getString(2);
        userLogin = getUser(email, pass);
        db.close();
        return userLogin;
    }

    /**
     * Método paea adicionar un usuario
     * @param user2
     * @return
     */
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

    /**
     * Método para obtener usuario por email y password
     * @param email
     * @param pass
     * @return
     */
    public User getUser(String email, String pass){
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

            //return true;
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Método para obtener usuario por email
     * @param email
     * @return
     */
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

    /**
     * Método para editar usuario
     * @param user2
     */
    public void updateUser(User user2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user2.getName());
        values.put(COLUMN_EMAIL, user2.getEmail());
        values.put(COLUMN_PASS, user2.getPassword());

        db.update(USER_TABLE, values, COLUMN_ID+"="+user2.getId(), null);
        db.close();
    }

    /**
     * Método para verificar si la contraseña ingresada es correcta
     * @param sEmail
     * @param pass
     * @return
     */
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


    /**
     * Método para obtener usuario
     * @return
     */
    public User getUser2(){
        return user;
    }

    /**
     * Método para generar las categorías de gastos
     */
    public void generateCategoriesDebits(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] categories;
        Resources resources = context.getResources();

        categories = resources.getStringArray(R.array.categories_debits_array);
        /*ArrayList<String> categorias = new ArrayList<>();
        categorias.add("categoria1");
        categorias.add("categoriaN");*/
        ContentValues values;
        for (int i = 0; i < categories.length; i++){
            values = new ContentValues();
            values.put(COLUMN_NAME, categories[i]);
            db.insert(CATEGORY_DEBIT_TABLE, null, values);
        }
        db.close();
    }

    /**
     * Método para generar las categorías de Ingresos
     */
    public void generateCategoriesEntries(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] categories;
        Resources resources = context.getResources();

        categories = resources.getStringArray(R.array.categories_entries_array);
        ContentValues values;
        for (int i = 0; i < categories.length; i++){
            values = new ContentValues();
            values.put(COLUMN_NAME, categories[i]);
            db.insert(CATEGORY_ENTRIE_TABLE, null, values);
        }
        db.close();
    }
}
