package com.shahidshakeel.lifebelowwater.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieFormDialog;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieProfileDialog;
import com.shahidshakeel.lifebelowwater.model.Specie;
import com.shahidshakeel.lifebelowwater.utils.AddSpecieListener;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.OnSpecieClickListener;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.SpeciesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SpeciesFragment extends Fragment implements OnSpecieClickListener {
  private final ArrayList<Specie> species = new ArrayList<>();
  private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_species, container, false);

    RecyclerView rvSpecies = v.findViewById(R.id.rvSpecies);
    SearchView svSpecies = v.findViewById(R.id.svSpecies);
    ImageButton ibSort = v.findViewById(R.id.ibSort);
    FloatingActionButton fabAddSpecie = v.findViewById(R.id.fabAddSpecie);

    rvSpecies.setLayoutManager(new LinearLayoutManager(getContext()));
    rvSpecies.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    SpeciesAdapter speciesAdapter = new SpeciesAdapter(species);
    rvSpecies.setAdapter(speciesAdapter);
    speciesAdapter.setOnSpecieClickListener(this);

    ref.child("species")
      .addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            species.clear();
            svSpecies.clearFocus();
            for (DataSnapshot snap : snapshot.getChildren()) {
              Specie specie = snap.getValue(Specie.class);
              if (specie != null) {
                specie.setKey(snap.getKey());
                species.add(specie);
              }
            }
            Collections.sort(species);
            speciesAdapter.update();
            speciesAdapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
        }
      );

    svSpecies.setOnQueryTextListener(
      new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
          return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
          speciesAdapter.getSpecieFilter().filter(newText);
          return false;
        }
      }
    );

    ibSort.setOnClickListener(
      view -> speciesAdapter.toggleSort()
    );

    fabAddSpecie.setOnClickListener(
      view -> new SpecieFormDialog(getContext(), null).show()
    );

    return v;
  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onSpecieClick(int position) {
    new SpecieProfileDialog(getContext(), species.get(position)).show();
  }
}