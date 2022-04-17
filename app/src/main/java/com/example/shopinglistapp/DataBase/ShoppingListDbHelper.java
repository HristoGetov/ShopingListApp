package com.example.shopinglistapp.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shopinglistapp.Model.Item;
import com.example.shopinglistapp.Model.Type;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDbHelper extends SQLiteOpenHelper {


    private static final String ITEMS_TABLE = "Items";
    private static final String ITEM_ID_COLUMN = "item_id";
    private static final String ITEM_NAME_COLUMN = "item_name";
    private static final String QUANTITY_COLUMN = "quantity";
    private static final String TYPE_COLUMN = "type";
    private static final String createItemTable = "CREATE TABLE " + ITEMS_TABLE + " ( " + ITEM_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME_COLUMN
            + " VARCHAR(50), " + QUANTITY_COLUMN + " INTEGER, " + TYPE_COLUMN + " VARCHAR(50));";

    private static final String RECIPE_TABLE = "recipe_table";
    private static final String RECIPE_ID = "recipe_id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String PREPARATION_TIME = "prep_time";
    private static final String COOK_TIME = "cook_time";
    private static final String CREATE_RECIPE = "CREATE TABLE " + RECIPE_TABLE + " ( "  + RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RECIPE_NAME + " VARCHAR(50), " + PREPARATION_TIME + " NUMERIC, " + COOK_TIME + " NUMERIC);";



    Type type;
    public ShoppingListDbHelper(@Nullable Context context) {
        super(context, "list.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createItemTable);
        sqLiteDatabase.execSQL(CREATE_RECIPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME_COLUMN,item.getName());
        contentValues.put(QUANTITY_COLUMN,item.getQty());
        contentValues.put(TYPE_COLUMN,item.getType().toString());
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
                String str = cursor.getString(3);

                switch (str){
                    case "KILO":
                        type = Type.KILO;
                        break;
                    case "GRAM":
                        type = Type.GRAM;
                        break;
                    case "BROI":
                        type = Type.BROI;
                        break;
                    case "BOTTLE":
                        type = Type.BOTTLE;
                        break;
                    case "CAN":
                        type = Type.CAN;
                        break;
                    case "LITRE":
                        type = Type.LITRE;
                        break;
                }

                Item returnedItem = new Item(id, name, quantity,type);
                items.add(returnedItem);
            } while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        database.close();
        return items;
    }

    public void update(String id,String name, String qty,Type type){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME_COLUMN,name);
        cv.put(QUANTITY_COLUMN,qty);
        cv.put(TYPE_COLUMN,type.toString());
        db.update(ITEMS_TABLE,cv," item_id =  " + id,null);
        db.close();

    }



}
