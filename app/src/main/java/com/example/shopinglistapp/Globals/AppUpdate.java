package com.example.shopinglistapp.Globals;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.shopinglistapp.BuildConfig;
import com.example.shopinglistapp.Controllers.MainActivity;

import java.io.File;
import java.util.Objects;

public class AppUpdate {
    private Context mContext;
    private static AppUpdate instance;

    public static AppUpdate getInstance(Context context){
        if (instance == null){
            instance = new AppUpdate(context);
        }
        return instance;
    }

    private AppUpdate(Context mContext){
        this.mContext = mContext;
    }

    public void downloadUpdate(String downloadURL){
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUrl = Uri.parse(downloadURL);
        DownloadManager.Request request = new DownloadManager.Request(downloadUrl);
        String title = URLUtil.guessFileName(downloadURL, null, null);
        String destination = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + title;
        Uri uri = Uri.parse("file://" + destination);
        File file = new File(destination);
        if (file.exists()){
            boolean isDeleted =  file.delete();
            Toast.makeText(mContext, "Delete existing file" + isDeleted,Toast.LENGTH_SHORT).show();
        }
        request.setTitle(title);
        request.setDescription("Downloading file...");
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
        request.setDestinationUri(uri);
        install(uri, destination);
        manager.enqueue(request);
        Toast.makeText(mContext, "Download Started...", Toast.LENGTH_SHORT).show();
    }


    private void install(Uri uri, String destination){
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String filePath = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getAbsolutePath();
                    Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", new File(destination));
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(contentUri,
                            "application/vnd.android.package-archive");
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    mContext.startActivity(install);
                } else {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setDataAndType(
                            uri,
                            "\"application/vnd.android.package-archive\""
                    );
                    mContext.startActivity(install);
                    // finish()
                }
                mContext.unregisterReceiver(this);
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
