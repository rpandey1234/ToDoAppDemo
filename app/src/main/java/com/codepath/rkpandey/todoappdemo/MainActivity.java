package com.codepath.rkpandey.todoappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    EditText etTodo;
    RecyclerView rvItems;

    List<String> items;

    // Add RecyclerView AndroidX library to the Gradle build file DONE
    //Define a model class to use as the data source DONE
    //Add a RecyclerView to your activity to display the items DONE
    //Create a custom row layout XML file to visualize the item DONE- we'll use the built in
    //Create a RecyclerView.Adapter and ViewHolder to render the item
    //Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        etTodo = findViewById(R.id.etTodo);
        rvItems = findViewById(R.id.rvItems);

        items = new ArrayList<>();
        items.add("Hello");
        items.add("World");

        rvItems.setLayoutManager(new LinearLayoutManager(this));
        ItemsAdapter itemsAdapter = new ItemsAdapter(this, items);
        rvItems.setAdapter(itemsAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = etTodo.getText().toString();
                etTodo.setText("");
                // Add the new item to the list
                items.add(itemText);
                Toast.makeText(MainActivity.this, "Added item", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
