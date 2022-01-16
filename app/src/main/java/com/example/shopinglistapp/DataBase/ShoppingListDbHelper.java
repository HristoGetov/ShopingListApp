package com.example.shopinglistapp.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shopinglistapp.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDbHelper extends SQLiteOpenHelper {


    public static final String ITEMS_TABLE = "Items";
    public static final String ITEM_ID_COLUMN = "item_id";
    public static final String ITEM_NAME_COLUMN = "item_name";
    public static final String QUANTITY_COLUMN = "quantity";

    public ShoppingListDbHelper(@Nullable Context context) {
        super(context, "shoppingList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createItemTable = "CREATE TABLE " + ITEMS_TABLE + " ( " + ITEM_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME_COLUMN
                + " VARCHAR(50), " + QUANTITY_COLUMN + " INTEGER);";

        sqLiteDatabase.execSQL(createItemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_COLUMN,item.getName());
        contentValues.put(QUANTITY_COLUMN,item.getQty());
        database.insert(ITEMS_TABLE,null,contentValues);
        return "An item was added!";
    }

    public boolean deleteItem(Item item){

        String deleteItem= "Delete from " + ITEMS_TABLE + " where " + ITEM_ID_COLUMN + " = " + item.getId();
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(deleteItem, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public List<Item> getAllItems(){
        List<Item> items = new ArrayList<>();

        String getAllItems = "SELECT * FROM " + ITEMS_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(getAllItems,null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String quantity = cursor.getString(2);

                Item returnedItem = new Item(id, name, quantity);
                items.add(returnedItem);
            } while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        database.close();
        return items;
    }

    public void update(String id,String name, String qty){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME_COLUMN,name);
        cv.put(QUANTITY_COLUMN,qty);
        db.update(ITEMS_TABLE,cv," item_id =  " + id,null);
        db.close();

    }



}
