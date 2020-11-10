package com.example.biblereader.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.biblereader.R;
import com.example.biblereader.data.DbAccess;
import com.example.biblereader.data.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    String book;
    int chapter;
    String bookChpMenu = "";

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
            initContextMenu();
            doSearch(Settings.Query);
            initSearchBox();

            //doSearch();

        } catch (Exception e) {
            Log.d("Exceptions", e.getMessage());
        }
    }

    void initSearchBox(){
        SearchView search = (SearchView) getView().findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try{
                    doSearch(query);
                    Toast.makeText(getContext(), "search:  " + query,Toast.LENGTH_LONG).show();
                    Settings.Query = query;

                }catch (Exception e){
                    Log.d("Exceptions", e.getMessage());
                }



                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getContext(), "on new text " + newText,Toast.LENGTH_LONG).show();

                return false;
            }
        });

    }

    void doSearch(String queryString){

       ListView list = (ListView) getView().findViewById(R.id.listview1);
       DbAccess dbAccess = new DbAccess(getContext());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dbAccess.getQRes(queryString));
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                String[] sa = clickedItem.split("\\.");
                String[] sa1 = sa[0].split(":");
                String[] sa2 = sa1[0].split(" ");
                book = sa2[0];//not surviving state need to fix
                chapter = Integer.parseInt(sa2[1]);


                Toast.makeText(getContext(),"Selected: " + sa2[0] + ":" + sa2[1], Toast.LENGTH_LONG).show();
            }
        });

    }

    void initContextMenu(){
        ListView list = (ListView) getView().findViewById(R.id.listview1);
        registerForContextMenu(list);


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        bookChpMenu = "Set Bible: " + book + ":" + chapter;
        menu.setHeaderTitle("Search Menu");
        if(book != "" && book != null){
            menu.add(0, v.getId(), 0, bookChpMenu);
            menu.add(0, v.getId(), 0, "Add to Notes: " + book + ":" + chapter);
        }else {

            Toast.makeText(getContext(),"PLease Select a List Item", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == bookChpMenu) {
            // do your coding
            //TODO: add check for nulls etc.
            Settings.BookName = book;
            Settings.ChapterNumber = chapter;

            //Toast.makeText(getContext(),"Set Book " + book, Toast.LENGTH_LONG).show();
        }
        else if (item.getTitle() == "") {
            // do your coding


            //Toast.makeText(getContext(),"Set Book " + book, Toast.LENGTH_LONG).show();
        }else{
            return  false;
        }
        return true;
    }

}