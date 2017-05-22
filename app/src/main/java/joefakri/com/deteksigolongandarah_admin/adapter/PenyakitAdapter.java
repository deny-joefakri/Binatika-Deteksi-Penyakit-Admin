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
import joefakri.com.deteksigolongandarah_admin.api.ResponsePenyakit;
import joefakri.com.deteksigolongandarah_admin.model.GejalaModel;

/**
 * Created by air-water on 2/12/17.
 */

public class PenyakitAdapter extends RecyclerView.Adapter{
    private List<ResponsePenyakit> list;
    AdapterListener adapterListener;

    public PenyakitAdapter(){
        list = new ArrayList<>();

    }

    public void updateList(List<ResponsePenyakit> listModels) {
        this.list = listModels;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public ResponsePenyakit getItem(int position) {
        return list.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_penyakit, parent, false);
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
        @BindView(R.id.txtKode) TextView txtKode;
        @BindView(R.id.txtNama) TextView txtNama;
        @BindView(R.id.txtPenyebab) TextView txtPenyebab;
        @BindView(R.id.txtPengobatan) TextView txtPengobatan;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setValue(final ResponsePenyakit responsePenyakit) {
            txtKode.setText(responsePenyakit.getKodePenyakit());
            txtNama.setText(responsePenyakit.getNamaPenyakit());
            txtPenyebab.setText(responsePenyakit.getPenyebab());
            txtPengobatan.setText(responsePenyakit.getPengobatan());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListener.onClick(responsePenyakit);
                }
            });

        }

    }

    public interface AdapterListener {
        void onClick(ResponsePenyakit responsePenyakit);
    }
}