package joefakri.com.deteksigolongandarah_admin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import joefakri.com.deteksigolongandarah_admin.model.GejalaModel;
import joefakri.com.deteksigolongandarah_admin.R;

/**
 * Created by air-water on 2/12/17.
 */

public class GejalaAdapter extends RecyclerView.Adapter{
    private ArrayList<GejalaModel> list;
    AdapterListener adapterListener;

    public GejalaAdapter(){
        list = new ArrayList<>();

    }

    public void updateList(ArrayList<GejalaModel> listModels) {
        this.list = listModels;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public GejalaModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_gejala, parent, false);
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

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setValue(final GejalaModel gejalaModel) {
            txtKode.setText(gejalaModel.getKode_gelaja());
            txtNama.setText(gejalaModel.getGejala());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterListener.onClick(gejalaModel);
                }
            });

        }

    }

    public interface AdapterListener {
        void onClick(GejalaModel gejalaModel);
    }
}