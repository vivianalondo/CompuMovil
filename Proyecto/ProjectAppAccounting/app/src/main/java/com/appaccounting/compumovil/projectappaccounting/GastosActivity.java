package com.appaccounting.compumovil.projectappaccounting;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryDebit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class GastosActivity extends AppCompatActivity {

    private Button btnRegistrarGasto;
    private boolean control=false;
    EditText[] txtValidate = new EditText[3];
    DbHelper dbH;
    Debit debit;
    User userLogueado;
    private Calendar calendar;
    private int year, month, day;
    MaterialBetterSpinner spinnerCategoryDebit;
    ArrayList categoriesDebit;
    CategoryDebit catdebit;
    ArrayList<String> itemsCategoryDebit = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        userLogueado = new User();

        txtValidate[0]=(EditText)findViewById(R.id.valorGasto);
        txtValidate[1]=(EditText)findViewById(R.id.txtDescripGasto);
        txtValidate[2]=(EditText)findViewById(R.id.txtDateGasto);

        dbH = new DbHelper(this);
        btnRegistrarGasto = (Button)findViewById(R.id.btnEnviarGasto);
        spinnerCategoryDebit = (MaterialBetterSpinner) findViewById(R.id.category_debit_spinner);

        categoriesDebit = dbH.getAllCategoriesDebits();

        for (int i = 0; i < categoriesDebit.size(); i++){
            catdebit = new CategoryDebit();
            catdebit = (CategoryDebit)categoriesDebit.get(i);
            itemsCategoryDebit.add(catdebit.getName());

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemsCategoryDebit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryDebit.setAdapter(adapter);

        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(verificarVaciosSinMessage(txtValidate)){btnRegistrarGasto.setEnabled(true);}
                else{btnRegistrarGasto.setEnabled(false);}
            }
        };

        for (int n = 0; n < txtValidate.length; n++)
        {
            txtValidate[n].addTextChangedListener(btnActivation);
        }

        //Para traer el datepeacker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        saveDate(year, month+1, day);


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    saveDate(arg1, arg2+1, arg3);
                }
    };

    private void saveDate(int year, int month, int day) {
        txtValidate[2].setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    public void saveGastos(View v) throws IOException {
        View focusView=null;
        if (!verificarVacios(txtValidate)){

        }
        else{
            Double amount = Double.parseDouble(txtValidate[0].getText().toString());
            String categorySelect= spinnerCategoryDebit.getText().toString();
            Integer categoryId;
            Integer userId;
            String description = txtValidate[1].getText().toString();
            String date = txtValidate[2].getText().toString();
            CategoryDebit categoryDebitSelect = dbH.getCategoryDebitsByName(categorySelect);

            categoryId = categoryDebitSelect.getId();

            userLogueado = dbH.getLogin();
            userId = userLogueado.getId();
            System.out.println("El id del user logueado es: "+userId);
            debit = new Debit(amount, description, date);
            dbH.addDebit(debit, userId, categoryId);

            //newUser = dbH.addUser(user);
            control=true;
            finish();

        }

    }

    /**
     * Método para verificar vacíos
     * @param txtValidate
     * @return
     */
    public boolean verificarVacios(EditText[] txtValidate)//verificacion de campo requerido
    {
        View focus=null;
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                txtValidate[i].setError(getString(R.string.field_required));
                focus = txtValidate[i];
                return false;
            }
        }
        return true;
    }

    /**
     * Método para verificar vacíos sin mensaje
     * @param txtValidate
     * @return
     */
    public boolean verificarVaciosSinMessage(EditText[] txtValidate)
    {
        View focus=null;
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }
}
