package com.example.hardeepproject.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import java.util.List;

public class MainActivity extends ListActivity {

    private AddDelete users;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new AddDelete(this);
        users.open();

        List values = users.getAllStudents();
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void addUser(View view) {

        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        EditText text = (EditText) findViewById(R.id.editText1);
        Student stud = users.addStudent(text.getText().toString());
        adapter.add(stud);

    }

    public void deleteFirstUser(View view) {

        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        Student stud = null;

        if (getListAdapter().getCount() > 0) {
            stud = (Student) getListAdapter().getItem(0);
            users.deleteStudent(stud);
            adapter.remove(stud);
        }
    }
    @Override
    protected void onResume() {
        users.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        users.close();
        super.onPause();
    }

}