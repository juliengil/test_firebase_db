package com.fifty_five.firebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView productList = (ListView) findViewById(R.id.listview);
        Button goToCartButton = (Button) findViewById(R.id.go_to_cart_button);
        goToCartButton.setOnClickListener(clickOnCart);

        mDatabase = FirebaseDatabase.getInstance().getReference("Products");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProductsAdapter productsAdpt;

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<HashMap<String, Product>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Product>>() {};
                HashMap<String, Product> prodList = dataSnapshot.getValue(genericTypeIndicator);
                if (prodList != null) {
                    ArrayList<Product> cleanList = new ArrayList<Product>();
                    for(Map.Entry<String, Product> entry : prodList.entrySet()) {
                        Product prod = entry.getValue();
                        if (prod != null) {
                            prod.id = entry.getKey();
                            cleanList.add(prod);
                        }
                    }
                    productsAdpt= new ProductsAdapter (MainActivity.this, 0, cleanList);
                    productList.setAdapter(productsAdpt);
                    productList.setOnItemClickListener(onItemClick);
                }
                else
                    Log.e("Productlist " , "null");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("oops", "Failed to read value.", error.toException());
            }
        });

    }

    View.OnClickListener clickOnCart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, DisplayCart.class);
            startActivity(intent);
        }
    };

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Product prod = (Product) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DisplayProduct.class);
            intent.putExtra("product_id", prod.id);
            startActivity(intent);
        }
    };
}
