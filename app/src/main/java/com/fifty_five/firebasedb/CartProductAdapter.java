package com.fifty_five.firebasedb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Julien Gil on 25/09/2017.
 */

public class CartProductAdapter extends ArrayAdapter<CartProduct> {
    private Activity activity;
    private ArrayList<CartProduct> lProducts;
    private static LayoutInflater inflater = null;

    public CartProductAdapter (Activity activity, int textViewResourceId, ArrayList<CartProduct> _lProducts) {
        super(activity, textViewResourceId, _lProducts);
        try {
            this.activity = activity;
            this.lProducts = _lProducts;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lProducts.size();
    }

    public Product getItem(Product position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_price;
        public TextView display_quantity;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.product_cart_list_item, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                holder.display_price = (TextView) vi.findViewById(R.id.display_price);
                holder.display_quantity = (TextView) vi.findViewById(R.id.display_quantity);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(lProducts.get(position).getName());
            holder.display_price.setText(lProducts.get(position).getPrice().toString());
            holder.display_quantity.setText(lProducts.get(position).getQuantity().toString());


        } catch (Exception e) {


        }
        return vi;
    }
}