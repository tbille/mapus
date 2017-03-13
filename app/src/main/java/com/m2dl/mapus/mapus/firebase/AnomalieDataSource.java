package com.m2dl.mapus.mapus.firebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.m2dl.mapus.mapus.model.Anomalie;

import java.io.File;
import java.util.UUID;

public class AnomalieDataSource {

    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    public AnomalieDataSource() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void addNewAnomalie(String path, final Anomalie anomalie) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                UUID uuid = UUID.randomUUID();

                mDatabase.child("anomalie").child(uuid.toString()).setValue(anomalie);
            }
        });
    }
}
