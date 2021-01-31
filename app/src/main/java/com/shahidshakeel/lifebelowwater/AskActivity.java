package com.shahidshakeel.lifebelowwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahidshakeel.lifebelowwater.model.UserCredentials;

import static com.shahidshakeel.lifebelowwater.utils.Const.AQUATIC_EXPERT;
import static com.shahidshakeel.lifebelowwater.utils.Const.SHARED_PREFS_KEY;

public class AskActivity extends AppCompatActivity {
  private TextInputLayout tilUsername, tilPassword;
  private TextInputEditText tietUsername, tietPassword;
  private MaterialButton mbLogIn, mbRegister, mbExplore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ask);

    tilUsername = findViewById(R.id.tilUsername);
    tilPassword = findViewById(R.id.tilPassword);
    tietUsername = findViewById(R.id.tietUsername);
    tietPassword = findViewById(R.id.tietPassword);

    mbLogIn = findViewById(R.id.mbLogIn);
    mbRegister = findViewById(R.id.mbRegister);
    mbExplore = findViewById(R.id.mbExplore);

    mbExplore.setOnClickListener(
      v -> startActivity(
        new Intent(this, MainActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
      )
    );

    mbLogIn.setOnClickListener(
      v -> {
        UserCredentials creds = validate();
        if (creds != null) {
          login(creds);
        }
      }
    );


    mbRegister.setOnClickListener(
      v ->
        startActivity(
          new Intent(this, RegisterActivity.class)
        )
    );
  }

  private void login(UserCredentials creds) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("aquaticExpert")
      .addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snap : snapshot.getChildren()) {
              UserCredentials uc = snap.getValue(UserCredentials.class);
              if (uc != null && uc.equals(creds)) {
                loginSuccess(creds.getUsername());
                break;
              }
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
        }
      );
  }

  private void loginSuccess(String username) {
    SharedPreferences sp = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    sp.edit().putString(AQUATIC_EXPERT, username).apply();
    startActivity(
      new Intent(this, MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
    );
  }

  private UserCredentials validate() {
    tilUsername.setErrorEnabled(false);
    tilPassword.setErrorEnabled(false);

    Editable edUsername = tietUsername.getText();
    Editable edPassword = tietPassword.getText();

    if (edUsername == null || edUsername.toString().isEmpty()) {
      tilUsername.setError("Username Required");
      return null;
    }

    if (edPassword == null || edPassword.toString().isEmpty()) {
      tilPassword.setError("Password Required");
      return null;
    }

    return new UserCredentials(edUsername.toString(), edPassword.toString());
  }
}
