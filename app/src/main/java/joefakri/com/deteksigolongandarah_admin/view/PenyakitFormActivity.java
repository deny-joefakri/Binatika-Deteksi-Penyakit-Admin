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

public class PenyakitFormActivity extends BaseActivity {

    @BindView(R.id.ed_kode_penyakit) EditText edKodePenyakit;
    @BindView(R.id.ed_penyakit) EditText edPenyakit;
    @BindView(R.id.ed_penyebab) EditText edPenyebab;
    @BindView(R.id.ed_pengobatan) EditText edPengobatan;
    @BindView(R.id.viewEditDelete) View viewEditDelete;
    @BindView(R.id.btnEdit) Button btnEdit;
    @BindView(R.id.btnHapus) Button btnHapus;
    @BindView(R.id.btnTambah) Button btnTambah;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit_form);
        ButterKnife.bind(this);
        initializeRetrofit();

        if (getIntent().getBooleanExtra("viewEditDelete", false)){
            viewEditDelete.setVisibility(View.VISIBLE);
            btnTambah.setVisibility(View.GONE);

            edPenyakit.setText(getIntent().getStringExtra("penyakit"));
            edKodePenyakit.setText(getIntent().getStringExtra("kodePenyakit"));
            edPenyebab.setText(getIntent().getStringExtra("penyebab"));
            edPengobatan.setText(getIntent().getStringExtra("pengobatan"));

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edPenyakit.getText().toString().isEmpty() || !edKodePenyakit.getText().toString().isEmpty() ||
                            !edPenyebab.getText().toString().isEmpty() || !edPengobatan.getText().toString().isEmpty()){
                        getDataWithPost(updateData(edKodePenyakit.getText().toString(), edPenyakit.getText().toString(),
                                edPenyebab.getText().toString(), edPengobatan.getText().toString()));
                    }
                }
            });

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edKodePenyakit.getText().toString().isEmpty()){
                        getDataWithGet("delete", edKodePenyakit.getText().toString());
                    }
                }
            });

        } else {
            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edPenyakit.getText().toString().isEmpty() || !edKodePenyakit.getText().toString().isEmpty() ||
                            !edPenyebab.getText().toString().isEmpty() || !edPengobatan.getText().toString().isEmpty()){
                        getDataWithPost(insertData(edKodePenyakit.getText().toString(), edPenyakit.getText().toString(),
                                edPenyebab.getText().toString(), edPengobatan.getText().toString()));
                    }
                }
            });

        }
    }

    public HashMap<String, String> insertData(String kodePenyakit, String penyakit, String penyebab, String pengobatan){
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insert");
        map.put("kode_penyakit", kodePenyakit);
        map.put("nama_penyakit", penyakit);
        map.put("penyebab", penyebab);
        map.put("pengobatan", pengobatan);
        return map;
    }

    public HashMap<String, String> updateData(String kodePenyakit, String penyakit, String penyebab, String pengobatan){
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "update");
        map.put("kode_penyakit", kodePenyakit);
        map.put("nama_penyakit", penyakit);
        map.put("penyebab", penyebab);
        map.put("pengobatan", pengobatan);
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

    private void getDataWithGet(String action, String kode_gejala){
        showProgressMessage(this, "Please Wait", false);
        APIService apiService = retrofit.create(APIService.class);
        Call<SuccessResponse> result = apiService.getPenyakit(action, kode_gejala);
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
        Call<SuccessResponse> result = apiService.postPenyakit(params);
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
