package com.appaccounting.compumovil.projectappaccounting;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Helpers.Session;

public class PrincipalActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    public static final String EXTRA_EMAIL = "emailE";
    public static final String EXTRA_PASS = "pass";
    private Session session;
    DbHelper dbH;
    private PrincipalActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        dbH = new DbHelper(this);
        session = new Session(this);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PrincipalActivity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container1);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);

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

                            case R.id.movements:
                                Intent ppalActivity = new Intent(PrincipalActivity.this, PrincipalActivity.class);
                                startActivity(ppalActivity);
                                //finish();
                                //fab.setVisibility(View.INVISIBLE);
                                return true;


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
                                dbH.deleteLogin();
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
            dbH.deleteLogin();
            Intent newActivity = new Intent(this, LoginActivity.class);
            startActivity(newActivity);
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    /*public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PrincipalActivity.PlaceholderFragment newInstance(int sectionNumber) {
            PrincipalActivity.PlaceholderFragment fragment = new PrincipalActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    PrincipalFragment principalFragment = new PrincipalFragment();
                    return principalFragment;
                case 1:
                    TransactionFragment transactionFragment = new TransactionFragment();
                    return transactionFragment;
                case 2:
                    budgetFragment budFragment = new budgetFragment();
                    return budFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PRINCIPAL";
                case 1:
                    return "MOVIMIENTOS";
                case 2:
                    return "PRESUPUESTO";
                case 3:
                    return "REPORTES";
            }
            return null;
        }
    }


}
