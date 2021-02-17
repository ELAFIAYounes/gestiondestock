package com.fstt.serien2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class Transaction_activity extends AppCompatActivity {
    ArrayList<Transaction> transactions = new ArrayList<>();
    ListView listView;
    SqlDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        listView = findViewById(R.id.listTranscation);
        database = new SqlDB(this);
        transactions = database.getAllTransactions();
        listView.setAdapter(new AdapterTransactionList(this,R.layout.cutsom_transaction_list,transactions));

    }
}