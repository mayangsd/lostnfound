package com.example.lost_found.retrofit;

import com.example.lost_found.model.AuthClass;
import com.example.lost_found.model.ListBarangObject;
import com.example.lost_found.model.RegisResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PortalClient {

    //EndPoin Login
    @FormUrlEncoded
    @POST("api/login")
    Call<AuthClass> checkLogin(@Field("username") String username, @Field("password") String password);

    @GET("api/barang")
    Call<ListBarangObject> getBarang(@Header("Authorization") String tokenb);

    @POST("api/register")
    Call<RegisResponse> daftarRegis(@Field("nama") String nama,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("kontak") String kontak);

}
