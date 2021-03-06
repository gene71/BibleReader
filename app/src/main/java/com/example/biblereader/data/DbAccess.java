package com.example.biblereader.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbAccess extends SQLiteOpenHelper {
    String DB_PATH = null;
    private static String DB_NAME = "scripturesv1";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DbAccess(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("scriptures", null, null, null, null, null, null);
    }

    public ArrayList getChapter(String book, int chapter) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "Select VerseText from scriptures where BookName = '" + book + "' and ChapterNumber = " + chapter + ";", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("VerseText")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getOTBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "SELECT DISTINCT BookName FROM scriptures Where BookNumber < 47;", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("BookName")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getBooks(int i) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        //get OTBooks
        if(i == 1){
            Cursor res = db.rawQuery( "SELECT DISTINCT BookName FROM scriptures Where BookNumber < 47;", null);
            res.moveToFirst();
            while(res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex("BookName")));
                res.moveToNext();
            }
        }

        //get NTBooks
        if(i == 2){
            Cursor res = db.rawQuery( "SELECT DISTINCT BookName FROM scriptures Where BookNumber > 46;", null);
            res.moveToFirst();
            while(res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex("BookName")));
                res.moveToNext();
            }
        }


        return array_list;
    }

    public String getOTBook(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String OTBook = "Genesis";
        Cursor res = db.rawQuery( "SELECT DISTINCT BookName FROM scriptures Where BookNumber = " + i + ";", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            OTBook = res.getString(res.getColumnIndex("BookName"));
            res.moveToNext();
        }
        return OTBook;
    }

//    public ArrayList getBookChapters(int i) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<Integer> array_list = new ArrayList<Integer>();
//        Cursor res = db.rawQuery( "SELECT DISTINCT ChapterNumber FROM scriptures Where BookNumber = " + i + ";", null);
//        res.moveToFirst();
//        while(res.isAfterLast() == false) {
//            array_list.add(res.getInt(res.getColumnIndex("ChapterNumber")));
//            res.moveToNext();
//        }
//        return array_list;
//    }

    public ArrayList getBookChapters(String BookName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        Cursor res = db.rawQuery( "SELECT DISTINCT ChapterNumber FROM scriptures Where BookName = '" + BookName + "';", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getInt(res.getColumnIndex("ChapterNumber")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getQRes(String queryString) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<>();
        Cursor res = db.rawQuery( "SELECT BookName, ChapterNumber, VerseNumber, VerseText FROM scriptures Where VerseText LIKE '% " + queryString + "%';", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {

            StringBuilder sb = new StringBuilder();
            sb.append(res.getString(res.getColumnIndex("BookName")));
            sb.append(" ");
            sb.append(res.getString(res.getColumnIndex("ChapterNumber")));
            sb.append(":");
            sb.append(res.getString(res.getColumnIndex("VerseNumber")));
            sb.append(". ");
            sb.append(res.getString(res.getColumnIndex("VerseText")));



            array_list.add(sb.toString());
            res.moveToNext();
        }
        return array_list;
    }


}
