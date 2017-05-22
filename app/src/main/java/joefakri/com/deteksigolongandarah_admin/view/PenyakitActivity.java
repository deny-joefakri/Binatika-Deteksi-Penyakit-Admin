package joefakri.com.deteksigolongandarah_admin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joefakri.com.deteksigolongandarah_admin.R;
import joefakri.com.deteksigolongandarah_admin.adapter.GejalaAdapter;
import joefakri.com.deteksigolongandarah_admin.adapter.PenyakitAdapter;
import joefakri.com.deteksigolongandarah_admin.api.APIService;
import joefakri.com.deteksigolongandarah_admin.api.BaseGejala;
import joefakri.com.deteksigolongandarah_admin.api.BasePenyakit;
import joefakri.com.deteksigolongandarah_admin.api.ResponsePenyakit;
import joefakri.com.deteksigolongandarah_admin.model.GejalaModel;
import joefakri.com.deteksigolongandarah_admin.util.Const;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by deny on bandung.
 */

public class PenyakitActivity extends AppCompatActivity {

    @BindView(R.id.rvData) RecyclerView rvData;
    PenyakitAdapter penyakitAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit);
        ButterKnife.bind(this);
        initializeRetrofit();

        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setNestedScrollingEnabled(false);
        rvData.setHasFixedSize(false);
        rvData.setAdapter(penyakitAdapter = new PenyakitAdapter());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataWithGet("view", "");
    }

    @OnClick(R.id.btnTambah)
    public void btnTambah(){
        Intent intent = new Intent(PenyakitActivity.this, PenyakitFormActivity.class);
        intent.putExtra("viewEditDelete", false);
        startActivity(intent);
    }

    @OnClick(R.id.btnBatal)
    public void btnBatal(){
        finish();
    }

    @OnClick(R.id.btnKeluar)
    public void btnKeluar(){
        finish();
    }

    private void initializeRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void getDataWithGet(String action, String kode_gejala){

        APIService apiService = retrofit.create(APIService.class);
        Call<BasePenyakit> result = apiService.getDataPenyakit(action, kode_gejala);
        result.enqueue(new Callback<BasePenyakit>() {
            @Override
            public void onResponse(Call<BasePenyakit> call, Response<BasePenyakit> response) {
                try {
                    if(response.body()!=null){
                        penyakitAdapter.updateList(response.body().getData());
                        penyakitAdapter.setAdapterListener(adapterListener);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BasePenyakit> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    PenyakitAdapter.AdapterListener adapterListener = new PenyakitAdapter.AdapterListener() {
        @Override
        public void onClick(ResponsePenyakit responsePenyakit) {
            Intent intent = new Intent(PenyakitActivity.this, PenyakitFormActivity.class);
            intent.putExtra("viewEditDelete", true);
            intent.putExtra("penyakit", responsePenyakit.getNamaPenyakit());
            intent.putExtra("kodePenyakit", responsePenyakit.getKodePenyakit());
            intent.putExtra("penyebab", responsePenyakit.getPenyebab());
            intent.putExtra("pengobatan", responsePenyakit.getPengobatan());
            startActivity(intent);
        }
    };

}
