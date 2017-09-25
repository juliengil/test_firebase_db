package com.fifty_five.firebasedb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Julien Gil on 25/09/2017.
 */

public class DisplayCart extends Activity {

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView productList = (ListView) findViewById(R.id.listview);
        final Button totalButton = (Button) findViewById(R.id.go_to_cart_button);

        mDatabase = FirebaseDatabase.getInstance().getReference("Carts/123456");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CartProductAdapter productsAdpt;
                Double total = 0.0;

                GenericTypeIndicator<ArrayList<CartProduct>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<CartProduct>>() {};
                ArrayList<CartProduct> prodReferenceList = dataSnapshot.getValue(genericTypeIndicator);
                if (prodReferenceList != null) {
                    ArrayList<CartProduct> cleanList = new ArrayList<CartProduct>();
                    for (CartProduct prod : prodReferenceList) {
                        if (prod != null) {
                            cleanList.add(prod);
                            total += prod.getQuantity() * prod.getPrice();
                        }
                    }
                    productsAdpt= new CartProductAdapter (DisplayCart.this, 0, cleanList);
                    productList.setAdapter(productsAdpt);
                    totalButton.setText(total.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("oops", "Failed to read value.", error.toException());
            }
        });
    }
}
