package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.ShowMyOrders;
import com.alaa.microprocess.lrahtk.pojo.Basket2;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by microprocess on 2019-01-26.
 */

public class ShowMyOrder_Adapter extends RecyclerView.Adapter<ShowMyOrder_Adapter.ViewHolder>{
    List<Basket2> basket2;
    Context context;
    public ShowMyOrder_Adapter(ShowMyOrders showMyOrders, List<Basket2> basket2) {
        context = showMyOrders;
        this.basket2 = basket2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout,parent,false);
        return new ShowMyOrder_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder1, int position1) {
        holder1.add.setVisibility(View.GONE);
        holder1.min.setVisibility(View.GONE);
        Glide // fast to get Item in Local //Picasso is the faster than glide from internet
                .with(context)
                .load(ApiRetrofit.API_IMAGE_BASE_URL + basket2.get(position1).getProduct().getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder1.Product_Image);

        holder1.productName.setText(basket2.get(position1).getProduct().getName());
        holder1.quantity.setText(basket2.get(position1).getQuantity() + "");
        holder1.product_company.setText(basket2.get(position1).getProduct().getBrand() + "");
       final double price = basket2.get(position1).getPrice() * basket2.get(position1).getQuantity();
        holder1.Price.setText(price + " L.E");

        //Delete LeftMargin
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 0, 0, 0);
        holder1.foreground.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return basket2.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder   {
        ImageButton add , min ;
        TextView productName , product_company , quantity , Price;
        ImageView Product_Image ;
        LinearLayout foreground , delete_background ;

        public ViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
            min = itemView.findViewById(R.id.min);
            productName = itemView.findViewById(R.id.productName);
            product_company = itemView.findViewById(R.id.product_company);
            quantity = itemView.findViewById(R.id.quantity);
            Price   = itemView.findViewById(R.id.Price);
            foreground = itemView.findViewById(R.id.foreground);
            Product_Image = itemView.findViewById(R.id.Product_Image);
            delete_background = itemView.findViewById(R.id.Cardview2);

        }

    }
}
