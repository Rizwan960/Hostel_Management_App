package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class AddRoomFragment extends BottomSheetDialogFragment {
    EditText id,bed,ac,rent,description;
    ImageView imageView;
    View view;
    Uri uri;
    ProgressDialog progressDialog;
    int SELECT_IMAGE_CODE=1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(SELECT_IMAGE_CODE==1)
        {
             uri=data.getData();
            imageView.setImageURI(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_room, container, false);
        id=view.findViewById(R.id.editTextTextPersonName);
        bed=view.findViewById(R.id.editTextTextPersonName2);
        ac=view.findViewById(R.id.editTextTextPersonName3);
        rent=view.findViewById(R.id.editTextTextPersonName4);
        description=view.findViewById(R.id.editTextTextPersonName5);
        imageView=view.findViewById(R.id.imageView);
        progressDialog = new ProgressDialog(getActivity());
        Button pickImage=view.findViewById(R.id.button2);
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });
        Button upload=view.findViewById(R.id.button3);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        return view;
    }

    private void uploadData() {
        String i,b,a,d,r;
        i=id.getText().toString();
        b=bed.getText().toString();
        a=ac.getText().toString();
        d=description.getText().toString();
        r=rent.getText().toString();
        if(i.isEmpty())
        {
            id.setError("Enter room id");
        }else if(b.isEmpty())
        {
            bed.setError("Enter number of bed in room");
        }
        else if(a.isEmpty())
        {
            bed.setError("Enter AC availability");
        }
        else if(d.isEmpty())
        {
            bed.setError("Enter room description");
        }
        else if(r.isEmpty())
        {
            bed.setError("Enter rent");
        }
        else
        {
            String[] array={"https://nomadsworld.com/wp-content/uploads/2018/11/nomads-brisbane-hostel-dorm.jpg","https://www.tezu.ernet.in/hostels/kwh/img/IMG_20220206_130545.jpg"
            ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXKvwu7tUXyOusYW9eK5Ny7Uv0xmHSnRI0WYOKx-7QO0JgilWlINbTpZSpqfDsI_BPMGs&usqp=CAU"
            ,"https://www.raiuniversity.edu/wp-content/uploads/Hostel-min.jpg","https://pix10.agoda.net/hotelImages/5410581/0/efe83e8f54e41ebfb4366f8649ba5813.jpg?ca=8&ce=1&s=1024x768"};
            
            String myRandString = array[new Random().nextInt(array.length)];
            Map<String,Object> map=new HashMap<>();
            map.put("roomId",i);
            map.put("number_of_beds",b);
            map.put("aC",a);
            map.put("description",d);
            map.put("rent",r);
            map.put("imageUrl",myRandString);
            map.put("Time", Calendar.getInstance().getTime());
            progressDialog.setMessage("Please wait while upload");
            progressDialog.setTitle("Uploading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            FirebaseDatabase.getInstance().getReference().child("Rooms").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        id.setText("");
                        bed.setText("");
                        ac.setText("");
                        description.setText("");
                        rent.setText("");
                        imageView.setImageURI(null);
                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }




    }
}