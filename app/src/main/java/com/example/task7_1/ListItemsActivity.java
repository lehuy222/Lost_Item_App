package com.example.task7_1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListItemsActivity extends Activity {

    private ListView lvItems;
    private ArrayAdapter<Item> itemsAdapter;
    private ArrayList<Item> itemList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        lvItems = findViewById(R.id.lvItems);
        itemList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        // Fetch the items from the database
        itemList = databaseHelper.getAllItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ListItemsActivity.this, ItemDetailActivity.class);
                // Send item name or ID to the detail view
                intent.putExtra("itemName", itemList.get(position).getName());
                startActivity(intent);
            }
        });
    }
}