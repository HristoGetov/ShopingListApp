package com.example.shopinglistapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopinglistapp.R;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor appUpdateVersion;
    private float softwareVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Updated to version 2.4", Toast.LENGTH_SHORT).show();
        //Log.e("Log", "Update to version 1.4");
        appUpdateVersion = getSharedPreferences("softwareVersion", Context.MODE_PRIVATE).edit();
        softwareVersion = getSharedPreferences("softwareVersion", MODE_PRIVATE).getFloat("version",1);
        TextView version = findViewById(R.id.software_version);
        version.setText("Version: " + softwareVersion);

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json")
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                       // Toast.makeText(MainActivity.this,"Software version: " + update.getLatestVersion(), Toast.LENGTH_SHORT).show();
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        String latestVersion = update.getLatestVersion();
                        float lateVersion = Float.parseFloat(latestVersion);
                        if (softwareVersion < lateVersion){
                            appUpdateVersion.putFloat("version",lateVersion).apply();
                            AppUpdater appUpdater = new AppUpdater(MainActivity.this);
                            appUpdater.setUpdateFrom(UpdateFrom.JSON)
                                    .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json")
                                    .setDisplay(Display.DIALOG);//.showEvery(5);
                            appUpdater.setTitleOnUpdateAvailable("Update available")
                                    .setContentOnUpdateAvailable("Check out the latest version available of ShoppingList app!")
                                    .setButtonUpdate("Update")
                                    .setCancelable(true)
                                    .showAppUpdated(true);
                            appUpdater.start();
                        }
                        Log.e("Version", softwareVersion + "");
                    }
                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });

        appUpdaterUtils.start();
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