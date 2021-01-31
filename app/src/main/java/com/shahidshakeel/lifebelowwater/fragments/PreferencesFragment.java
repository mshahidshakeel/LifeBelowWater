package com.shahidshakeel.lifebelowwater.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahidshakeel.lifebelowwater.R;
import com.shahidshakeel.lifebelowwater.dialogs.SpecieFormDialog;
import com.shahidshakeel.lifebelowwater.model.Specie;
import com.shahidshakeel.lifebelowwater.utils.ApprovalAdapter;
import com.shahidshakeel.lifebelowwater.utils.LogOutListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.shahidshakeel.lifebelowwater.utils.Const.AQUATIC_EXPERT;
import static com.shahidshakeel.lifebelowwater.utils.Const.SHARED_PREFS_KEY;

public class PreferencesFragment extends Fragment {
  private String username;
  private TextView tvUsername;
  private MaterialButton mbLogOut, mbRequestSpecieAdd, mbLogInAsExpert;
  private LogOutListener listener;

  public PreferencesFragment(String username, LogOutListener listener) {
    this.username = username;
    this.listener = listener;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_prefrences, container, false);
    LinearLayout ll_aquaticExpert = view.findViewById(R.id.ll_aquatic_expert);
    LinearLayout ll_normalUser = view.findViewById(R.id.ll_normal_user);
    tvUsername = view.findViewById(R.id.tvUsername);
    mbLogOut = view.findViewById(R.id.mbLogOut);
    mbRequestSpecieAdd = view.findViewById(R.id.addSpecieAsNormalUser);
    mbLogInAsExpert = view.findViewById(R.id.mbLogInAsExpert);
    ListView approvalsList = view.findViewById(R.id.approvalsList);

    if (username == null) {
      ll_aquaticExpert.setVisibility(View.GONE);
      ll_normalUser.setVisibility(View.VISIBLE);
    } else {
      ll_aquaticExpert.setVisibility(View.VISIBLE);
      ll_normalUser.setVisibility(View.GONE);
      tvUsername.setText(username);
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
      ref.child("specieRequests")
        .addValueEventListener(
          new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              ArrayList<Specie> specieList = new ArrayList<>();
              for (DataSnapshot snap : snapshot.getChildren()) {
                Specie specie = snap.getValue(Specie.class);
                if (specie != null) {
                  specie.setKey(snap.getKey());
                  specieList.add(specie);
                }
              }

              ApprovalAdapter adapter = new ApprovalAdapter(getContext(), R.layout.list_item_approval, specieList);
              approvalsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          }
        );
    }

    mbLogOut.setOnClickListener(
      v -> listener.onLogoutClicked()
    );

    mbRequestSpecieAdd
      .setOnClickListener(
        v -> new SpecieFormDialog(getContext(), null, true).show()
      );

    mbLogInAsExpert.setOnClickListener(
      v -> listener.onLogoutClicked()
    );

    return view;
  }
}