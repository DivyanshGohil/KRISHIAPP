package com.example.krishiapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Locale;

import cz.msebera.android.httpclient.client.cache.Resource;

public class NavwithTab extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    TabLayout tabLayout;
    TabItem tab1,tab2,tab3;
    ViewPager viewPager;
    fragmentmanager fragmentManager;

    boolean lang_selected = true;
    Context context;
    Resource resource;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        

        setContentView(R.layout.activity_navwith_tab);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("KRISHI APP");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.ctablayout);
        tab1 = (TabItem)findViewById(R.id.tab1);
        tab2 = (TabItem)findViewById(R.id.tab2);
        tab3 = (TabItem)findViewById(R.id.tab3);

        viewPager = (ViewPager)findViewById(R.id.pageholder);

        drawerLayout = (DrawerLayout)findViewById(R.id.my_drawer);
        navigationView = (NavigationView)findViewById(R.id.cnav);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        fragmentManager = new fragmentmanager(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                tabLayout.getTabCount());
        viewPager.setAdapter(fragmentManager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.my_account: {
                        Intent intent = new Intent(NavwithTab.this, LogInInfo.class);
                        startActivity(intent);
                    }
                        break;
                    case R.id.share:{
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody = "Your body here";
                        String shareSub = "Your Subject here";
                        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(intent,"SHAREVIA"));
                        break;
                    }
                    case R.id.about_us:{
                        Intent intent = new Intent(NavwithTab.this,aboutus.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.Language:{

                        final String[] language = {"ENGLISH","हिंदी"};
                        int checkeditem;

                        if(lang_selected){
                            checkeditem=0;
                        }else{
                            checkeditem=1;
                        }

                        final AlertDialog.Builder builder = new AlertDialog.Builder(NavwithTab.this);
                         builder.setTitle("Select a Language").setSingleChoiceItems(language, checkeditem,
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         if(language[which].equals("ENGLISH")){
                                             context = LocaleHelper.setLocale(NavwithTab.this,"en");
                                             resource = (Resource) context.getResources();
                                             //helloworldtext.settext(resources.getstring(R.string.language));
                                         }
                                         if(language[which].equals("हिंदी")){
                                             context = LocaleHelper.setLocale(NavwithTab.this,"hi");
                                             resource = (Resource) context.getResources();
                                         }
                                     }
                                 }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                             }
                         });

                         builder.create().show();

                    }

                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


    }




}