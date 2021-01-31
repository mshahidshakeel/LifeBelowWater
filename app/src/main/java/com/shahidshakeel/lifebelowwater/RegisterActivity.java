package com.shahidshakeel.lifebelowwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class RegisterActivity extends AppCompatActivity {
  private TextInputLayout tilUsername, tilPassword, tilConfirmPassword;
  private TextInputEditText tietUsername, tietPassword, tietConfirmPassword;
  private MaterialButton mbRegister;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    tilUsername = findViewById(R.id.tilUsername);
    tilPassword = findViewById(R.id.tilPassword);
    tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
    tietUsername = findViewById(R.id.tietUsername);
    tietPassword = findViewById(R.id.tietPassword);
    tietConfirmPassword = findViewById(R.id.tietConfirmPassword);

    mbRegister = findViewById(R.id.mbRegister);

    mbRegister.setOnClickListener(
      v -> {
        UserCredentials uc = validate();
        if (uc != null) {
          checkIfUserAlreadyExists(uc);
        }
      }
    );

  }

  private void checkIfUserAlreadyExists(UserCredentials creds) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("aquaticExpert").addListenerForSingleValueEvent(
      new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          for (DataSnapshot snap : snapshot.getChildren()) {
            UserCredentials uc = snap.getValue(UserCredentials.class);
            if (uc != null) {
              if (uc.getUsername().equals(creds.getUsername())) {
                showUserAlreadyExists(uc.getUsername());
                return;
              }
            }
          }

          addUserToDatabase(creds);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
      }
    );
  }

  private void addUserToDatabase(UserCredentials creds) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("aquaticExpert").push().setValue(creds);
    SharedPreferences sp = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    sp.edit().putString(AQUATIC_EXPERT, creds.getUsername()).apply();
    startActivity(
      new Intent(this, MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
    );
  }

  private void showUserAlreadyExists(String username) {
    new AlertDialog.Builder(this)
      .setTitle("Oops...")
      .setMessage(String.format("A user with handle \"%s\" already exists", username))
      .setPositiveButton("Ok", (dialog, which) -> {
        dialog.dismiss();
      })
      .show();
  }

  private UserCredentials validate() {
    tilUsername.setErrorEnabled(false);
    tilPassword.setErrorEnabled(false);
    tilConfirmPassword.setErrorEnabled(false);

    Editable edUsername = tietUsername.getText();
    Editable edPassword = tietPassword.getText();
    Editable edConfirmPassword = tietConfirmPassword.getText();

    if (edUsername == null || edUsername.toString().isEmpty()) {
      tilUsername.setError("Username Required");
      return null;
    }

    if (edPassword == null || edPassword.toString().isEmpty()) {
      tilPassword.setError("Password Required");
      return null;
    }

    if (edConfirmPassword == null || edConfirmPassword.toString().isEmpty()) {
      tilPassword.setError("Confirm Password Required");
      return null;
    }

    if (!edConfirmPassword.toString().equals(edConfirmPassword.toString())) {
      tilConfirmPassword.setError("Confirm Password don't match");
      return null;
    }

    return new UserCredentials(edUsername.toString(), edPassword.toString());
  }
}