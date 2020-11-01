package com.example.biblereader.data;

import android.content.Context;
import android.database.SQLException;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.biblereader.R;

import java.util.ArrayList;

public class Util {

    public String updateText(Context context){
        DbAccess da = new DbAccess(context);
        try {
            da.openDataBase();
            //Toast.makeText(this.getContext(), "Successfully opened database", Toast.LENGTH_SHORT).show();

            ArrayList<String> chp = da.getChapter(Settings.BookName, Settings.ChapterNumber);
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for(String verse : chp){
                sb.append(Settings.BookName + " " + Settings.ChapterNumber + ":"
                        + i + "." + verse + " \r\n\r\n");
                i++;
            }

            return sb.toString();

        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    public void NextChapter(ScrollView sv, TextView tv, Context context){

        Settings.ChapterNumber += 1;
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(View.FOCUS_UP);
            }
        });
        tv.setText(updateText(context));

    }

    public void PrevChapter(ScrollView sv, TextView tv, Context context){

        Settings.ChapterNumber -= 1;
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(View.FOCUS_UP);
            }
        });
        tv.setText(updateText(context));

    }


}
