package com.example.shopinglistapp.Controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopinglistapp.Globals.AppUpdate;
import com.example.shopinglistapp.R;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.Duration;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

public class  MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor appUpdateVersion;
    private float softwareVersion;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show();
        //Log.e("Log", "Update to version 1.4");
        appUpdateVersion = getSharedPreferences("softwareVersion", Context.MODE_PRIVATE).edit();
        softwareVersion = getSharedPreferences("softwareVersion", MODE_PRIVATE).getFloat("version",1);
        checkForUpdate();
        TextView version = findViewById(R.id.software_version);
        version.setText("Version: " + softwareVersion);
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

    public static MainActivity getInstance() {
        return instance;
    }


    private void checkForUpdate(){
        Log.e("Log", "Call from checkForUpdate method");
        AppUpdaterUtils appUpdaterUtils =  new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json")//
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                       // Toast.makeText(MainActivity.this,"Software version: " + update.getLatestVersion(), Toast.LENGTH_SHORT).show();
                        String latestVersion = update.getLatestVersion();
                        String downloadUrl = update.getUrlToDownload().toString();
                        float lateVersion = Float.parseFloat(latestVersion);
                        if (softwareVersion < lateVersion){
                            Log.e("Log", "Before AppUpdater");
                            AppUpdater appUpdater = new AppUpdater(MainActivity.this);
                            appUpdater.setUpdateFrom(UpdateFrom.JSON)
                                    .setUpdateJSON("https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json")//https://raw.githubusercontent.com/hristogetov/ShopingListApp/master/app/update-changelog.json
                                    .setDisplay(Display.DIALOG);//.showEvery(5);
                            appUpdater.setTitleOnUpdateAvailable("Update available")
                                    .setContentOnUpdateAvailable("Check out the latest version available of ShoppingList app!")
                                    .setButtonUpdate("Update")
                                    .setButtonUpdateClickListener(new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            AppUpdate.getInstance(MainActivity.this).downloadUpdate(downloadUrl);
                                            appUpdateVersion.putFloat("version", lateVersion).apply();
                                        }
                                    })
                                    .setCancelable(true)
                                    .showAppUpdated(true);

                            Log.e("Log", "Before AppUpdater start");
                            appUpdater.start();
                            Log.e("Log", "After AppUpdater start");
                            appUpdateVersion.putFloat("version",lateVersion).apply();
                        }
                        Log.e("Version", softwareVersion + "");
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}