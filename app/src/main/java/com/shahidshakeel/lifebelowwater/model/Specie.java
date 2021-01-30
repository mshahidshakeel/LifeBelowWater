package com.shahidshakeel.lifebelowwater.model;

import java.util.ArrayList;
import java.util.List;

public class Specie implements Comparable<Specie>{
  private String name, description;
  private ArrayList<String> locations;
  private long population;

  public Specie(String name, String description, ArrayList<String> locations, long population) {
    this.name = name;
    this.description = description;
    this.locations = locations;
    this.population = population;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<String> getLocations() {
    return locations;
  }

  public void setLocations(ArrayList<String> locations) {
    this.locations = locations;
  }

  public long getPopulation() {
    return population;
  }

  public void setPopulation(long population) {
    this.population = population;
  }

  @Override
  public int compareTo(Specie o) {
    return (int) (this.population - o.getPopulation());
  }
}
