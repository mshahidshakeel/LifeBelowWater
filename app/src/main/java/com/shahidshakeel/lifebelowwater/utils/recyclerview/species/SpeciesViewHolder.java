package com.shahidshakeel.lifebelowwater.utils.recyclerview.species;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahidshakeel.lifebelowwater.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SpeciesViewHolder extends RecyclerView.ViewHolder {
  private final TextView tvSpecieName, tvLocations, tvPopulation;

  public SpeciesViewHolder(@NonNull View itemView, OnSpecieClickListener listener) {
    super(itemView);
    tvSpecieName = itemView.findViewById(R.id.tvSpecieName);
    tvLocations = itemView.findViewById(R.id.tvLocations);
    tvPopulation = itemView.findViewById(R.id.tvPopulation);

    itemView.setOnClickListener(
      v -> {
        if (listener != null) {
          int pos = getAdapterPosition();
          if (pos != RecyclerView.NO_POSITION)
            listener.onSpecieClick(getAdapterPosition());
        }
      }
    );
  }

  public void setSpecieName(String specieName) {
    this.tvSpecieName.setText(specieName);
  }

  public void setSpecieLocations(ArrayList<String> locations) {
    StringBuilder locationsStringBuilder = new StringBuilder();
    for (int i = 0; i < locations.size(); ++i) {
      locationsStringBuilder.append(locations.get(i));
      if (i != locations.size() - 1)
        locationsStringBuilder.append(", ");
    }
    tvLocations.setText(locationsStringBuilder.toString());
  }

  public void setSpeciePopulation(long population) {
    tvPopulation.setText(NumberFormat.getNumberInstance().format(population));
    if (population < 50000)
      tvPopulation.setTextColor(Color.RED);
    else
      tvPopulation.setTextColor(Color.BLACK);
  }
}
