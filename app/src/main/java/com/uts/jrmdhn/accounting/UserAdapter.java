package com.uts.jrmdhn.accounting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrmdhn on 10/28/2016.
 */

public class UserAdapter extends ArrayAdapter<Transaksi>{
    public UserAdapter(Context context, ArrayList<Transaksi> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Transaksi transaksi = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        // Lookup view for data population
        TextView tvID = (TextView) convertView.findViewById(R.id.tvID);
        TextView tvTanggal = (TextView) convertView.findViewById(R.id.tvTanggal);
        TextView tvJurnal = (TextView) convertView.findViewById(R.id.tvJurnal);
        TextView tvDebet = (TextView) convertView.findViewById(R.id.tvDebet);
        TextView tvKredit = (TextView) convertView.findViewById(R.id.tvKredit);

        // Populate the data into the template view using the data object

        tvID.setText(String.valueOf(transaksi.getID()));

        try{
            String formattedDateString;
            DateFormat formatAwal = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat formatAkhir = new SimpleDateFormat("dd MMM, yyyy");
            java.util.Date date = formatAwal.parse(transaksi.getTanggal());
            formattedDateString = formatAkhir.format(date);
            tvTanggal.setText(formattedDateString);
        } catch (ParseException e){}

        tvJurnal.setText(String.valueOf(transaksi.getJurnal()));
        tvDebet.setText(String.valueOf(transaksi.getDebet()));
        tvKredit.setText(String.valueOf(transaksi.getKredit()));

        return convertView;
    }
}
