package com.example.shopinglistapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.R;

public class EditItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_item);

        int id = getIntent().getIntExtra("id",0);
        String name = getIntent().getStringExtra("name");
        String qty = getIntent().getStringExtra("qty");
        String idToString = "'" + id + "'";

        EditText item_name = findViewById(R.id.name);
        EditText quantity_item = findViewById(R.id.quantity);
        item_name.setText(name);
        quantity_item.setText(qty);
        ShoppingListDbHelper dbHelper = new ShoppingListDbHelper(EditItemActivity.this);
        Button save = findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.update(idToString,item_name.getText().toString(),quantity_item.getText().toString());
                Intent intent = new Intent(EditItemActivity.this,ShoppingListActivity.class);
                startActivity(intent);
            }
        });



    }
}
