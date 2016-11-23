package com.uts.jrmdhn.accounting;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UbahTransaksi extends AppCompatActivity implements View.OnClickListener {

    int jenis,ID;

    EditText etEdTanggal,etEdJurnal,etEdNominal,etEdKeterangan;
    Button btnEdSimpan;

    final Calendar myCal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_transaksi);
        Intent ii = getIntent();
        ID = ii.getIntExtra("ID",0);
        setTitle("Ubah Transaksi: "+ID);

        etEdTanggal = (EditText) findViewById(R.id.etEdTanggal);
        etEdJurnal = (EditText) findViewById(R.id.etEdJurnal);
        etEdNominal = (EditText) findViewById(R.id.etEdNominal);
        etEdKeterangan =(EditText) findViewById(R.id.etEdKeterangan);

        DatabaseHandler db = new DatabaseHandler(this);
        Transaksi trx = db.getTransaksi(ID);


        etEdJurnal.setHint(trx.getJurnal());
        etEdTanggal.setHint(trx.getTanggal());
        etEdNominal.setHint(String.valueOf(trx.getDebet()+trx.getKredit()));
        etEdKeterangan.setHint(trx.getKeterangan());
        etEdKeterangan.setText(trx.getKeterangan());
        etEdJurnal.setText(trx.getJurnal());
        etEdTanggal.setText(trx.getTanggal());
        etEdNominal.setText(String.valueOf(trx.getDebet()+trx.getKredit()));

        TextView tvReset = (TextView) findViewById(R.id.tvReset);
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                Transaksi trx = db.getTransaksi(ID);
                etEdKeterangan.setText(trx.getKeterangan());
                etEdJurnal.setText(trx.getJurnal());
                etEdTanggal.setText(trx.getTanggal());
                etEdNominal.setText(String.valueOf(trx.getDebet()+trx.getKredit()));
            }
        });

        btnEdSimpan = (Button) findViewById(R.id.btnEdSimpan);
        btnEdSimpan.setOnClickListener(this);

        //DATEPICKER
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, monthOfYear);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etEdTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UbahTransaksi.this, date, myCal
                        .get(Calendar.YEAR), myCal.get(Calendar.MONTH),
                        myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //RADIOGROUP//
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgEdTransaksi);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioDebet:
                        jenis = 0;
                        etEdNominal.setHint("Nominal (Debet)");
                        break;
                    case R.id.radioKredit:
                        jenis = 1;
                        etEdNominal.setHint("Nominal (Kredit)");
                        break;
                }
            }
        });


    }
    private void updateLabel(){String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etEdTanggal.setText(sdf.format(myCal.getTime()));}


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdSimpan:{
                if(jenis == 0 ){
                    DatabaseHandler db = new DatabaseHandler(this);
                    SQLiteDatabase dbu = db.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(db.KEY_TGL,etEdTanggal.getText().toString());
                    values.put(db.KEY_JURNAL,etEdJurnal.getText().toString());
                    values.put(db.KEY_DEBET,Integer.parseInt(etEdNominal.getText().toString()));
                    values.put(db.KEY_KREDIT,0);
                    values.put(db.KEY_KETERANGAN,etEdKeterangan.getText().toString());
                    dbu.update(db.TABLE_NAME,values,db.KEY_ID+"=?", new String[] {String.valueOf(ID)});

                    Intent in = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in);
                } else {
                    DatabaseHandler db = new DatabaseHandler(this);
                    SQLiteDatabase dbu = db.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(db.KEY_TGL,etEdTanggal.getText().toString());
                    values.put(db.KEY_JURNAL,etEdJurnal.getText().toString());
                    values.put(db.KEY_DEBET,0);
                    values.put(db.KEY_KREDIT,Integer.parseInt(etEdNominal.getText().toString()));
                    values.put(db.KEY_KETERANGAN,etEdKeterangan.getText().toString());
                    dbu.update(db.TABLE_NAME,values,db.KEY_ID+"=?", new String[] {String.valueOf(ID)});

                    Intent in = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in);}
            }
        }
    }
}
