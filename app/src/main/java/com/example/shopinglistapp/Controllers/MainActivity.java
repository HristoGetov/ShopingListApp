package com.example.shopinglistapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shopinglistapp.R;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/shoping_list.json")
                .setDisplay(Display.DIALOG);//.setDuration(Duration.NORMAL);
        appUpdater.setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setIcon(R.drawable.add_button) // Notification icon
                .setCancelable(false);
        appUpdater.start();
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