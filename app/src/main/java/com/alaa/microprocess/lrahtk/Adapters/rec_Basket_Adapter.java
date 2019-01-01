package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alaa.microprocess.lrahtk.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class rec_Basket_Adapter extends RecyclerView.Adapter<rec_Basket_Adapter.ViewHolder> {
    private ArrayList<Integer> Image;
    Context context;
    public rec_Basket_Adapter(Context context,ArrayList<Integer> Image){
        this.Image = Image;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout,parent,false);
        return new rec_Basket_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.foreground.setScaleX(.9f);
        holder.foreground.setScaleY(.9f);
        holder.foreground.animate().scaleX(1f).scaleY(1f).setDuration(500);


        //

        holder.delete_background.setScaleX(.9f);
        holder.delete_background.setScaleY(.9f);
        holder.delete_background.animate().scaleX(1f).scaleY(1f).setDuration(500);


        Glide // fast to get Item in Local //Picasso is the faster than glide from internet
                .with(context)
                .load(Image.get(position))
                .into(holder.Product_Image);


    }

    @Override
    public int getItemCount() {
        return Image.size();
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
