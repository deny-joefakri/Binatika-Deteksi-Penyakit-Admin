package joefakri.com.deteksigolongandarah_admin.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hafizh Herdi on 10/15/2016.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("gejala")
    Call<SuccessResponse> postGejala(@FieldMap HashMap<String, String> params);

    @GET("gejala")
    Call<SuccessResponse> getGejala(@Query("action") String action, @Query("kode_gejala") String kode_gejala);

    @GET("gejala")
    Call<BaseGejala> getDataGejala(@Query("action") String action, @Query("kode_gejala") String kode_gejala);

    @FormUrlEncoded
    @POST("penyakit")
    Call<SuccessResponse> postPenyakit(@FieldMap HashMap<String, String> params);

    @GET("penyakit")
    Call<SuccessResponse> getPenyakit(@Query("action") String action, @Query("kode_penyakit") String kode_gejala);

    @GET("penyakit")
    Call<BasePenyakit> getDataPenyakit(@Query("action") String action, @Query("kode_penyakit") String kode_gejala);

    @FormUrlEncoded
    @POST("golongan_darah")
    Call<SuccessResponse> postGolonganDarah(@FieldMap HashMap<String, String> params);

    @GET("golongan_darah")
    Call<BaseGolonganDarah> getDataGolonganDarah(@Query("gol_darah") String kode_gejala);

    @FormUrlEncoded
    @POST("hasil")
    Call<SuccessResponse> postHasil(@FieldMap HashMap<String, String> params);

    @GET("hasil")
    Call<SuccessResponse> getHasil(@Query("action") String action, @Query("no") String no);

    @GET("hasil")
    Call<Basehasil> getDataHasil(@Query("action") String action, @Query("no") String no);
}
