package com.example.catatankas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catatankas.databinding.ActivityTambahPengeluaranBinding;

public class ActivityTambahPengeluaran extends AppCompatActivity {

    private ActivityTambahPengeluaranBinding binding;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTambahPengeluaranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preferenceHelper = new PreferenceHelper(getApplicationContext());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferenceHelper.addTransaksi(
                        new TransaksiModel(
                                binding.etPengeluaranTitle.getText().toString(),
                                binding.etPengeluaranDescription.getText().toString(),
                                Integer.parseUnsignedInt(
                                        binding.etPengeluaranAmount.getText().toString()
                                ),
                                System.currentTimeMillis(),
                                TransaksiModel.TipeTransaksi.PENGELUARAN
                        )
                )){
                    Toast.makeText(ActivityTambahPengeluaran.this, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityTambahPengeluaran.this, ActivityMain.class));
                    finish();
                }else{
                    Toast.makeText(ActivityTambahPengeluaran.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityTambahPengeluaran.this, ActivityMain.class));
                    finish();
                }
            }
        });
    }
}