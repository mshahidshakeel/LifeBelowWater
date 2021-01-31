package com.shahidshakeel.lifebelowwater.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieProfileDialog;
import com.shahidshakeel.lifebelowwater.model.Specie;

import java.util.List;

public class ApprovalAdapter extends ArrayAdapter<Specie> {
  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

  public ApprovalAdapter(@NonNull Context context, int resource, @NonNull List<Specie> objects) {
    super(context, resource, objects);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (convertView == null)
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_approval, null );

    Specie specie = getItem(position);

    TextView tvName = convertView.findViewById(R.id.tvSpecieName);
    tvName.setText(specie.getName());

    ImageButton ibAccept = convertView.findViewById(R.id.ibAccept);
    ImageButton ibReject = convertView.findViewById(R.id.ibReject);

    ibReject.setOnClickListener(
      v -> {
        ref.child("specieRequests").child(specie.getKey()).removeValue();
      }
    );

    ibAccept.setOnClickListener(
      v -> {
        ref.child("specieRequests").child(specie.getKey()).removeValue();
        ref.child("species").child(specie.getKey()).setValue(specie);
      }
    );

    return convertView;
  }
}
