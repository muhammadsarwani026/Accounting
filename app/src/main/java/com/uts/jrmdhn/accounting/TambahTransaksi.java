package com.uts.jrmdhn.accounting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TambahTransaksi extends AppCompatActivity {
    private Toolbar toolbar;
    int jenis;
    EditText etTanggal,etJurnal,etNominal,etKeterangan;
    Button btnSubmit;

    final Calendar myCal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("Tambah Transaksi");

        etKeterangan = (EditText) findViewById(R.id.etKeterangan);
        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etJurnal = (EditText) findViewById(R.id.etJurnal);
        etNominal = (EditText) findViewById(R.id.etNominal);

        btnSubmit = (Button) findViewById(R.id.btnSubmitBaru);
        final DatabaseHandler db = new DatabaseHandler(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jenis == 0){
                    Log.d("Insert: ", "Inserting ..");
                    db.tambahTransaksi(new Transaksi(   etTanggal.getText().toString(),
                            etJurnal.getText().toString() ,
                            Integer.parseInt(etNominal.getText().toString()),
                            0,etKeterangan.getText().toString() ) );
                    Log.d("Insert: ", "success");

                    Intent in = new Intent(TambahTransaksi.this,MainActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    Log.d("Insert: ", "Inserting ..");
                    db.tambahTransaksi(new Transaksi(   etTanggal.getText().toString(),
                            etJurnal.getText().toString() ,
                            0,
                            Integer.parseInt(etNominal.getText().toString()),
                            etKeterangan.getText().toString()) );
                    Log.d("Insert: ", "success");

                    Intent in = new Intent(TambahTransaksi.this,MainActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });

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
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahTransaksi.this, date, myCal
                        .get(Calendar.YEAR), myCal.get(Calendar.MONTH),
                        myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //RADIOGROUP//
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgTransaksi);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioDebet:
                        jenis = 0;
                        etNominal.setHint("Nominal (Debet)");
                        break;
                    case R.id.radioKredit:
                        jenis = 1;
                        etNominal.setHint("Nominal (Kredit)");
                        break;
                }
            }
        });


        Transaksi namatransaksi = new Transaksi();
    }
    private void updateLabel(){String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etTanggal.setText(sdf.format(myCal.getTime()));}
}
