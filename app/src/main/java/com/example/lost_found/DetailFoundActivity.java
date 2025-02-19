package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lost_found.model.ClaimResponse;
import com.example.lost_found.retrofit.PortalClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailFoundActivity extends AppCompatActivity {

    TextView textNamaBarang;
    TextView textkategori;
    TextView textKontak;
    TextView textD;
    TextView textLokasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_found);

        //sharedpreference
        SharedPreferences simpan = getSharedPreferences("com.example.lost_found.SIMP", MODE_PRIVATE);
        String nama = simpan.getString("nama", "");
        String token = simpan.getString("token", "");
        int i_user = simpan.getInt("i_user", 0);


        Intent detailBarangIntent = getIntent();
        String namaBarang = detailBarangIntent.getStringExtra("NAMA_BARANG");
        String kategori = detailBarangIntent.getStringExtra("Kategori");
        String kontak = detailBarangIntent.getStringExtra("kontak");
        String lokasi = detailBarangIntent.getStringExtra("lokasi");
        String deskripsi = detailBarangIntent.getStringExtra("deskripsiB");
        int id_penemub = detailBarangIntent.getIntExtra("id_penemu", 0);


        if(i_user==id_penemub){
            hideTombol(0);
        } else {
            hideTombol(1);
        }


        textNamaBarang = findViewById(R.id.textNamaBarang);
        textNamaBarang.setText(namaBarang);

        textkategori= findViewById(R.id.textkategori);
        textkategori.setText(kategori);

        textKontak = findViewById(R.id.textnomor);
        textKontak.setText(kontak);

        textD = findViewById(R.id.textDesk);
        textD.setText(deskripsi);

        textLokasi = findViewById(R.id.textLokasib);
        textLokasi.setText(lokasi);


    }
    public void hideTombol(int statusbar){
        Button loginBtn = findViewById(R.id.buttonclaim);
        if (statusbar == 1){
            loginBtn.setVisibility(View.GONE);
        } else if (statusbar == 0){
            loginBtn.setVisibility(View.VISIBLE);
        }
    }
    public void telepon(View view){
        Intent detailBarangIntent = getIntent();

        String kontak = detailBarangIntent.getStringExtra("kontak");
        String phone = kontak;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);

    }
    public void claimBarangs(View view){
        Intent detailBarangIntent = getIntent();
        String idbarangs = detailBarangIntent.getStringExtra("id_barang");

        //buat objek klien
        String API_BASE_URL = " https://f75a-36-69-9-69.ngrok.io/";

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okBuilder.build());

        Retrofit retrofit = builder.build();

        PortalClient client = retrofit.create(PortalClient.class);

        //panggil
        Call<ClaimResponse> call = client.claimBarang(idbarangs);

        call.enqueue(new Callback<ClaimResponse>() {
            @Override
            public void onResponse(Call<ClaimResponse> call, Response<ClaimResponse> response) {
                Toast.makeText(getApplicationContext(), idbarangs, Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }

            @Override
            public void onFailure(Call<ClaimResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Yah. .  Sambungan Jelek Nih", Toast.LENGTH_SHORT).show();
            }
        });
    }


}