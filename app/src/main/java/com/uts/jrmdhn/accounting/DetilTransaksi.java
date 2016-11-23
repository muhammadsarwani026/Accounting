package com.uts.jrmdhn.accounting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetilTransaksi extends AppCompatActivity implements View.OnClickListener {

    TextView tvdTanggal, tvdJumlah, tvdJurnal, tvdKeterangan;
    Button btnHapus,btnUbah;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_transaksi);

        tvdJurnal = (TextView) findViewById(R.id.tvdJurnal);
        tvdTanggal = (TextView) findViewById(R.id.tvdTanggal);
        tvdJumlah = (TextView) findViewById(R.id.tvdNominal);
        tvdKeterangan = (TextView) findViewById(R.id.tvdKeterangan);

        Intent ii = getIntent();
        ID = ii.getIntExtra("ID",0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ID Transaksi: "+ID);

        btnHapus = (Button) findViewById(R.id.btnHapus);
        btnHapus.setOnClickListener(this);
        btnUbah = (Button) findViewById(R.id.btnUbah);
        btnUbah.setOnClickListener(this);

        DatabaseHandler db = new DatabaseHandler(this);
        Transaksi trx = db.getTransaksi(ID);
        long jumlah = trx.getKredit()+trx.getDebet();

        tvdJurnal.setText(trx.getJurnal());
        tvdJumlah.setText("Rp"+String.valueOf( NumberFormat.getNumberInstance(Locale.GERMANY).format(jumlah) )+",00");
        tvdKeterangan.setText(trx.getKeterangan());
        try{
            String formattedDateString;
            DateFormat formatAwal = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat formatAkhir = new SimpleDateFormat("EEEE, dd MMM yyyy");
            java.util.Date date = formatAwal.parse(trx.getTanggal());
            formattedDateString = formatAkhir.format(date);
            tvdTanggal.setText(formattedDateString);
        } catch (ParseException e){}

    }

    @Override
    public void onClick(View v) {
        if (v==btnHapus){
            DatabaseHandler db = new DatabaseHandler(this);
            db.hapusTransaksi(ID);
            Intent in = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
            finish();
        }
        else if (v==btnUbah){
            int lalala = ID;
            Intent in = new Intent(getApplicationContext(),UbahTransaksi.class);
            in.putExtra("ID",lalala);
            startActivity(in);
            finish();
        }
    }
}
