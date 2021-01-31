package com.shahidshakeel.lifebelowwater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shahidshakeel.lifebelowwater.fragments.PreferencesFragment;
import com.shahidshakeel.lifebelowwater.fragments.SpeciesFragment;
import com.shahidshakeel.lifebelowwater.fragments.WaterBodiesFragment;
import com.shahidshakeel.lifebelowwater.utils.LogOutListener;

import static com.shahidshakeel.lifebelowwater.utils.Const.AQUATIC_EXPERT;
import static com.shahidshakeel.lifebelowwater.utils.Const.SHARED_PREFS_KEY;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, LogOutListener {
  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sp = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    username = sp.getString(AQUATIC_EXPERT, null);

    getSupportFragmentManager()
      .beginTransaction()
      .add(R.id.fragments_container, new SpeciesFragment(username))
      .commit();

    BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
    bnv.setOnNavigationItemSelectedListener(this);
  }

  private void showFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragments_container, fragment);
    transaction.commit();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_species) {
      showFragment(new SpeciesFragment(username));
      return true;
    }

    if (item.getItemId() == R.id.menu_water_bodies) {
      showFragment(new WaterBodiesFragment());
      return true;
    }

    if (item.getItemId() == R.id.menu_preferences) {
      showFragment(new PreferencesFragment(username, this));
      return true;
    }

    return false;
  }

  @Override
  public void onLogoutClicked() {
    SharedPreferences sp = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    sp.edit().remove(AQUATIC_EXPERT).apply();
    startActivity(
      new Intent(this, AskActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
    );
  }
}