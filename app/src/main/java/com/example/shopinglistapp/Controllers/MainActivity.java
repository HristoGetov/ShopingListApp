package com.example.shopinglistapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopinglistapp.R;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("LOG", "START APP");
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json")
                .setDisplay(Display.DIALOG);//.setDuration(Duration.NORMAL);
        appUpdater.setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setIcon(R.drawable.ic_launcher_foreground) // Notification icon
                .setCancelable(false);
        appUpdater.start();

        Button newItem = findViewById(R.id.new_item);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewItemActivity.class);
                startActivity(intent);
            }
        });
        Button shoppingList = findViewById(R.id.list_button);
        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
                startActivity(intent);
            }
        });
       // Toast.makeText(this,"Updated successfully", Toast.LENGTH_LONG).show();
        getSupportActionBar().hide();
    }

    public void openNewItem(View view){
        Intent intent = new Intent(this,NewItemActivity.class);
        startActivity(intent);
    }
    public void openShoppingList(View view){
        Intent intent = new Intent(this,ShoppingListActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}