package com.shahidshakeel.lifebelowwater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import static com.shahidshakeel.lifebelowwater.utils.Const.AQUATIC_EXPERT;
import static com.shahidshakeel.lifebelowwater.utils.Const.SHARED_PREFS_KEY;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    SharedPreferences sp = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    String username = sp.getString(AQUATIC_EXPERT, null);

    Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

    Class<?> destination = null;

    if (username == null)
      destination = AskActivity.class;
    else
      destination = MainActivity.class;

    startActivity(
      new Intent(this, destination)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
    );
  }
}