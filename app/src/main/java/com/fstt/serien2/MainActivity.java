package com.fstt.serien2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterStrore.ShowDialog {
    ListView listView;
    BottomNavigationView bottomNavigationView;
    ArrayList<Product> products  = new ArrayList();
    SqlDB database;
    AdapterStrore adapterStrore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listStore);
        bottomNavigationView = findViewById(R.id.navigationBottom);
        database = new SqlDB(this);
        products = database.getAllProducts();
        adapterStrore = new AdapterStrore(this,R.layout.custom_list_store,products);
        listView.setAdapter(adapterStrore);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.addProd:
                        Intent intentAdd = new Intent(getApplicationContext(),add_produit_activity.class);
                        startActivityForResult(intentAdd,1);
                        break;
                    case R.id.showTranscations:
                        Intent intentShow = new Intent(getApplicationContext(),Transaction_activity.class);
                        startActivity(intentShow);
                        break;
                }
                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                    Product product = (Product) data.getSerializableExtra("newProduct");
                    try{
                        long prodId = database.addProduct(product);
                        product.setProdId((int) prodId);
                        products.add(product);
                        adapterStrore.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Product Add Successfully",Toast.LENGTH_SHORT).show();
                    }catch(SQLException e){
                        Toast.makeText(getApplicationContext(),"Product  UnSuccessfully Added",Toast.LENGTH_SHORT).show();
                    }
            }else{

                Toast.makeText(getApplicationContext(),"Something Wrong Happining Try Again",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void display(Product prd) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        EditText qte = dialog.findViewById(R.id.qteSelected);
        Button btn  = dialog.findViewById(R.id.addQte);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qteValue = qte.getText().toString().trim();

                if(qteValue.equals("")){
                    qte.setError("Please Add Quantity");
                    return;
                }
                dialog.dismiss();

                if( prd.getProdQte() >= Integer.parseInt(qteValue)){
                    try{
                        long idTrans = database.addTranscation(prd,Integer.parseInt(qteValue));
                        Toast.makeText(getApplicationContext(),"successfully Operation",Toast.LENGTH_SHORT).show();
                        int prodId = products.indexOf(prd);
                        int newQte  = prd.getProdQte() - Integer.parseInt(qteValue);
                        products.get(prodId).setProdQte(newQte);
                        adapterStrore.notifyDataSetChanged();

                    }catch(SQLException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Unsuccessfully Operation",Toast.LENGTH_SHORT).show();
                    }


                }else{

                    Toast.makeText(getApplicationContext(),"Quantity Insufficient",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();

    }

}