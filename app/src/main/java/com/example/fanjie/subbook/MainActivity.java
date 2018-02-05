package com.example.fanjie.subbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "sub.txt";
    private ListView listview;
    private TextView textclick;
    private EditText name, date, charge, comment, totalch;
    private listadapter listadapter1;
    private ArrayList<subscription> sublist;
    private int pos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.namet);
        date = (EditText) findViewById(R.id.datet);
        charge = (EditText) findViewById(R.id.charget);
        comment = (EditText) findViewById(R.id.commentt);
        totalch = (EditText) findViewById(R.id.totalc);
        final Button addbutton = (Button) findViewById(R.id.addbutton);
        final Button donebutton = (Button) findViewById(R.id.Donebutton);
        final Button deletebutton = (Button) findViewById(R.id.deletebutton);
        donebutton.setVisibility(View.INVISIBLE);
        deletebutton.setVisibility(View.INVISIBLE);

        listview = (ListView) findViewById(R.id.list);
        sublist = new ArrayList<subscription>();

        loadFromFile();
        listadapter1 = new listadapter(MainActivity.this, R.layout.list, sublist);
        listview.setAdapter(listadapter1);
        totalch.setText(Addcharge(sublist));

        listadapter1.setOnClickListener(new listadapter.onClickListenertext() {
            @Override
            public void Clicktext(int position) {
                pos1=position;
                name.setText(sublist.get(position).getName());
                date.setText(sublist.get(position).getDate());
                charge.setText(String.valueOf(sublist.get(position).getPrice()));
                comment.setText(sublist.get(position).getComments());

                addbutton.setVisibility(View.INVISIBLE);
                donebutton.setVisibility(View.VISIBLE);
                deletebutton.setVisibility(View.VISIBLE);

            }
        });



        addbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String names = name.getText().toString();
                String dates = date.getText().toString();
                String charges = charge.getText().toString();
                String comments = comment.getText().toString();

                if ((CheckInput(dates, charges, date, charge ) == true)){
                    subscription subs = new subscription(names, dates, charges, comments );
                    sublist.add(subs);
                    totalch.setText(Addcharge(sublist));
                    saveInFile();


                }
            }
        });

        donebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                String dates = date.getText().toString();
                String charges = charge.getText().toString();

                if ((CheckInput(dates, charges, date, charge ) == true)){
                    sublist.get(pos1).setSub(name.getText().toString(), date.getText().toString(), charge.getText().toString(), comment.getText().toString());
                    totalch.setText(Addcharge(sublist));
                    saveInFile();
                }
                addbutton.setVisibility(View.VISIBLE);
                donebutton.setVisibility(View.INVISIBLE);
                deletebutton.setVisibility(View.INVISIBLE);

            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sublist.remove(pos1);
                
                totalch.setText(Addcharge(sublist));

                saveInFile();
            }
        });


    }



    /**
     * load from file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //taken from stakcoverflow//
            //2018-01-25//
            Type listType = new TypeToken<ArrayList<subscription>>(){}.getType();
            sublist = gson.fromJson(in, listType);



        } catch (FileNotFoundException e) {
            sublist = new ArrayList<subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * save the text and date in the file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(sublist, out);
            out.flush();


        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private Boolean CheckInput(String date, String charge, EditText datet, EditText charget){
        Boolean ifdate = date.matches("\\d{4}/\\d{2}/\\d{2}");
        if (ifdate == false){
            datet.setError("please enter date in the format of yyyy/mm/dd");
            return false;
        }
        else if (Float.parseFloat(charge)<0){
            charget.setError("we do not accept negative charge");
            return false;
        }
        return true;
    }
    private String Addcharge(ArrayList<subscription> sublist){
        float total = 0;
        for (int i = 0; i < sublist.size(); i++){
            total +=sublist.get(i).getPrice();
        }
        return Float.toString(total);
    }



}
