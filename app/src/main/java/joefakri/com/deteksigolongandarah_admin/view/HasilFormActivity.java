package joefakri.com.deteksigolongandarah_admin.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import joefakri.com.deteksigolongandarah_admin.R;
import joefakri.com.deteksigolongandarah_admin.api.APIService;
import joefakri.com.deteksigolongandarah_admin.api.SuccessResponse;
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

public class HasilFormActivity extends BaseActivity {

    @BindView(R.id.ed_golongan_darah) EditText edGolDarah;
    @BindView(R.id.ed_kode_penyakit) EditText edKodePenyakit;
    @BindView(R.id.ed_kode_gejala) EditText edKodeGejala;
    @BindView(R.id.ed_kode_pertanyaan) EditText edKodePertanyaan;
    @BindView(R.id.viewEditDelete) View viewEditDelete;
    @BindView(R.id.btnEdit) Button btnEdit;
    @BindView(R.id.btnHapus) Button btnHapus;
    @BindView(R.id.btnTambah) Button btnTambah;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_form);
        ButterKnife.bind(this);
        initializeRetrofit();

        if (getIntent().getBooleanExtra("viewEditDelete", false)){
            viewEditDelete.setVisibility(View.VISIBLE);
            btnTambah.setVisibility(View.GONE);

            edGolDarah.setText(getIntent().getStringExtra("gol_darah"));
            edKodePenyakit.setText(getIntent().getStringExtra("kode_penyakit"));
            edKodeGejala.setText(getIntent().getStringExtra("kode_gejala"));
            edKodePertanyaan.setText(getIntent().getStringExtra("kode_pertanyaan"));

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edGolDarah.getText().toString().isEmpty() || !edKodePenyakit.getText().toString().isEmpty() ||
                            !edKodeGejala.getText().toString().isEmpty() || !edKodePertanyaan.getText().toString().isEmpty()){
                        getDataWithPost(updateData(edGolDarah.getText().toString(), edKodePenyakit.getText().toString(),
                                edKodeGejala.getText().toString(), edKodePertanyaan.getText().toString()));
                    }
                }
            });

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edKodePenyakit.getText().toString().isEmpty()){
                        getDataWithGet("delete", getIntent().getStringExtra("no"));
                    }
                }
            });

        } else {
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edGolDarah.getText().toString().isEmpty() || !edKodePenyakit.getText().toString().isEmpty() ||
                            !edKodeGejala.getText().toString().isEmpty() || !edKodePertanyaan.getText().toString().isEmpty()){
                        getDataWithPost(insertData(edGolDarah.getText().toString(), edKodePenyakit.getText().toString(),
                                edKodeGejala.getText().toString(), edKodePertanyaan.getText().toString()));
                    }
                }
            });

        }
    }

    public HashMap<String, String> insertData(String golDarah, String kodePenyakit, String kodeGejala, String kodePertanyaan){
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insert");
        map.put("gol_darah", golDarah);
        map.put("kode_penyakit", kodePenyakit);
        map.put("kode_gejala", kodeGejala);
        map.put("kode_pertanyaan", kodePertanyaan);
        return map;
    }

    public HashMap<String, String> updateData(String golDarah, String kodePenyakit, String kodeGejala, String kodePertanyaan){
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "update");
        map.put("no", getIntent().getStringExtra("no"));
        map.put("gol_darah", golDarah);
        map.put("kode_penyakit", kodePenyakit);
        map.put("kode_gejala", kodeGejala);
        map.put("kode_pertanyaan", kodePertanyaan);
        return map;
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
        showProgressMessage(this, "Please Wait", false);
        APIService apiService = retrofit.create(APIService.class);
        Call<SuccessResponse> result = apiService.getHasil(action, no);
        result.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                dismissProgressMessage();
                try {
                    if(response.body()!=null){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                dismissProgressMessage();
                t.printStackTrace();
            }
        });
    }

    private void getDataWithPost(HashMap<String, String> params){

        showProgressMessage(this, "Please Wait", false);
        APIService apiService = retrofit.create(APIService.class);
        Call<SuccessResponse> result = apiService.postHasil(params);
        result.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                dismissProgressMessage();
                try {
                    if(response.body()!=null){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    dismissProgressMessage();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                dismissProgressMessage();
                t.printStackTrace();
            }
        });
    }
}
