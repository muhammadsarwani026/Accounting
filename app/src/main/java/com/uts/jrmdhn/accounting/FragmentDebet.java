package com.uts.jrmdhn.accounting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jrmdh on 10/25/2016.
 */
public class FragmentDebet extends Fragment {


    public FragmentDebet() {
        // Required empty public constructor
    }

    TextView tvTotalDebet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_debet, container, false);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        tvTotalDebet = (TextView) v.findViewById(R.id.tvTotalDebet);
        long totalDebet = db.hitungDebet();

        tvTotalDebet.setText("Rp "+String.valueOf(NumberFormat.getNumberInstance(Locale.GERMANY).format(totalDebet))+",00");
        UserAdapter adapter = new UserAdapter(getContext(),db.ambilSemuaDebet());
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

        return v;
    }
}

