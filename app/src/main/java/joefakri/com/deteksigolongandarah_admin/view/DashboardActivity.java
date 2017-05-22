package joefakri.com.deteksigolongandarah_admin.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import joefakri.com.deteksigolongandarah_admin.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGejala)
    public void btnGejala(){
        startActivity(new Intent(DashboardActivity.this, GejalaActivity.class));
    }

    @OnClick(R.id.btnGolonganDarah)
    public void btnGolonganDarah(){
        startActivity(new Intent(DashboardActivity.this, GolonganDarahActivity.class));
    }

    @OnClick(R.id.btnHasil)
    public void btnHasil(){
        startActivity(new Intent(DashboardActivity.this, HasilActivity.class));
    }

    @OnClick(R.id.btnPenyakit)
    public void btnPenyakit(){
        startActivity(new Intent(DashboardActivity.this, PenyakitActivity.class));
    }


}
