package com.example.biblereader.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblereader.MainActivity;
import com.example.biblereader.R;
import com.example.biblereader.data.DbAccess;
import com.example.biblereader.data.Settings;
import com.example.biblereader.data.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment{

    float mRatio = 1.0f;
    int sizeInc = 20;



    private HomeViewModel homeViewModel;
    //TextView tv;
    //ScrollView sv;
    long lastTap;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final ScrollView sv = root.findViewById(R.id.scrollView2);
        lastTap = System.currentTimeMillis();
        textView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int bottom = textView.getBottom() - 70;
                int right = textView.getRight() - 100;
                int left = textView.getLeft() + 100;

                long timeDif = System.currentTimeMillis() - lastTap;

                if((int)event.getY() > bottom && (int)event.getX() > right && timeDif > 1000){
                    Util u = new Util();
                    u.NextChapter(sv, textView, getContext());
                    lastTap = System.currentTimeMillis();
                    return true;

                }else  if((int)event.getY() > bottom && (int)event.getX() < left && timeDif > 1000){
                    Util u = new Util();
                    u.PrevChapter(sv, textView, getContext());
                    lastTap = System.currentTimeMillis();
                    return true;

                }else {
                    return false;
                }

            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               //textView.setText(s);
                Util u = new Util();
               textView.setText(u.updateText(getContext()));



            }

        });

        registerForContextMenu(sv);







        return root;


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("SV Menu");
        menu.add(0, v.getId(), 0, "+Text");
        menu.add(0, v.getId(), 0, "-Text");
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "+Text") {
            // do your coding
            TextView tv = (TextView) getView().findViewById(R.id.text_home);
            sizeInc += 1;
            tv.setTextSize(mRatio + sizeInc);

            //Toast.makeText(getContext(),"Search selected", Toast.LENGTH_LONG).show();
        }
        if (item.getTitle() == "-Text") {
            // do your coding
            TextView tv = (TextView) getView().findViewById(R.id.text_home);
            sizeInc -= 1;
            tv.setTextSize(mRatio + sizeInc);

            //Toast.makeText(getContext(),"Search selected", Toast.LENGTH_LONG).show();
        }
        else {
            return  false;
        }
        return true;
    }



}