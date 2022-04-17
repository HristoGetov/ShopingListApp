package com.example.shopinglistapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.Model.Type;
import com.example.shopinglistapp.R;

public class EditItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_item);
        Spinner typesSpinner = findViewById(R.id.types_spinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(EditItemActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.types));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typeAdapter);
        typesSpinner.setOnItemSelectedListener(this);

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
                dbHelper.update(idToString,item_name.getText().toString(),quantity_item.getText().toString(),type);
                Intent intent = new Intent(EditItemActivity.this,ShoppingListActivity.class);
                startActivity(intent);
            }
        });
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

    public void onCancel(View view){
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }
}
