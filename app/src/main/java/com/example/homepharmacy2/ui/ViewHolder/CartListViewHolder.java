
package com.example.homepharmacy2.ui.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepharmacy2.R;
import com.example.homepharmacy2.ui.Interface.ItemClickListner;


public class CartListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name , food_price , quantity , total;
    public ItemClickListner listner;
    public RelativeLayout layout;
    public Button remove , edit;



    public CartListViewHolder(@NonNull View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.cart_food_name);
        food_price = itemView.findViewById(R.id.cart_food_price);
        quantity = itemView.findViewById(R.id.cart_food_quantity);
        layout = itemView.findViewById(R.id.RL_cart);
        edit = itemView.findViewById(R.id.btn_edit_order);
        remove = itemView.findViewById(R.id.btn_remove_order);


    }
    public void  setItemClickListner(ItemClickListner listner){

        this.listner =listner;
    }

    @Override
    public void onClick(View view)
    {

        listner.onClick(view,getAdapterPosition(),false);

    }
}