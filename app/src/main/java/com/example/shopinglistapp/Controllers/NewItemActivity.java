package com.example.shopinglistapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.Model.Item;
import com.example.shopinglistapp.R;


public class NewItemActivity extends AppCompatActivity {

private String name;
private String quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_new);


    }

    public void onSafe(View view){
        EditText nameText = findViewById(R.id.name);
        EditText quantityText = findViewById(R.id.quantity);
        ShoppingListDbHelper helper = new ShoppingListDbHelper(NewItemActivity.this);
        Item item = new Item(nameText.getText().toString(),quantityText.getText().toString());

        String result = helper.addItem(item);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onCancel(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
