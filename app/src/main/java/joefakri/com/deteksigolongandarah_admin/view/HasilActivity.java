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
import joefakri.com.deteksigolongandarah_admin.adapter.HasilAdapter;
import joefakri.com.deteksigolongandarah_admin.adapter.PenyakitAdapter;
import joefakri.com.deteksigolongandarah_admin.api.APIService;
import joefakri.com.deteksigolongandarah_admin.api.BasePenyakit;
import joefakri.com.deteksigolongandarah_admin.api.Basehasil;
import joefakri.com.deteksigolongandarah_admin.api.ResponseHasil;
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

public class HasilActivity extends AppCompatActivity {

    @BindView(R.id.rvData) RecyclerView rvData;
    HasilAdapter hasilAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        ButterKnife.bind(this);
        initializeRetrofit();

        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setNestedScrollingEnabled(false);
        rvData.setHasFixedSize(false);
        rvData.setAdapter(hasilAdapter = new HasilAdapter());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataWithGet("view", "");
    }

    @OnClick(R.id.btnTambah)
    public void btnTambah(){
        Intent intent = new Intent(HasilActivity.this, HasilFormActivity.class);
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

    private void getDataWithGet(String action, String no){

        APIService apiService = retrofit.create(APIService.class);
        Call<Basehasil> result = apiService.getDataHasil(action, no);
        result.enqueue(new Callback<Basehasil>() {
            @Override
            public void onResponse(Call<Basehasil> call, Response<Basehasil> response) {
                try {
                    if(response.body()!=null){
                        hasilAdapter.updateList(response.body().getData());
                        hasilAdapter.setAdapterListener(adapterListener);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Basehasil> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    HasilAdapter.AdapterListener adapterListener = new HasilAdapter.AdapterListener() {
        @Override
        public void onClick(ResponseHasil responseHasil) {
            Intent intent = new Intent(HasilActivity.this, HasilFormActivity.class);
            intent.putExtra("viewEditDelete", true);
            intent.putExtra("gol_darah", responseHasil.getGolDarah());
            intent.putExtra("kode_penyakit", responseHasil.getKodePenyakit());
            intent.putExtra("kode_gejala", responseHasil.getKodeGejala());
            intent.putExtra("kode_pertanyaan", responseHasil.getKodePertanyaan());
            intent.putExtra("no", responseHasil.getNo());
            startActivity(intent);
        }
    };

}
