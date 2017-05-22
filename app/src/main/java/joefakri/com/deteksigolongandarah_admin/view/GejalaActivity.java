package joefakri.com.deteksigolongandarah_admin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joefakri.com.deteksigolongandarah_admin.R;
import joefakri.com.deteksigolongandarah_admin.adapter.GejalaAdapter;
import joefakri.com.deteksigolongandarah_admin.api.APIService;
import joefakri.com.deteksigolongandarah_admin.api.BaseGejala;
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

public class GejalaActivity extends AppCompatActivity {

    @BindView(R.id.rvData) RecyclerView rvData;
    GejalaAdapter gejalaAdapter;
    private Retrofit retrofit;

    ArrayList<GejalaModel> resultGejala;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gejala);
        ButterKnife.bind(this);
        initializeRetrofit();

        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setNestedScrollingEnabled(false);
        rvData.setHasFixedSize(false);
        rvData.setAdapter(gejalaAdapter = new GejalaAdapter());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataWithGet("view", "");
    }

    @OnClick(R.id.btnTambah)
    public void btnTambah(){
        Intent intent = new Intent(GejalaActivity.this, GejalaFormActivity.class);
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
        Call<BaseGejala> result = apiService.getDataGejala(action, kode_gejala);
        result.enqueue(new Callback<BaseGejala>() {
            @Override
            public void onResponse(Call<BaseGejala> call, Response<BaseGejala> response) {
                try {
                    if(response.body()!=null){
                        resultGejala = new ArrayList<>();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            GejalaModel gejalaModel = new GejalaModel();
                            gejalaModel.setGejala(response.body().getData().get(i).getGejala());
                            gejalaModel.setKode_gelaja(response.body().getData().get(i).getKodeGelaja());
                            resultGejala.add(gejalaModel);

                        }
                        gejalaAdapter.updateList(resultGejala);
                        gejalaAdapter.setAdapterListener(adapterListener);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseGejala> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    GejalaAdapter.AdapterListener adapterListener = new GejalaAdapter.AdapterListener() {
        @Override
        public void onClick(GejalaModel gejalaModel) {
            Intent intent = new Intent(GejalaActivity.this, GejalaFormActivity.class);
            intent.putExtra("viewEditDelete", true);
            intent.putExtra("gejala", gejalaModel.getGejala());
            intent.putExtra("kodeGejala", gejalaModel.getKode_gelaja());
            startActivity(intent);
        }
    };

}
