package com.example.shopinglistapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopinglistapp.Adapters.RecyclerViewAdapter;
import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.Model.Item;
import com.example.shopinglistapp.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    ArrayList<Integer> ids = new ArrayList<>();
    String name,quantity;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> qty = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping_list);
        ShoppingListDbHelper helper = new ShoppingListDbHelper(ShoppingListActivity.this);
        List<Item> allItems = helper.getAllItems();
        Button button = findViewById(R.id.close_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        for(int i = 0; i <allItems.size();i++){
            names.add(allItems.get(i).getName());
            qty.add(allItems.get(i).getQty());
            ids.add(allItems.get(i).getId());
        }
        System.out.println("LOG: " + name + " " + quantity);
        initRecyclerView();
    }

    private void initRecyclerView(){
       RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ids,names,qty,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
