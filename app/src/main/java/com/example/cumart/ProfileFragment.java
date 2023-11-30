package com.example.cumart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
FirebaseAuth oth;
FirebaseDatabase db;
DatabaseReference ref;

TextView txt1,txt2,txt3;
CardView card1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        oth=FirebaseAuth.getInstance();
db=FirebaseDatabase.getInstance();
txt1=view.findViewById(R.id.username);
txt2=view.findViewById(R.id.emailtxthere);
txt3=view.findViewById(R.id.mobilehere);

ref=db.getReference("Users").child(oth.getCurrentUser().getUid());
ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DataSnapshot> task) {
if(task.isSuccessful()){
    if(task.getResult().exists()){
DataSnapshot snap= task.getResult();
String username=String.valueOf(snap.child("name").getValue());
String email=String.valueOf(snap.child("email").getValue());
String mobile=String.valueOf(snap.child("mobile").getValue());
txt1.setText(username);
txt2.setText(email);
txt3.setText(mobile);
    }
}
    }
});


        card1=view.findViewById(R.id.logoutcard);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oth.signOut();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });

        return view ;
    }
}