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

public class GolonganDarahFormActivity extends BaseActivity {

    @BindView(R.id.ed_golongan_darah) EditText edGolonganDarah;
    @BindView(R.id.ed_pengobatan) EditText edPengobatan;
    @BindView(R.id.btnEdit) Button btnEdit;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gol_darah_form);
        ButterKnife.bind(this);
        initializeRetrofit();

        edGolonganDarah.setText(getIntent().getStringExtra("golDarah"));
        edPengobatan.setText(getIntent().getStringExtra("pengobatan"));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edGolonganDarah.getText().toString().isEmpty() || !edPengobatan.getText().toString().isEmpty()){
                    getDataWithPost(updateData(edGolonganDarah.getText().toString(), edPengobatan.getText().toString()));
                }
            }
        });
    }

    public HashMap<String, String> updateData(String golDarah, String pengobatan){
        HashMap<String, String> map = new HashMap<>();
        map.put("gol_darah", golDarah);
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

    private void getDataWithPost(HashMap<String, String> params){

        showProgressMessage(this, "Please Wait", false);
        APIService apiService = retrofit.create(APIService.class);
        Call<SuccessResponse> result = apiService.postGolonganDarah(params);
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
