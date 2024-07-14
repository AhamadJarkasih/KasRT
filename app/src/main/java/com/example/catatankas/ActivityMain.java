package com.example.catatankas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankas.databinding.ActivityMainBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityMain extends AppCompatActivity {
    public static final String TAG = ActivityMain.class.getSimpleName();
    private ActivityMainBinding binding;

    private List<TransaksiModel> transaksiList;
    private PreferenceHelper preferenceHelper;

    private TransaksiAdapter transaksiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        setup data
        transaksiList = new ArrayList<>();
        preferenceHelper = new PreferenceHelper(getApplicationContext());

//        setup recyclerview
        transaksiAdapter = new TransaksiAdapter(transaksiList);
        binding.rvTransaksi.setAdapter(transaksiAdapter);

        updateData();

        binding.btnAddPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ActivityTambahPemasukan.class));
//                finish();
            }
        });

        binding.btnAddPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ActivityTambahPengeluaran.class));
//                finish();
            }
        });

        binding.btnClearTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferenceHelper.clearTransaksi()){
                    Toast.makeText(ActivityMain.this, "Berhasil membersihkan data.", Toast.LENGTH_SHORT).show();
                    updateData();
                }
            }
        });
    }

    private long calculateSaldo(){
        long saldo = 0;

        for (TransaksiModel item :
                transaksiList) {
            switch (item.getTipeTransaksi()) {
                case PEMASUKAN:
                    saldo += item.getAmount();
                    break;

                case PENGELUARAN:
                    saldo -= item.getAmount();
                    break;
            }
        }
        return saldo;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData(){
//        update data
        if(preferenceHelper.getTransaksis()!=null){
            transaksiList.clear();
            transaksiList.addAll(preferenceHelper.getTransaksis());
        }
        transaksiAdapter.notifyDataSetChanged();

        // update saldo
        String cash = NumberFormat.getCurrencyInstance(new Locale("ID", "ID")).format(calculateSaldo());
        binding.tvSaldo.setText(cash);
        if(calculateSaldo()>0){
            binding.tvSaldo.setTextColor(getColor(R.color.textColorIn));
        }else{
            binding.tvSaldo.setTextColor(getColor(R.color.textColorOut));
        }
    }

    public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {
        private List<TransaksiModel> transaksiModelList;

        public TransaksiAdapter(List<TransaksiModel> transaksiModelList) {
            this.transaksiModelList = transaksiModelList;
        }

        @NonNull
        @Override
        public TransaksiAdapter.TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaksi, parent, false);

            return  new TransaksiViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiViewHolder holder, int position) {
            TransaksiModel transaksi = this.transaksiModelList.get(position);
            holder.getItemDate().setText(transaksi.getFormatDate());
            holder.getItemTitle().setText(transaksi.getTitle());
            holder.getItemDescription().setText(transaksi.getDescription());
            holder.getItemAmount().setText(transaksi.getFormatAmount());

            switch (transaksi.getTipeTransaksi()){
                case PENGELUARAN:
                    holder.getItemIcon().setImageResource(R.drawable.money_out);
                    holder.getItemAmount().setTextColor(getColor(R.color.textColorOut));
                    break;
                case PEMASUKAN:
                    holder.getItemIcon().setImageResource(R.drawable.money_in);
                    holder.getItemAmount().setTextColor(getColor(R.color.textColorIn));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return this.transaksiModelList.size();
        }

        public class TransaksiViewHolder extends RecyclerView.ViewHolder{
            private ImageView itemIcon;
            private TextView itemTitle, itemDescription, itemAmount, itemDate;
            public TransaksiViewHolder(@NonNull View itemView) {
                super(itemView);
                itemIcon = itemView.findViewById(R.id.ivTransactionIcon);
                itemTitle = itemView.findViewById(R.id.tvTransactionTitle);
                itemDescription = itemView.findViewById(R.id.tvTransactionDescription);
                itemAmount = itemView.findViewById(R.id.tvTransactionAmount);
                itemDate = itemView.findViewById(R.id.tvTransactionDate);
            }

            public ImageView getItemIcon() {
                return itemIcon;
            }

            public TextView getItemTitle() {
                return itemTitle;
            }

            public TextView getItemDescription() {
                return itemDescription;
            }

            public TextView getItemAmount() {
                return itemAmount;
            }

            public TextView getItemDate() {
                return itemDate;
            }
        }
    }
}