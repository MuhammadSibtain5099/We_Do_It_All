package com.awais.homeservices;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {
    RecyclerView recyclerViewNewlyAddedRestaurants;
    RecyclerView recyclerViewTopRatedRestaurants;
    RecyclerView recyclerViewTopRatedReviews;



    SliderView sliderView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
     //   findViews(view);
        //setting size of Recycler View
     //initiating Adapter

        //setting Adapter
        Button button = view.findViewById(R.id.btneme);
        Button btnService = view.findViewById(R.id.btnservice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Emergency.class));
            }
        });
 btnService.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         startActivity(new Intent(getContext(),selectService.class));
     }
 });






        return view;
    }






}