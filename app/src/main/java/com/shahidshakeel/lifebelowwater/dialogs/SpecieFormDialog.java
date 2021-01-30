package com.shahidshakeel.lifebelowwater.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.model.Specie;

import java.util.Arrays;

public class SpecieFormDialog extends AlertDialog {
  private Specie specie = null;
  private TextInputEditText tietSpecieName, tietSpeciePopulation, tietSpecieDescription;
  private MultiAutoCompleteTextView mactvLocations;
  private TextInputLayout tilName, tilPopulation, tilLocations, tilDescription;

  public SpecieFormDialog(@NonNull Context context, Specie specie) {
    super(context);
    this.specie = specie;
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_specie_form, null);
    setView(v);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    tietSpecieName = v.findViewById(R.id.tietSpecieName);
    tietSpeciePopulation = v.findViewById(R.id.tietSpeciePopulation);
    mactvLocations = v.findViewById(R.id.mactvLocations);
    tietSpecieDescription = v.findViewById(R.id.tietSpecieDescription);

    tilName = v.findViewById(R.id.tilSpecieName);
    tilPopulation = v.findViewById(R.id.tilSpeciePopulation);
    tilLocations = v.findViewById(R.id.tilSpecieLocations);
    tilDescription = v.findViewById(R.id.tilSpecieDescription);

    if (specie != null){
      tietSpecieName.setText(specie.getName());
      tietSpecieName.setEnabled(false);
      tietSpeciePopulation.setText(Long.toString(specie.getPopulation()));
      mactvLocations.setText(TextUtils.join(", ", specie.getLocations()));
      tietSpecieDescription.setText(specie.getDescription());
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
      getContext(),
      R.layout.list_item_single_text,
      new String[]{"Pubjab", "KPK"}
      );

    mactvLocations.setAdapter(adapter);
    mactvLocations.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    MaterialButton mbAdd = v.findViewById(R.id.mbAdd);
    mbAdd.setOnClickListener(
      v1 -> {
        Specie specie = validate();
        if (specie != null) {
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
          ref.child("species").push().setValue(specie);

          this.dismiss();
        }
      }
    );

    MaterialButton mbCancel = v.findViewById(R.id.mbCancel);
    mbCancel.setOnClickListener(
      v2 -> this.dismiss()
    );

    super.onCreate(savedInstanceState);
  }

  private Specie validate(){
    tilName.setErrorEnabled(false);
    tilPopulation.setErrorEnabled(false);
    tilLocations.setErrorEnabled(false);
    tilDescription.setErrorEnabled(false);

    Editable edName = tietSpecieName.getText();
    Editable edPopulation = tietSpeciePopulation.getText();
    Editable edDescription = tietSpecieDescription.getText();
    Editable edLocations = mactvLocations.getText();

    if (edName == null || edName.toString().isEmpty()){
      tilName.setError("Name Required");
      return null;
    }

    if (edPopulation == null || edPopulation.toString().isEmpty()){
      tilPopulation.setError("Population Required");
      return null;
    }

    if (edLocations == null || edLocations.toString().isEmpty()){
      tilLocations.setError("Location(s) Required");
      return null;
    }

    if (edDescription == null || edDescription.toString().isEmpty()){
      tilDescription.setError("Description Required");
      return null;
    }

    return new Specie(edName.toString(), edDescription.toString(), Arrays.asList(edLocations.toString().split("\\s*,\\s*")), Long.parseLong(edPopulation.toString()),null);
  }
}
