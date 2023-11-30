package com.example.cumart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
RecyclerView homeview;
ArrayList<Modal> listdata;
FirebaseFirestore fire;

Context context;

myadapter myadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeview=view.findViewById(R.id.homeview);
        homeview.setLayoutManager(new GridLayoutManager(getContext(),2));
        listdata =new ArrayList<>();
        myadapter=new myadapter(listdata);
        homeview.setAdapter(myadapter)  ;
        fire=FirebaseFirestore.getInstance();
        fire.collection("Homepageimages").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
List<DocumentSnapshot> List = queryDocumentSnapshots.getDocuments();
for(DocumentSnapshot d: List){
    Modal obj=d.toObject(Modal.class);
    listdata.add(obj);

}
myadapter.notifyDataSetChanged();


                    }
                });


    }
}