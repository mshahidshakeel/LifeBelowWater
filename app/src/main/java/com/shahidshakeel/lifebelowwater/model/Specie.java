package com.shahidshakeel.lifebelowwater.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Specie implements Comparable<Specie> {
  private String name, description;
  private List<String> locations;
  private long population;
  private String key;

  public Specie() {}

  public Specie(String name, String description, List<String> locations, long population, String key) {
    this.name = name;
    this.description = description;
    this.locations = locations;
    this.population = population;
    this.key = key;
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

  public List<String> getLocations() {
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

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return this.key;
  }

  @Exclude
  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("key", key);
    result.put("name", name);
    result.put("description", description);
    result.put("population", population);
    result.put("locations", locations);

    return result;
  }
}
