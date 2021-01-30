package com.shahidshakeel.lifebelowwater;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shahidshakeel.lifebelowwater.fragments.SpeciesFragment;
import com.shahidshakeel.lifebelowwater.fragments.WaterBodiesFragment;
import com.shahidshakeel.lifebelowwater.model.Specie;
import com.shahidshakeel.lifebelowwater.utils.AddSpecieListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
  private final ArrayList<Specie> species = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    showFragment(new SpeciesFragment(null));

    BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
    bnv.setOnNavigationItemSelectedListener(this);

  }

  private void showFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragments_container, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_species){
      showFragment(new SpeciesFragment(null));
      return true;
    }

    if (item.getItemId() == R.id.menu_water_bodies){
      showFragment(new WaterBodiesFragment());
      return true;
    }

    return false;
  }
}