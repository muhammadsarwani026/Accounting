package com.uts.jrmdhn.accounting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jrmdhn on 10/25/2016.
 */

public class FragmentTransaksi extends Fragment {

    public FragmentTransaksi() {}

    long saldo;
    TextView tvSaldo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaksi, container, false);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        long sumDebet = db.hitungDebet();
        long sumKredit = db.hitungKredit();
        tvSaldo = (TextView) v.findViewById(R.id.tvSaldo);
        saldo = db.hitungDebet()-db.hitungKredit();
        tvSaldo.setText("Rp"+String.valueOf(NumberFormat.getNumberInstance(Locale.GERMANY).format(saldo))+",00");
        Log.d("ACCOUNTING::", "Total Debet  = "+String.valueOf(sumDebet));
        Log.d("ACCOUNTING::", "Total Kredit = "+String.valueOf(sumKredit));
        Log.d("ACCOUNTING::", "Saldo = "+String.valueOf(saldo));

        UserAdapter adapter = new UserAdapter(getContext(),db.ambilSemua());
        ListView lv = (ListView) v.findViewById(R.id.listMember);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  TextView textView = (TextView) view.findViewById(R.id.tvID);
                int idTrx = Integer.parseInt(textView.getText().toString());
                Intent i = new Intent(getActivity(), DetilTransaksi.class);
                i.putExtra("ID",idTrx);
                startActivity(i);
            }
        });
        List<Transaksi> transaksis = db.ambilSemua();
        for (Transaksi m : transaksis) {
            String log = "ID: "+m.getID()+"\t Tanggal:"+m.getTanggal()+" \t Jurnal: "+m.getJurnal()+"\t d: "+m.getDebet()+"\t k: "+m.getKredit();
            Log.d("ACCOUNTING:: ",log);
        }
        return v;
    }
}
