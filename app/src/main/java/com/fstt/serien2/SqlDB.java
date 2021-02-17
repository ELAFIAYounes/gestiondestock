package com.fstt.serien2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SqlDB extends SQLiteOpenHelper {

    public SqlDB(@Nullable Context context) {
        super(context, "gestion_product",null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE products(prod_id INTEGER not null Primary key autoIncrement,prod_name TEXT not null,prod_price REAL NOT NULL,prod_qte INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE transactions(trans_id INTEGER not null Primary key autoIncrement,prod_name TEXT not null,prod_price REAL NOT NULL,qte INTEGER NOT NULL,dateOperation TEXT NOT NULL)");
    }

    public long addProduct(Product product) throws SQLException {
        SQLiteDatabase  db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prod_name",product.getProdName());
        contentValues.put("prod_price",product.getProdPrice());
        contentValues.put("prod_qte",product.getProdQte());
        return db.insertOrThrow("products",null,contentValues);
    }
    public ArrayList<Product> getAllProducts() throws SQLException {
        SQLiteDatabase  db =  this.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();
        Cursor cursor = db.rawQuery("SELECT * FROM products",null);
        while(cursor.moveToNext()){

            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getInt(3)));
        }
        cursor.close();
        return  products;
    }
    public long addTranscation(Product product,int qte) throws SQLException {
        SQLiteDatabase  db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("prod_qte",product.getProdQte() - qte);
        contentValues.put("prod_name",product.getProdName());
        contentValues.put("prod_price",product.getProdPrice());
        contentValues.put("qte",qte);
        contentValues.put("dateOperation",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        long transId =  db.insertOrThrow("transactions",null,contentValues);
        db.update("products",contentValues2,"prod_id = ?",new String[]{String.valueOf(product.getProdId())});
        return  transId;
    }
    public ArrayList<Transaction> getAllTransactions() throws SQLException {
        SQLiteDatabase  db =  this.getReadableDatabase();
        ArrayList<Transaction> Transactions = new ArrayList<Transaction>();
        Cursor cursor = db.rawQuery("SELECT * FROM transactions",null);
        while(cursor.moveToNext()){

            Transactions.add(new Transaction(cursor.getLong(0),cursor.getString(1),cursor.getDouble(2), cursor.getInt(3),cursor.getString(4)));
        }
        cursor.close();
        return  Transactions;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }
}
