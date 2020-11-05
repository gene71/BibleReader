package com.example.biblereader.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biblereader.R;
import com.example.biblereader.data.DbAccess;
import com.example.biblereader.data.Settings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    Spinner spin;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            setOTCheckBox();

        } catch (Exception e) {
            Log.d("Exceptions", e.getMessage());
        }
    }

    void setOTCheckBox(){

        CheckBox cbOt = (CheckBox) getView().findViewById(R.id.checkBox3);
        cbOt.setChecked(true);//default
        setOTSpinner(1);
        setOTChapter("Genesis"); //Genesis
        Settings.BookName = "Genesis";
        Settings.ChapterNumber = 1;
        CheckBox cbNt = (CheckBox) getView().findViewById(R.id.checkBox4);
        try{
           cbOt.setOnClickListener(new View.OnClickListener(){

               @Override
               public void onClick(View view) {

                   if(cbOt.isChecked()){
                       cbNt.setChecked(false);
                       setOTSpinner(1);
                       setOTChapter("Genesis"); //Genesis
                       Settings.BookName = "Genesis";
                       Settings.ChapterNumber = 1;
                   }else{
                       cbNt.setChecked(true);
                       setOTSpinner(2);
                       setOTChapter("Mathew");//Mathew
                       Settings.BookName = "Mathew";
                       Settings.ChapterNumber = 1;
                   }

                }

           });


            cbNt.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if(cbNt.isChecked()){
                        cbOt.setChecked(false);
                        setOTSpinner(2);
                        setOTChapter("Mathew");//Mathew
                        Settings.BookName = "Mathew";
                        Settings.ChapterNumber = 1;
                    }else{
                        cbOt.setChecked(true);
                        setOTSpinner(1);
                        setOTChapter("Genesis"); //Genesis
                        Settings.BookName = "Genesis";
                        Settings.ChapterNumber = 1;
                    }
                }

            });

        }catch (Exception e){
            Log.d("Exceptions", e.getMessage());
        }

    }

    void setOTSpinner(int range){

        DbAccess dbAccess = new DbAccess(getContext());
        Spinner spin = (Spinner) getView().findViewById(R.id.spinner1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, dbAccess.getBooks(range));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        try{
            spin.setAdapter(arrayAdapter);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Your code here
                    String Book = adapterView.getItemAtPosition(i).toString();

                    int book = i + 1;
                    //DbAccess dbAccess1 = new DbAccess(getContext());
                    //String OTBook = dbAccess.getOTBook(book);//problem here

 ////                   Toast.makeText(getContext(), "spinner selected: " + Book, Toast.LENGTH_LONG).show();
//                    Log.d("test", "spinner item selected: " + OTBook);
                    setOTChapter(Book);
                    Settings.BookName = Book;
                    Settings.ChapterNumber = 1;//set to first chapter on select

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });
            //spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        }catch (Exception e){
            Log.d("Exceptions", e.getMessage());
        }



    }//end setOTSpinner
    void setOTChapter(String BookName){
        DbAccess dbAccess = new DbAccess(getContext());
        Spinner spin = (Spinner) getView().findViewById(R.id.spinner2);



        try{
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_dropdown_item_1line, dbAccess.getBookChapters(BookName));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(arrayAdapter);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Your code here
                    int chapter = i + 1;

                    //String OTBook = dbAccess.getOTBook(book);
                    //Toast.makeText(getContext(), "OTChapters: " + chapter, Toast.LENGTH_LONG).show();
                    //Log.d("test", "spinner item selected: " + chapter);
                    //Settings.BookName = OTBook;
                    Settings.ChapterNumber = chapter;

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });
            //spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        }catch (Exception e){
            Log.d("Exceptions", e.getMessage());
        }


    }//end setOTChapter




}