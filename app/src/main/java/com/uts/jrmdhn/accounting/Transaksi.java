package com.uts.jrmdhn.accounting;

/**
 * Created by jrmdh on 10/28/2016.
 */

public class Transaksi {
    int _id;
    long _debet, _kredit, _jumlah;
    String _jurnaltransaksi, _tanggal, _keterangan;

    public Transaksi(int id, String tgl, String jnl, long debet, long kredit, String keterangan){
        this._id = id;
        this._tanggal = tgl;
        this._jurnaltransaksi = jnl;
        this._debet = debet;
        this._kredit = kredit;
        this._keterangan = keterangan;
    }

    public Transaksi(String tgl, String jnl, long debet, long kredit, String keterangan){
        this._tanggal = tgl;
        this._jurnaltransaksi = jnl;
        this._debet = debet;
        this._kredit = kredit;
        this._keterangan = keterangan;
    }

    public Transaksi(String tgl, String jrnl, long jumlah, String keterangan){
        this._tanggal = tgl;
        this._jurnaltransaksi = jrnl;
        this._jumlah= jumlah;
        this._keterangan = keterangan;
    }

    public Transaksi(){}

    //getter
    public int getID(){return this._id;}
    public long getDebet(){return this._debet;}
    public long getKredit(){return this._kredit;}
    public long getJumlah(){return this._jumlah;}
    public String getJurnal() {return this._jurnaltransaksi;}
    public String getTanggal() {return this._tanggal;}
    public String getKeterangan(){return this._keterangan;};

    //setter
    public void setID(int id){this._id=id;}
    public void setDebet(long deb){this._debet=deb;}
    public void setKredit(long kre){this._kredit=kre;}
    public void setJumlah(long jum){this._jumlah=jum;}
    public void setJurnal(String jurnal){this._jurnaltransaksi=jurnal;}
    public void setTanggal(String tgl){this._tanggal=tgl;}
    public void set_keterangan(String ktrng){this._keterangan=ktrng;}
}
