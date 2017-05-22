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
import joefakri.com.deteksigolongandarah_admin.api.ResponseGolonganDarah;
import joefakri.com.deteksigolongandarah_admin.api.ResponsePenyakit;

/**
 * Created by air-water on 2/12/17.
 */

public class GolonganDarahAdapter extends RecyclerView.Adapter{
    private List<ResponseGolonganDarah> list;
    AdapterListener adapterListener;

    public GolonganDarahAdapter(){
        list = new ArrayList<>();

    }

    public void updateList(List<ResponseGolonganDarah> listModels) {
        this.list = listModels;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public ResponseGolonganDarah getItem(int position) {
        return list.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_golongan_darah, parent, false);
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
        @BindView(R.id.txtPengobatan) TextView txtPengobatan;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setValue(final ResponseGolonganDarah responseGolonganDarah) {
            txtKode.setText(responseGolonganDarah.getGolDarah());
            txtPengobatan.setText(responseGolonganDarah.getPengobatan());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListener.onClick(responseGolonganDarah);
                }
            });

        }

    }

    public interface AdapterListener {
        void onClick(ResponseGolonganDarah responseGolonganDarah);
    }
}