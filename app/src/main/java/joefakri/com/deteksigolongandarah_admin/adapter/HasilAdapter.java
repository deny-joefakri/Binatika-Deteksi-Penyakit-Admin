package joefakri.com.deteksigolongandarah_admin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import joefakri.com.deteksigolongandarah_admin.R;
import joefakri.com.deteksigolongandarah_admin.api.ResponseHasil;
import joefakri.com.deteksigolongandarah_admin.api.ResponsePenyakit;

/**
 * Created by air-water on 2/12/17.
 */

public class HasilAdapter extends RecyclerView.Adapter{
    private List<ResponseHasil> list;
    AdapterListener adapterListener;

    public HasilAdapter(){
        list = new ArrayList<>();

    }

    public void updateList(List<ResponseHasil> listModels) {
        this.list = listModels;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public ResponseHasil getItem(int position) {
        return list.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_hasil, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder) holder).setValue(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtNo) TextView txtNo;
        @BindView(R.id.txtGolDarah) TextView txtGolDarah;
        @BindView(R.id.txtKodePenyakit) TextView txtKodePenyakit;
        @BindView(R.id.txtKodeGejala) TextView txtKodeGejala;
        @BindView(R.id.txtKodePertanyaan) TextView txtKodePertanyaan;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setValue(final ResponseHasil responseHasil) {
            txtNo.setText(responseHasil.getNo());
            txtGolDarah.setText(responseHasil.getGolDarah());
            txtKodePenyakit.setText(responseHasil.getKodePenyakit());
            txtKodeGejala.setText(responseHasil.getKodeGejala());
            txtKodePertanyaan.setText(responseHasil.getKodePertanyaan());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListener.onClick(responseHasil);
                }
            });

        }

    }

    public interface AdapterListener {
        void onClick(ResponseHasil ResponseHasil);
    }
}