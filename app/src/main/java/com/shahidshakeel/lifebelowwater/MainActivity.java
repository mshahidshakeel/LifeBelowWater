package com.shahidshakeel.lifebelowwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

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

    ArrayList<String> mykissLocations = new ArrayList<>();
    mykissLocations.add("KPK");
    species.add(new Specie("Oncorhynchus mykiss", "N/D", mykissLocations, 21134642));

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
    Toast.makeText(this, String.format("position: %d, size: %d", position, species.size()), Toast.LENGTH_SHORT).show();
  }
}