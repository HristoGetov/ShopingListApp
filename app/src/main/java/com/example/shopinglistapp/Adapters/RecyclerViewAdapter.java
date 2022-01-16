package com.example.shopinglistapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.shopinglistapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> item_names = new ArrayList<>();
    private ArrayList<String> item_quantities = new ArrayList<>();
    private ArrayList<Integer> item_ids = new ArrayList<>();
    private Context context;




    public RecyclerViewAdapter(ArrayList<Integer> item_ids, ArrayList<String> item_names, ArrayList<String> item_quantities, Context context) {
        this.item_ids = item_ids;
        this.item_names = item_names;
        this.item_quantities = item_quantities;
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


        holder.name.setText(item_names.get(position));
        holder.qty.setText(item_quantities.get(position));
        Item item = new Item(item_ids.get(position),item_names.get(position),item_quantities.get(position));
        ShoppingListDbHelper helper = new ShoppingListDbHelper(context);
        holder.delete.setOnClickListener(view -> {
            helper.deleteItem(item);
           notifyDataSetChanged();
           notifyItemRemoved(position);
            Intent intent = new Intent(view.getContext(),ShoppingListActivity.class);
            view.getContext().startActivity(intent);

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
        return item_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, qty;
        private Button delete;
        private RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            name = itemView.findViewById(R.id.item_name);
            qty = itemView.findViewById(R.id.quantity_item);
            delete = itemView.findViewById(R.id.delete_button);
        }
    }
}
