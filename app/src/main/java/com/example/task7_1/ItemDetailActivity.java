package com.example.task7_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {

    private TextView tvItemName, tvItemDetails;
    private Button btnRemove;
    private DatabaseHelper databaseHelper;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvItemName = findViewById(R.id.tvItemName);
        tvItemDetails = findViewById(R.id.tvItemDetails);
        btnRemove = findViewById(R.id.btnRemove);
        databaseHelper = new DatabaseHelper(this);

        itemName = getIntent().getStringExtra("itemName");
        Item item = databaseHelper.getItemByName(itemName);

        if (item != null) {
            tvItemName.setText(item.getName());
            String details = "Status: " + item.getStatus() + "\nPhone: " + item.getPhone() +
                    "\nDate: " + item.getDate() + "\nLocation: " + item.getLocation() +
                    "\nDescription: " + item.getDescription();
            tvItemDetails.setText(details);
        }

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteItem(itemName);
                Intent intent = new Intent(ItemDetailActivity.this, ListItemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
