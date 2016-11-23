package com.uts.jrmdhn.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by jrmdh on 10/25/2016.
 */
public class FragmentKredit extends Fragment {

    public FragmentKredit() {
        // Required empty public constructor
    }

    TextView tvTotalkre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kredit, container, false);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        tvTotalkre = (TextView) v.findViewById(R.id.tvTotalkre);
        long totalkre = db.hitungKredit();
        tvTotalkre.setText("(Rp"+String.valueOf(NumberFormat.getNumberInstance(Locale.GERMANY).format(totalkre))+",00)");

        UserAdapter adapter = new UserAdapter(getContext(),db.ambilSemuaKredit());
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
