package com.example.shopinglistapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.Model.Item;
import com.example.shopinglistapp.Model.Type;
import com.example.shopinglistapp.R;


public class NewItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


        Type type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Spinner typesSpinner = findViewById(R.id.types_spinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(NewItemActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.types));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typeAdapter);
        typesSpinner.setOnItemSelectedListener(this);


    }

    public void onSafe(View view){
        EditText nameText = findViewById(R.id.name);
        EditText quantityText = findViewById(R.id.quantity);
        ShoppingListDbHelper helper = new ShoppingListDbHelper(NewItemActivity.this);
        Item item = new Item(nameText.getText().toString(),quantityText.getText().toString(),type);
        String result = helper.addItem(item);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onCancel(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        switch (text){
            case "кг.":
                type = Type.KILO;
                break;
            case "г.":
                type = Type.GRAM;
                break;
            case "л.":
                type = Type.LITRE;
                break;
            case "кутии":
                type = Type.CAN;
                break;
            case "бутилки":
                type = Type.BOTTLE;
                break;
            case "бр.":
                type = Type.BROI;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
