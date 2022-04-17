package com.example.shopinglistapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopinglistapp.Controllers.EditItemActivity;
import com.example.shopinglistapp.Controllers.MainActivity;
import com.example.shopinglistapp.Controllers.ShoppingListActivity;
import com.example.shopinglistapp.DataBase.ShoppingListDbHelper;
import com.example.shopinglistapp.Model.Item;
import com.example.shopinglistapp.Model.Type;
import com.example.shopinglistapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<Item> items;
    private Context context;
    String itemTypeToDisplay;


    public RecyclerViewAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.name.setText(items.get(position).getName());
        holder.qty.setText(items.get(position).getQty());

        switch (items.get(position).getType().toString()){
            case "KILO":
                itemTypeToDisplay = "кг.";
                break;
            case "GRAM":
                itemTypeToDisplay = "г.";
                break;
            case "BROI":
                itemTypeToDisplay = "бр.";
                break;
            case "BOTTLE":
                itemTypeToDisplay = "бутилки";
                break;
            case "CAN":
                itemTypeToDisplay = "кутии";
                break;
            case "LITRE":
                itemTypeToDisplay = "л.";
                break;
        }

        holder.type.setText(itemTypeToDisplay);
        Item item = new Item(items.get(position).getId(),items.get(position).getName(),items.get(position).getQty(),items.get(position).getType());
        ShoppingListDbHelper helper = new ShoppingListDbHelper(context);
        holder.delete.setOnClickListener(view -> {
            helper.deleteItem(item);
            items.remove(position);
            notifyDataSetChanged();
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditItemActivity.class);
                i.putExtra("id",item.getId());
                i.putExtra("name", item.getName());
                i.putExtra("qty",item.getQty());
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, qty, type;
        private ImageButton delete;
        private RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            name = itemView.findViewById(R.id.item_name);
            qty = itemView.findViewById(R.id.quantity_item);
            type = itemView.findViewById(R.id.item_type);
            delete = itemView.findViewById(R.id.delete_button);
        }
    }
}
