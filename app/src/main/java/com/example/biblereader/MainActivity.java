package com.example.biblereader;

import android.app.FragmentTransaction;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biblereader.data.DbAccess;
import com.example.biblereader.data.Settings;
import com.example.biblereader.data.Util;
import com.example.biblereader.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GestureDetectorCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.ContextMenu.*;

public class MainActivity extends AppCompatActivity {

    float mRatio = 1.0f;
    int sizeInc = 20;

    Settings settings;

    private AppBarConfiguration mAppBarConfiguration;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private ContextMenu cmenu;
    private View v;
    private ContextMenuInfo menuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings.BookName = "John";
        settings.ChapterNumber = 1;
        DbAccess dbAccess = new DbAccess(this);
        try {
            dbAccess.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);











    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView tv = findViewById(R.id.text_home);
        Util u = new Util();
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.pls_txt:
//                //Toast.makeText(this, "clicked plus text", Toast.LENGTH_SHORT).show();
//                pls_Text();
//                return true;
//            case R.id.min_txt:
//                min_Text();
//                return true;
            case R.id.next_book:
                Toast.makeText(this, "clicked next", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.last_book:
                Toast.makeText(this, "clicked last", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.pls_chapter:
                Toast.makeText(this, "clicked next", Toast.LENGTH_SHORT).show();
                Settings.ChapterNumber += 1;
                ((ScrollView) findViewById(R.id.scrollView2)).post(new Runnable() {
                    public void run() {
                        ((ScrollView) findViewById(R.id.scrollView2)).fullScroll(View.FOCUS_UP);
                    }
                });
                tv.setText(u.updateText(this));
                return true;
            case R.id.min_chapter:
                Toast.makeText(this, "clicked next", Toast.LENGTH_SHORT).show();
                Settings.ChapterNumber -= 1;
                ((ScrollView) findViewById(R.id.scrollView2)).post(new Runnable() {
                    public void run() {
                        ((ScrollView) findViewById(R.id.scrollView2)).fullScroll(View.FOCUS_UP);
                    }
                });
                tv.setText(u.updateText(this));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void pls_Text(){
        TextView tv = findViewById(R.id.text_home);
        sizeInc += 1;
        tv.setTextSize(mRatio + sizeInc);
    }

    void min_Text(){
        TextView tv = findViewById(R.id.text_home);
        sizeInc -= 1;
        tv.setTextSize(mRatio + sizeInc);
    }







}