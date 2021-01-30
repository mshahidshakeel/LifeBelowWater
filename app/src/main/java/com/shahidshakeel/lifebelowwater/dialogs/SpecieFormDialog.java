package com.shahidshakeel.lifebelowwater.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.shahidshakeel.lifebelowwater.R;

import java.util.ArrayList;

public class SpecieFormDialog extends AlertDialog {
  public SpecieFormDialog(@NonNull Context context) {
    super(context);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_specie_form, null);
    setView(v);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    MultiAutoCompleteTextView mactvLocations = v.findViewById(R.id.mactvLocations);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
      getContext(),
      R.layout.list_item_single_text,
      new String[]{"Pubjab", "KPK"}
      );

    mactvLocations.setAdapter(adapter);
    mactvLocations.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    super.onCreate(savedInstanceState);
  }
}
