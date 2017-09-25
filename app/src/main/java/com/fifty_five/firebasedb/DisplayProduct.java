package com.fifty_five.firebasedb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julien Gil on 25/09/2017.
 */

public class DisplayProduct extends Activity{

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_product);

        Bundle extras = getIntent().getExtras();
        product_id = extras.get("product_id").toString();
        DatabaseReference productsRef = database.getReference("Products/"+product_id);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Product product = dataSnapshot.getValue(Product.class);
                if (product != null) {
                    TextView prod_name = (TextView) findViewById(R.id.display_name);
                    TextView prod_sku = (TextView) findViewById(R.id.display_sku);
                    TextView prod_price = (TextView) findViewById(R.id.display_price);
                    Button addToCart = (Button) findViewById(R.id.add_to_cart_button);
                    prod_name.setText(product.getName());
                    prod_price.setText(product.getPrice()+"");
                    prod_sku.setText(product.getSku()+"");
                    addToCart.setOnClickListener(addToCartListener);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ProductView", "Failed to read value.", error.toException());
            }
        });
    }

    View.OnClickListener addToCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final DatabaseReference prodInCartRef = database.getReference("Carts/"+123456+"/"+product_id);
            prodInCartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Map<String, Object> cartProductMap = new HashMap<String, Object>();

                    if (dataSnapshot != null && dataSnapshot.getValue(CartProduct.class) != null) {
                        CartProduct product  = dataSnapshot.getValue(CartProduct.class);
                        if (product.getQuantity() != 0) {
                            product.setQuantity(product.getQuantity() + 1);
                            cartProductMap.put(product_id, product);
                            prodInCartRef.getParent().updateChildren(cartProductMap);
                            prodInCartRef.removeEventListener(this);
                        }
                        else {
                            product.setQuantity(Long.parseLong("1"));
                            cartProductMap.put(product_id, product);
                            prodInCartRef.getParent().updateChildren(cartProductMap);
                            prodInCartRef.removeEventListener(this);
                        }
                    }
                    else {
                        database.getReference("Products/"+product_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                Map<String, Object> cartProductMap = new HashMap<String, Object>();

                                if (dataSnapshot != null && dataSnapshot.getValue(Product.class) != null) {
                                    Product product  = dataSnapshot.getValue(Product.class);
                                    CartProduct cartProduct = new CartProduct(product);
                                    cartProductMap.put(product_id, cartProduct);
                                    prodInCartRef.getParent().updateChildren(cartProductMap);
                                    prodInCartRef.removeEventListener(this);
                                }
                                else {
                                    Log.w("Add to cart", "Failed to read value.");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("ProductView", "Failed to read value.", error.toException());
                            }
                        });
                    }
                    onBackPressed();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("ProductView", "Failed to read value.", error.toException());
                }
            });
        }
    };
}
