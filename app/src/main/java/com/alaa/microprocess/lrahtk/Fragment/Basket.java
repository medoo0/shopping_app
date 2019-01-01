package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.RecyclerItemTouchHelper;
import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;


public class Basket extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    RecyclerView recyclerView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_basket, container, false);

         recyclerView = v.findViewById(R.id.basket_rec);

        ArrayList<Integer> image = new ArrayList<>();
        image.add(R.drawable.layer1);
        image.add(R.drawable.layer2);
        image.add(R.drawable.layer3);


        rec_Basket_Adapter adapter = new rec_Basket_Adapter(getActivity(),image);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);



        recyclerView.setAdapter(adapter);


        return v ;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Toast.makeText(getActivity(), ""+direction, Toast.LENGTH_SHORT).show();
    }
}
