package com.shahidshakeel.lifebelowwater.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.model.Specie;

import java.text.NumberFormat;
import java.util.List;

public class SpecieProfileDialog extends AlertDialog {
  private final Specie specie;

  public SpecieProfileDialog(Context context, Specie specie) {
    super(context);
    this.specie = specie;
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_specie_profile, null);
    setView(view);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    TextView tvSpecieName = view.findViewById(R.id.tvSpecieName);
    TextView tvSpeciePopulation = view.findViewById(R.id.tvSpeciePopulation);
    TextView tvSpecieLocations = view.findViewById(R.id.tvSpecieLocations);
    TextView tvSpecieDescription = view.findViewById(R.id.tvSpecieDescription);
    TextView tvEndangeredSpecie = view.findViewById(R.id.tvEndangeredSpecie);

    tvSpecieName.setText(specie.getName());
    tvSpeciePopulation.setText("Population of " + NumberFormat.getInstance().format(specie.getPopulation()));

    StringBuilder specieLocationsBuilder = new StringBuilder();
    List<String> specieLocations = specie.getLocations();
    for (int i = 0; i < specieLocations.size(); ++i) {
      specieLocationsBuilder.append(specieLocations.get(i));
      if (i != specieLocations.size() - 1)
        specieLocationsBuilder.append(", ");
    }

    tvSpecieLocations.setText("Found in " + specieLocationsBuilder.toString());

    tvSpecieDescription.setText(specie.getDescription());

    if (specie.getPopulation() < 50000)
      tvEndangeredSpecie.setVisibility(View.VISIBLE);
    else
      tvEndangeredSpecie.setVisibility(View.GONE);

    super.onCreate(savedInstanceState);
  }
}
