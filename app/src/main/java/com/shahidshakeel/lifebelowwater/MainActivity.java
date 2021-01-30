package com.shahidshakeel.lifebelowwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shahidshakeel.lifebelowwater.model.Specie;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.OnSpecieClickListener;
import com.shahidshakeel.lifebelowwater.utils.recyclerview.species.SpeciesAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements OnSpecieClickListener {
  private final ArrayList<Specie> species = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    loadData();

    RecyclerView rvSpecies = findViewById(R.id.rvSpecies);
    SearchView svSpecies = findViewById(R.id.svSpecies);
    SpeciesAdapter speciesAdapter = new SpeciesAdapter(species);
    ImageButton ibSort = findViewById(R.id.ibSort);

    rvSpecies.setAdapter(speciesAdapter);
    rvSpecies.setLayoutManager(new LinearLayoutManager(this));
    rvSpecies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
      v -> speciesAdapter.toggleSort()
    );
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

  @SuppressLint("DefaultLocale")
  @Override
  public void onSpecieClick(int position) {
    Gson gson = new Gson();
    String specieJson = gson.toJson(species.get(position));
    startActivity(new Intent(this, SpecieActivity.class).putExtra("SPECIE_JSON", specieJson));
  }
}