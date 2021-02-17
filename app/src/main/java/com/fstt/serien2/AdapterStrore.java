package com.fstt.serien2;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterStrore extends ArrayAdapter implements View.OnClickListener {
    ArrayList<Product> products;
    Context context;
    ShowDialog  showDialog;

    public AdapterStrore(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        products = objects;
        showDialog = (ShowDialog) context;
    }
    public interface  ShowDialog{
        public void display(Product prd);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_store,parent,false);
        }

        TextView prodPrice,prodQte,prodName;
        Button purchaseBtn;
        prodName = convertView.findViewById(R.id.prodName);
        prodPrice = convertView.findViewById(R.id.prodPrice);
        prodQte = convertView.findViewById(R.id.prodQte);
        purchaseBtn = convertView.findViewById(R.id.purchase);
        prodName.setText(getItem(position).getProdName());
        prodPrice.setText(String.format("%s DH",String.valueOf(getItem(position).getProdPrice())));
        prodQte.setText(String.valueOf(getItem(position).getProdQte()));
        purchaseBtn.setOnClickListener(this);
        purchaseBtn.setTag(String.valueOf(position));
        return convertView;
    }


    @Nullable
    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public void onClick(View v) {

        Product prod = getItem(Integer.parseInt((String) v.getTag()));
        showDialog.display(prod);
    }
}
