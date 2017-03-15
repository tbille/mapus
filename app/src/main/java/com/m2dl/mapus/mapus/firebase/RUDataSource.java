package com.m2dl.mapus.mapus.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.mapus.mapus.OccupationRuFragment;
import com.m2dl.mapus.mapus.model.Batiment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RUDataSource {

    private DatabaseReference mDatabase;
    private OccupationRuFragment occupationRuFragment;

    public RUDataSource(final OccupationRuFragment occupationRuFragment) {

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
                List<Integer> ruValues = new ArrayList<Integer>();
                for (DataSnapshot ruSnapshot : dataSnapshot.getChildren()) {
                    ruValues.add(ruSnapshot.getValue(Integer.class));
                }
                occupationRuFragment.addRUData(ruValues.get(0), ruValues.get(1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("RU", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
