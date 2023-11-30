package com.example.cumart;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellFragment extends Fragment {
    private ImageView img;

    private Button sellbtn;

    private EditText txt1,txt2,txt3,txt4;
    private Uri ImageUri;
    ToggleButton tg;
    final String[] phone = new String[1];
    private String PhotoUrl;
    private FirebaseAuth oth;

    private Bitmap bitmap;
    private FirebaseStorage storage;
    FirebaseDatabase dt;
    DatabaseReference ref;
    private FirebaseFirestore firestore;
    private StorageReference mStorageref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SellFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellFragment newInstance(String param1, String param2) {
        SellFragment fragment = new SellFragment();
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
        oth=FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
                dt=FirebaseDatabase.getInstance();


        ref=dt.getReference("Users");
        ref.child(oth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot data= task.getResult();
                         phone[0] ="91"+ String.valueOf(data.child("mobile").getValue());
                    }
                    else{
                        Toast.makeText(getContext(), "No mobile found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        storage=FirebaseStorage.getInstance();
        mStorageref=storage.getReference();
        View view= inflater.inflate(R.layout.fragment_sell, container, false);
        txt1=view.findViewById(R.id.editTextText);
        sellbtn=view.findViewById(R.id.buttonsell);
        sellbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        tg=view.findViewById(R.id.tg);
        txt2=view.findViewById(R.id.nxttxt);
        txt3=view.findViewById(R.id.nxttxt1);
        txt4=view.findViewById(R.id.nxttxt2);
        img=view.findViewById(R.id.selectitem);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CheckStoragepermission();
pickimage();
            }
        });

        return view;


    }

//    private void CheckStoragepermission() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if(ContextCompat.checkSelfPermission(getActivity(),
//                    Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
//
//            }
//            else{
//                pickimage();
//            }
//
//
//        }
//        else{
//            pickimage();
//        }
//
//
//    }

    private void pickimage() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    ActivityResultLauncher<Intent> launcher
         =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
             if(result.getResultCode()== Activity.RESULT_OK){
                 Intent data =result.getData();
                 if(data!=null && data.getData()!=null){
                                ImageUri=data.getData();

                     try {
                         bitmap= MediaStore.Images.Media.getBitmap(

                                 getActivity().getContentResolver(),
                                 ImageUri
                         );
                     } catch (IOException e) {
                                 throw new RuntimeException(e);
                     }


                 }

                 if(ImageUri!=null){
                     img.setImageBitmap(bitmap);
                 }
             }
            }
    );

    private void UploadImage(){
        if(ImageUri!=null){
            final  StorageReference myref=mStorageref.child("photo/"+ImageUri.getLastPathSegment());
            myref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
if(uri!=null){
    PhotoUrl=uri.toString();
    uploadinfo();

}
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private void uploadinfo(){


        String title= txt1.getText().toString().trim();
        String  desc=txt2.getText().toString().trim();
        String price=txt3.getText().toString().trim();
        String meet=txt4.getText().toString().trim();
        String what=tg.getText().toString().trim();


        if(TextUtils.isEmpty(title)&& TextUtils.isEmpty(desc)&& TextUtils.isEmpty(price)&&TextUtils.isEmpty(meet)){
            Toast.makeText(getContext(), "Enter all required fields", Toast.LENGTH_LONG).show();
        }
        else {
            DocumentReference documentReference=firestore.collection("Homepageimages").document();

            Modal md=new Modal(title,desc,price,meet,PhotoUrl,"",oth.getCurrentUser().getUid(),what,phone[0]);

            documentReference.set(md, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    if(task.isSuccessful()){
        String DocId=documentReference.getId();
        md.setImageid(DocId);
        documentReference.set(md,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
}
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}