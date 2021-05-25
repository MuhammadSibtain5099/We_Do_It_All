package com.awais.homeservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    FragmentManager fragmentManager;
    Button drawerButton;
    Button backButton;
    RelativeLayout relativeLayoutToolBar;
    home fragmentHome;
    ProfileFragment fragmentProfile;
    OurServicces fragmentServices;
    Fragment fragment = new Fragment();
    String tag = "";
    SharedPreferences sharedPreferences;
    TextView txtloginAsGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        findView();
        backButton.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        clickListeners();
        initiateFragments();
        inflateInitialFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        View headerview = navigationView.getHeaderView(0);
        txtloginAsGuest = (TextView) headerview.findViewById(R.id.textView2);
        txtloginAsGuest.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            txtloginAsGuest.setVisibility(View.INVISIBLE);
            LoginActivity.isLoginAsGuest=0;
        });
        if (LoginActivity.isLoginAsGuest==1) {
            txtloginAsGuest.setVisibility(View.VISIBLE);
        }else{
           txtloginAsGuest.setVisibility(View.INVISIBLE);
        }

    }

    private void inflateInitialFragment() {
        fragment = fragmentHome;
        pushFragment(fragment, Util.TAG_FRAGMENT_HOME);
    }

    private void setNavigationViewListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = new Fragment();
                drawerCaseChecking(item.getItemId());
                pushFragment(fragment, tag);
                //This is for closing the drawer after acting on it
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void drawerCaseChecking(int itemId) {
        switch (itemId) {

            case R.id.menu_home:
                fragment = fragmentHome;
                tag = Util.TAG_FRAGMENT_HOME;
                break;
            case R.id.service:
                fragment = fragmentServices;
                tag = Util.TAG_FRAGMENT_SERVICE;
                break;
            case R.id.menu_profile:
                fragment = fragmentProfile;
                tag = Util.TAG_FRAGMENT_PROFILE;
                break;
            case R.id.menu_logout:
                tag = Util.TAG_FRAGMENT_PROFILE;
                logout();
                break;
            default:
        }
    }

//    public void replaceFragment(){
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.profil,fragmentProfile, Util.TAG_FRAGMENT_PROFILE);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//    }
    public void pushFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //to check if the fragment has not already been instantiated
        if (!fragment.isAdded()) {
            // fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.nav_host_fragment, fragment, tag);
            fragmentTransaction.addToBackStack(String.valueOf(true));
            fragmentTransaction.commit();
        } else {
            refreshFragment(tag);
        }
    }
    public void refreshFragment(String tag) {
        Fragment fragment;
        fragment = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragment != null) {
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);

            fragmentTransaction.commit();
        }
    }

    private void logout() {
        //to set value of user login boolean to false in Shared Preference so that we can check
        // on app resume whether the user has already logged in or not
       // sharedPreferences = this.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
      //  sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply();
        SharedPrefManager.getInstance(this).logout();
    }
    public void clickListeners() {
        drawerButton.setOnClickListener(v -> openDrawerMenu());
        setNavigationViewListener();

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void openDrawerMenu() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }



    private void findView() {
        drawerButton = findViewById(R.id.drawer_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        relativeLayoutToolBar = findViewById(R.id.app_toolbar);
        backButton = findViewById(R.id.back_button);
        txtloginAsGuest = findViewById(R.id.textView2);

    }

    private void initiateFragments() {
//        fragmentOverallReviews = new FragmentOverallReviews();
//        fragmentProfile = new FragmentProfile();
//
        fragmentServices =new OurServicces();
        fragmentHome = new home();
        fragmentProfile= new ProfileFragment();
    }
}