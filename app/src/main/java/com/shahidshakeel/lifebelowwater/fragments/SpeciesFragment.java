package com.shahidshakeel.lifebelowwater.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieFormDialog;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieProfileDialog;
import com.shahidshakeel.lifebelowwater.model.Specie;
import com.shahidshakeel.lifebelowwater.utils.AddSpecieListener;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.OnSpecieClickListener;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.SpeciesAdapter;

import java.util.ArrayList;

public class SpeciesFragment extends Fragment implements OnSpecieClickListener {
  private final ArrayList<Specie> species = new ArrayList<>();
  private final AddSpecieListener addSpecieListener;

  public SpeciesFragment(AddSpecieListener addSpecieListener) {
    loadData();
    this.addSpecieListener = addSpecieListener;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_species, container, false);

    RecyclerView rvSpecies = v.findViewById(R.id.rvSpecies);
    SearchView svSpecies = v.findViewById(R.id.svSpecies);
    SpeciesAdapter speciesAdapter = new SpeciesAdapter(species);
    ImageButton ibSort = v.findViewById(R.id.ibSort);
    FloatingActionButton fabAddSpecie = v.findViewById(R.id.fabAddSpecie);

    rvSpecies.setAdapter(speciesAdapter);
    rvSpecies.setLayoutManager(new LinearLayoutManager(getContext()));
    rvSpecies.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    speciesAdapter.setOnSpecieClickListener(this);

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
      view -> new SpecieFormDialog(getContext()).show()
    );
    
    return v;
  }

  private void loadData() {
    ArrayList<String> salmoLocations = new ArrayList<>();
    salmoLocations.add("Gilgit Baltistan");
    salmoLocations.add("Balochistan");
    salmoLocations.add("KPK");
    species.add(new Specie("Salmo trutta fario", "N/D", salmoLocations, 123456));
    species.get(0).setDescription(
      "Salmo trutta fario, sometimes called the river trout,[2] and also known by the name of its parent species, the brown trout, is a predatory fish of the family Salmonidae and a subspecies or morph of the brown trout species, Salmo trutta, which also includes sea trout (Salmo trutta trutta) and a lacustrine trout (Salmo trutta lacustris). Depending on the supply of food, river trout measure 20 to 80 cm (0.7–2.6 ft) in length; exceptionally they may be up to 1 m (3.3 ft) long and weigh up to over 13 kg (29 lb). Their back is olive-dark brown and silvery blue, red spots with light edges occur towards the belly, the belly itself is whitish yellow. River trout usually attains a weight of up to 2 kg (4.4 lb). They can live for up to 18 years.");

    ArrayList<String> mykissLocations = new ArrayList<>();
    mykissLocations.add("KPK");
    species.add(new Specie("Oncorhynchus mykiss", "N/D", mykissLocations, 21134642));
    species.get(1).setDescription(
      "The rainbow trout (Oncorhynchus mykiss) is a trout and species of salmonid native to cold-water tributaries of the Pacific Ocean in Asia and North America. The steelhead (sometimes called \"steelhead trout\") is an anadromous (sea-run) form of the coastal rainbow trout (O. m. irideus) or Columbia River redband trout (O. m. gairdneri) that usually returns to fresh water to spawn after living two to three years in the ocean. Freshwater forms that have been introduced into the Great Lakes and migrate into tributaries to spawn are also called steelhead."
    );

    ArrayList<String> schisturaLocations = new ArrayList<>();
    schisturaLocations.add("Azad Jammu Kashmir");
    species.add(new Specie("Schistura nalbanti", "N/D", schisturaLocations, 86147));

    ArrayList<String> schizopygeLocations = new ArrayList<>();
    schizopygeLocations.add("Gilgit Baltistan");
    schizopygeLocations.add("KPK");
    species.add(new Specie("Schizopyge esocinus", "N/D", schizopygeLocations, 32714));

  }

  void showNewSpecieDialog(){

  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onSpecieClick(int position) {
    new SpecieProfileDialog(getContext(), species.get(position)).show();
  }
}