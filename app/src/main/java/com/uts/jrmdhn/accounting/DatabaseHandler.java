package com.uts.jrmdhn.accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrmdh on 10/28/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Akuntasnsi Sederhana";
    public static final String TABLE_NAME="Transaksi";

    public static final String KEY_ID="id";
    public static final String KEY_TGL="tgl";
    public static final String KEY_JURNAL="rincian";
    public static final String KEY_DEBET="debet";
    public static final String KEY_KREDIT="kredit";
    public static final String KEY_KETERANGAN="keterangan";

    public DatabaseHandler(Context  context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "create table "+TABLE_NAME+"("
                +KEY_ID+    " integer primary key autoincrement, "
                +KEY_TGL+   " text, "
                +KEY_JURNAL+" text, "
                +KEY_DEBET+ " bigint, "
                +KEY_KREDIT+" bigint, "
                +KEY_KETERANGAN+" text)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exist "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void tambahTransaksi(Transaksi trx){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TGL, trx.getTanggal());
        cv.put(KEY_JURNAL, trx.getJurnal());
        cv.put(KEY_DEBET, trx.getDebet());
        cv.put(KEY_KREDIT, trx.getKredit());
        cv.put(KEY_KETERANGAN, trx.getKeterangan());

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public Transaksi getTransaksi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs = db.query(TABLE_NAME,new String[]{KEY_ID,KEY_TGL,KEY_JURNAL,KEY_DEBET,KEY_KREDIT,KEY_KETERANGAN}, KEY_ID+"= ?",new String[] {String.valueOf(id)},null,null,null,null);
        if (crs != null)
            crs.moveToFirst();
        Transaksi trx = new Transaksi(
                Integer.parseInt(crs.getString(0)),
                crs.getString(1),
                crs.getString(2),
                Integer.parseInt(crs.getString(3)),
                Integer.parseInt(crs.getString(4)),
                crs.getString(5));
        return trx;
    }

    public ArrayList<Transaksi> ambilSemua(){
        ArrayList<Transaksi> transaksiList = new ArrayList<Transaksi>();

        String querry = "SELECT * FROM "+ TABLE_NAME+" ORDER BY "+KEY_TGL+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(querry, null);

        if (cursor.moveToFirst()){
            do {
                Transaksi transaksi = new Transaksi();
                transaksi.setID(Integer.parseInt(cursor.getString(0)));
                transaksi.setTanggal(cursor.getString(1));
                transaksi.setJurnal(cursor.getString(2));
                transaksi.setDebet(Integer.parseInt( cursor.getString(3)) );
                transaksi.setKredit(Integer.parseInt( cursor.getString(4)) );

                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }
        return transaksiList;
    }

    public ArrayList<Transaksi> ambilSemuaDebet(){
        ArrayList<Transaksi> transaksiList = new ArrayList<Transaksi>();

        String querry = "SELECT * FROM "+ TABLE_NAME+" WHERE "+KEY_DEBET+" !=0 ORDER BY "+KEY_TGL+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(querry, null);

        if (cursor.moveToFirst()){
            do {
                Transaksi transaksi = new Transaksi();
                transaksi.setID(Integer.parseInt(cursor.getString(0)));
                transaksi.setTanggal(cursor.getString(1));
                transaksi.setJurnal(cursor.getString(2));
                transaksi.setDebet(Integer.parseInt( cursor.getString(3)) );
                transaksi.setKredit(Integer.parseInt( cursor.getString(4)) );

                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }
        return transaksiList;
    }

    public ArrayList<Transaksi> ambilSemuaKredit(){
        ArrayList<Transaksi> transaksiList = new ArrayList<Transaksi>();

        String querry = "SELECT * FROM "+ TABLE_NAME+" WHERE "+KEY_KREDIT+" !=0 ORDER BY "+KEY_TGL+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(querry, null);

        if (cursor.moveToFirst()){
            do {
                Transaksi transaksi = new Transaksi();
                transaksi.setID(Integer.parseInt(cursor.getString(0)));
                transaksi.setTanggal(cursor.getString(1));
                transaksi.setJurnal(cursor.getString(2));
                transaksi.setDebet(Integer.parseInt( cursor.getString(3)) );
                transaksi.setKredit(Integer.parseInt( cursor.getString(4)) );

                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }
        return transaksiList;
    }

    public void hapusTransaksi (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?", new String[] {String.valueOf(id)});
        db.close();
    }

    public long hitungKredit(){
        long sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select sum("+KEY_KREDIT+") from "+TABLE_NAME;
        Cursor c = db.rawQuery(query,null);

        if (c != null && c.moveToFirst()){
            sum = c.getInt(0);
            return sum;
        } else {return 0;}
    }
    public long hitungDebet(){
        long sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select sum("+KEY_DEBET+") from "+TABLE_NAME;
        Cursor c = db.rawQuery(query,null);

        if (c != null && c.moveToFirst()){
            sum = c.getInt(0);
            return sum;
        } else {return 0;}
    }

}
