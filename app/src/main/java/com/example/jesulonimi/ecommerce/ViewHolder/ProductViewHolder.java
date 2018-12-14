package com.example.jesulonimi.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jesulonimi.ecommerce.Interfaces.ItemClickListener;
import com.example.jesulonimi.ecommerce.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView productImage;
    public TextView productName;
    public TextView productDescription;
    public TextView productPrice;
    public ItemClickListener listener;
    public ProductViewHolder(View itemView) {
        super(itemView);
        productDescription=(TextView) itemView.findViewById(R.id.product_description);
        productName=(TextView) itemView.findViewById(R.id.product_name);
        productImage=(ImageView) itemView.findViewById(R.id.product_image);
        productPrice=(TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {

        this.listener.onClick(v,getAdapterPosition(),false);
    }
}
