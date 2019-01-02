package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.RecyclerItemTouchHelper;
import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;


public class Basket extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    RecyclerView recyclerView ;
    LinearLayout sendOrder;
    Animation downtoup , uptodown;
    boolean last = false ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_basket, container, false);

         recyclerView = v.findViewById(R.id.basket_rec);
         sendOrder    = v.findViewById(R.id.sendOrder);
         downtoup = AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
         uptodown = AnimationUtils.loadAnimation(getActivity(),R.anim.uptodown);

        ArrayList<Integer> image = new ArrayList<>();
        image.add(R.drawable.layer1);
        image.add(R.drawable.layer2);
        image.add(R.drawable.layer3);
        image.add(R.drawable.layer1);
        image.add(R.drawable.layer2);
        image.add(R.drawable.layer3);


            //Recycler adapter .
        rec_Basket_Adapter adapter = new rec_Basket_Adapter(getActivity(),image);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if ( mLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount()-1) {
                    last = true ;
                    sendOrder.setVisibility(View.VISIBLE);
                    sendOrder.startAnimation(downtoup);


                }

                else {

                        if(last) {
                            sendOrder.setVisibility(View.GONE);
                            last = false;
                        }


                }

            }



        });

        //scroll to the end
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition( recyclerView.getAdapter().getItemCount() - 1 );
                // Here adapter.getItemCount()== child count
            }
        });

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Order sent", Toast.LENGTH_SHORT).show();
            }
        });


        return v ;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Toast.makeText(getActivity(), ""+direction, Toast.LENGTH_SHORT).show();
    }
}
