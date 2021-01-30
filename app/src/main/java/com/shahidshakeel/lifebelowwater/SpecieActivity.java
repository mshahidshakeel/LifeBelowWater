package com.shahidshakeel.lifebelowwater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shahidshakeel.lifebelowwater.model.Specie;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SpecieActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_specie);

    String specieJson = getIntent().getStringExtra("SPECIE_JSON");

    if (specieJson == null || specieJson.isEmpty()) {
      Toast.makeText(this, "Data Error", Toast.LENGTH_SHORT).show();
      finish();
    }

    Gson gson = new Gson();
    Specie specie = gson.fromJson(specieJson, Specie.class);

    TextView tvSpecieName = findViewById(R.id.tvSpecieName);
    TextView tvSpeciePopulation = findViewById(R.id.tvSpeciePopulation);
    TextView tvSpecieLocations = findViewById(R.id.tvSpecieLocations);
    TextView tvSpecieDescription = findViewById(R.id.tvSpecieDescription);

    tvSpecieName.setText(specie.getName());
    tvSpeciePopulation.setText(NumberFormat.getInstance().format(specie.getPopulation()));

    StringBuilder specieLocationsBuilder = new StringBuilder();
    ArrayList<String> specieLocations = specie.getLocations();
    for (int i = 0; i < specieLocations.size(); ++i) {
      specieLocationsBuilder.append(specieLocations.get(i));
      if (i != specieLocations.size() - 1)
        specieLocationsBuilder.append(", ");
    }
    tvSpecieLocations.setText(specieLocationsBuilder.toString());

    tvSpecieDescription.setText(specie.getDescription());
  }
}