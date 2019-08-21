package com.codepath.rkpandey.todoappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // a numeric code to identify the edit activity
    public static final int EDIT_REQUEST_CODE = 20;
    // key used for passing data between activities
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    Button btnAdd;
    EditText etTodo;
    RecyclerView rvItems;

    List<String> items;
    ItemsAdapter adapter;

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

        readItems();
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Log.i("MainActivity", "Long press at position " + position);
                items.remove(position);
                adapter.notifyItemRemoved(position);
                writeItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                Log.d("MainActivity", "Singe press at position " + position);
                // create the new activity
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                // pass the data being edited
                intent.putExtra(ITEM_POSITION, position);
                intent.putExtra(ITEM_TEXT, items.get(position));
                // display the activity
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        };

        adapter = new ItemsAdapter(this, items, onLongClickListener, onClickListener);
        rvItems.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = etTodo.getText().toString();
                if (itemText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Cannot add an empty item", Toast.LENGTH_SHORT).show();
                    return;
                }
                etTodo.setText("");
                // Add the new item to the list
                items.add(itemText);
                adapter.notifyItemInserted(items.size() - 1);
                Toast.makeText(MainActivity.this, "Added item", Toast.LENGTH_SHORT).show();
                writeItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // if the edit activity completed ok
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            // Update the list with the updated item text
            String itemText = data.getStringExtra(ITEM_TEXT);
            // extract original position of the edited item
            int position = data.getIntExtra(ITEM_POSITION, 0);
            // update the model with the new item text at the edited position
            items.set(position, itemText);
            // notify the adapter
            adapter.notifyItemChanged(position);
            // persist the changes
            writeItems();
            // notify the user that the operation completed successfully
            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file", e);
        }
    }
}
