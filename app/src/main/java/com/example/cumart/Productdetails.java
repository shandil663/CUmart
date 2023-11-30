package com.example.cumart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.UUID;


public class Productdetails extends AppCompatActivity {
ImageView img;
TextView txt1,txt2,txt3,txt4,txt5;
Button chatbtn;
    private FirebaseAuth oth;
    private FirebaseStorage storage;
    FirebaseDatabase db;
    DatabaseReference ref;

    private FirebaseFirestore firestore;
    private StorageReference mStorageref;
Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        storage=FirebaseStorage.getInstance();
        mStorageref=storage.getReference();
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        oth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        img=findViewById(R.id.productimagehere);
        txt1=findViewById(R.id.pricehere);
        chatbtn=findViewById(R.id.chatbutton);
        txt2=findViewById(R.id.taghere);
        txt3=findViewById(R.id.namehere);
        txt4=findViewById(R.id.descriptionhere);
        txt5=findViewById(R.id.lochere1);
        btn1=findViewById(R.id.chatbutton);
        btn2=findViewById(R.id.favhere);
        Glide.with(img.getContext()).load(getIntent().getStringExtra("productimage")).into(img);
        txt1.setText(getIntent().getStringExtra("productprice"));
        txt2.setText(getIntent().getStringExtra("producttg"));
        txt3.setText(getIntent().getStringExtra("productname"));
        txt4.setText(getIntent().getStringExtra("productdesc"));
        txt5.setText(getIntent().getStringExtra("productmeet"));
        String Image=getIntent().getStringExtra("productimage");
        String title=getIntent().getStringExtra("productname");
        String price=getIntent().getStringExtra("productprice");
        String tg=getIntent().getStringExtra("producttg");
        String desc=getIntent().getStringExtra("productdesc");
        String mobile= getIntent().getStringExtra("mobileofad");
        String Doc=getIntent().getStringExtra("Docid");
        String msg="Product Name:"+" "+getIntent().getStringExtra("productname")+"\n"+"Product Price:"+" "+getIntent().getStringExtra("productprice")+"\n"+"Product Description:"+" "+getIntent().getStringExtra("productdesc").toString()+"\n\n"+"Is it available,I am interesting in your product.";

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWhatappInstalled()){
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+mobile+"&text="+msg));
                    startActivity(i);
//                   db=FirebaseDatabase.getInstance();
//                   ref=db.getReference("Chats").child(oth.getCurrentUser().getUid()).child(uuidAsString);
//                   Modal md=new Modal(title,price,Image);
//                   ref.setValue(md);


                    DocumentReference documentReference=firestore.collection(oth.getCurrentUser().getUid()).document();
                    Modal md=new Modal(title,price,Image,"",tg,desc,mobile);
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

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

                else{
                    Toast.makeText(Productdetails.this, "Whatsapp not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isWhatappInstalled(){
        PackageManager packageManager=getPackageManager();
        boolean what;
        try{
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            what=true;
        }
        catch(PackageManager.NameNotFoundException e){
            what =false;
        }
        return what;
    }


}
