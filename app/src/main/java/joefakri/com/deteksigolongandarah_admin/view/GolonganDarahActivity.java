package joefakri.com.deteksigolongandarah_admin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joefakri.com.deteksigolongandarah_admin.R;
import joefakri.com.deteksigolongandarah_admin.adapter.GolonganDarahAdapter;
import joefakri.com.deteksigolongandarah_admin.adapter.PenyakitAdapter;
import joefakri.com.deteksigolongandarah_admin.api.APIService;
import joefakri.com.deteksigolongandarah_admin.api.BaseGolonganDarah;
import joefakri.com.deteksigolongandarah_admin.api.BasePenyakit;
import joefakri.com.deteksigolongandarah_admin.api.ResponseGolonganDarah;
import joefakri.com.deteksigolongandarah_admin.api.ResponsePenyakit;
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

public class GolonganDarahActivity extends AppCompatActivity {

    @BindView(R.id.rvData) RecyclerView rvData;
    GolonganDarahAdapter golonganDarahAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golongan_darah);
        ButterKnife.bind(this);
        initializeRetrofit();

        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setNestedScrollingEnabled(false);
        rvData.setHasFixedSize(false);
        rvData.setAdapter(golonganDarahAdapter = new GolonganDarahAdapter());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataWithGet("");
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

    private void getDataWithGet(String kode_gejala){

        APIService apiService = retrofit.create(APIService.class);
        Call<BaseGolonganDarah> result = apiService.getDataGolonganDarah(kode_gejala);
        result.enqueue(new Callback<BaseGolonganDarah>() {
            @Override
            public void onResponse(Call<BaseGolonganDarah> call, Response<BaseGolonganDarah> response) {
                try {
                    if(response.body()!=null){
                        golonganDarahAdapter.updateList(response.body().getData());
                        golonganDarahAdapter.setAdapterListener(adapterListener);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseGolonganDarah> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    GolonganDarahAdapter.AdapterListener adapterListener = new GolonganDarahAdapter.AdapterListener() {
        @Override
        public void onClick(ResponseGolonganDarah responseGolonganDarah) {
            Intent intent = new Intent(GolonganDarahActivity.this, GolonganDarahFormActivity.class);
            intent.putExtra("golDarah", responseGolonganDarah.getGolDarah());
            intent.putExtra("pengobatan", responseGolonganDarah.getPengobatan());
            startActivity(intent);
        }
    };

}
