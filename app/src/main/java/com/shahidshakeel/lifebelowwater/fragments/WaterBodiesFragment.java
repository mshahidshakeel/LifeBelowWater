package com.shahidshakeel.lifebelowwater.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.shahidshakeel.lifebelowwater.R;

public class WaterBodiesFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_water_bodies, container, false);
//    TextInputEditText tietLocations = view.findViewById(R.id.tietSpecieLocations);
    return view;
  }
}