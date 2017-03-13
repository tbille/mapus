package com.m2dl.mapus.mapus.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RUDataSource {

    private DatabaseReference mDatabase;

    public RUDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("ru").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * ici à chaque fois qu'une valeur est modifié cette fonction est appelée.
                 *
                 * Les questions sont donc :
                 *  * est ce qu'on passe en paramètre le graphe
                 *  * est ce qu'on ne fait pas de classe pour ça et on met directement dans la classe qui fera tout le boulot de la vue?
                 *
                 */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("RU", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
