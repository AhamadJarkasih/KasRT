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

import com.example.catatankas.databinding.ActivityTambahPemasukanBinding;

public class ActivityTambahPemasukan extends AppCompatActivity {

    private ActivityTambahPemasukanBinding binding;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTambahPemasukanBinding.inflate(getLayoutInflater());
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
                if( preferenceHelper.addTransaksi(
                        new TransaksiModel(
                                binding.etPemasukanTitle.getText().toString(),
                                binding.etPemasukanDescription.getText().toString(),
                                Integer.parseUnsignedInt(
                                        binding.etPemasukanAmount.getText().toString()
                                ),
                                System.currentTimeMillis() ,
                                TransaksiModel.TipeTransaksi.PEMASUKAN
                        )
                )){
                    Toast.makeText(ActivityTambahPemasukan.this, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ActivityTambahPemasukan.this, ActivityMain.class));
                    finish();
                }else{
                    Toast.makeText(ActivityTambahPemasukan.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ActivityTambahPemasukan.this, ActivityMain.class));
                    finish();
                }
            }
        });

    }
}