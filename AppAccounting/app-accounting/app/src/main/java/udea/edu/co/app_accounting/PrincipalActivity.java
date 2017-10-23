package udea.edu.co.app_accounting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import udea.edu.co.app_accounting.helpers.DbHelper;
import udea.edu.co.app_accounting.helpers.Session;
import udea.edu.co.app_accounting.helpers.StatusContract;

public class PrincipalActivity extends AppCompatActivity {

    //declaracion de variables globes
    DbHelper dbHelper;
    SQLiteDatabase db;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    public static final String EXTRA_EMAIL = "emailE";
    public static final String EXTRA_PASS = "pass";
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        dbHelper =new DbHelper(this);//nueva base de datos
        session = new Session(this);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        // TODO: handle navigation
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()){


                            //Replacing the main content with ContentFragment Which is our Inbox View;
                            /*case R.id.info:
                                //Toast.makeText(getApplicationContext(),"Info Selected",Toast.LENGTH_SHORT).show();
                                InformationFragment fragment = new InformationFragment();
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container,fragment);
                                Bundle args = new Bundle();
                                args.putString("myString", getIntent().getExtras().getString(EXTRA_EMAIL));
                                args.putString("myString", getIntent().getExtras().getString(EXTRA_PASS));
                                //fragmentTransaction.set
                                fragmentTransaction.commit();
                                fab.setVisibility(View.INVISIBLE);
                                return true;*/

                            /*case R.id.apart:
                                //Toast.makeText(getApplicationContext(),"Apartment list",Toast.LENGTH_SHORT).show();
                                ListApartmentFragment fragment3 = new ListApartmentFragment();
                                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction3.replace(R.id.fragment_container,fragment3);
                                fragmentTransaction3.commit();
                                //Intent aptActivity = new Intent(MainActivity.this, ApartmentsActivity.class);
                                //startActivity(aptActivity);
                                return true;*/


                            /*case R.id.setting:
                                Intent settingActivity = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(settingActivity);
                                //finish();
                                //fab.setVisibility(View.INVISIBLE);
                                return true;*/

                            // For rest of the options we just show a toast on click

                            case R.id.action_exit:
                                db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from " + StatusContract.TABLE_LOGIN);
                                db.close();
                                session.logout();
                                Intent newActivity = new Intent(PrincipalActivity.this, LoginActivity.class);
                                startActivity(newActivity);
                                finish();
                                return true;

                            default:
                                Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                                return true;

                        }
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            db = dbHelper.getWritableDatabase();
            db.execSQL("delete from " + StatusContract.TABLE_LOGIN);
            db.close();
            Intent newActivity = new Intent(this, LoginActivity.class);
            startActivity(newActivity);
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

}
