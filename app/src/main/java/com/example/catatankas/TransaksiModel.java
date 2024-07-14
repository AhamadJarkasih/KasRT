package com.example.catatankas;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransaksiModel {
    enum TipeTransaksi {
        PEMASUKAN,
        PENGELUARAN
    }

    private String title, description;
    private long amount;
    private long time;
    private TipeTransaksi tipeTransaksi;

    public TransaksiModel(String title, String description, long amount, long time, TipeTransaksi tipeTransaksi) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.time = time;
        this.tipeTransaksi = tipeTransaksi;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public TipeTransaksi getTipeTransaksi() {
        return tipeTransaksi;
    }

    public long getTime() {
        return time;
    }

    public String getFormatAmount(){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("ID", "ID"));
        formatter.setMaximumFractionDigits(0);
        return formatter.format(amount);
    }

    public String getFormatDate(){
        SimpleDateFormat formatter
                = new SimpleDateFormat ("dd/MM/yyyy", new Locale("ID", "ID"));
        return formatter.format(new Date(this.time));
    }

}
