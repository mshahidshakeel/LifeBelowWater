package com.shahidshakeel.lifebelowwater.utils.recyclerview.species;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.model.Specie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesViewHolder> {
  private final ArrayList<Specie> species;
  private final ArrayList<Specie> filteredSpecies;
  private boolean sorted;
  private OnSpecieClickListener onSpecieClickListener;

  public SpeciesAdapter(ArrayList<Specie> species) {
    Collections.sort(species);
    sorted = true;

    this.filteredSpecies = species;
    this.species = new ArrayList<>(this.filteredSpecies);
  }

  @NonNull
  @Override
  public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.view_holder_species, parent, false);
    return new SpeciesViewHolder(view, onSpecieClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
    Specie specie = filteredSpecies.get(position);
    holder.setSpecieName(specie.getName());
    holder.setSpecieLocations(specie.getLocations());
    holder.setSpeciePopulation(specie.getPopulation());
  }

  @Override
  public int getItemCount() {
    return filteredSpecies.size();
  }

  public Filter getSpecieFilter() {
    return specieFilter;
  }

  private final Filter specieFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      String searchKey = constraint.toString().toLowerCase();

      FilterResults filterResults = new FilterResults();

      if (searchKey.isEmpty()) {
        filterResults.values = new ArrayList<>(species);
      } else {
        ArrayList<Specie> filteredSpecies = new ArrayList<>();
        for (Specie specie : species)
          for (String location : specie.getLocations())
            if (location.toLowerCase().startsWith(searchKey))
              filteredSpecies.add(specie);

        filterResults.values = filteredSpecies;
      }

      return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      filteredSpecies.clear();
      filteredSpecies.addAll((Collection<? extends Specie>) results.values);
      notifyDataSetChanged();
    }
  };

  public void toggleSort() {
    if (sorted) {
      Collections.reverse(species);
      Collections.reverse(filteredSpecies);
      sorted = false;
    } else {
      Collections.sort(species);
      Collections.sort(filteredSpecies);
      sorted = true;
    }
    notifyDataSetChanged();
  }

  public void setOnSpecieClickListener(OnSpecieClickListener onSpecieClickListener){
    this.onSpecieClickListener = onSpecieClickListener;
  }
}
