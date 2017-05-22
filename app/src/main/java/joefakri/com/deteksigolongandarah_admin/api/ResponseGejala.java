package joefakri.com.deteksigolongandarah_admin.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deny on bandung.
 */

public class ResponseGejala {

    @SerializedName("gejala")
    @Expose
    private String gejala;
    @SerializedName("kode_gelaja")
    @Expose
    private String kodeGelaja;

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getKodeGelaja() {
        return kodeGelaja;
    }

    public void setKodeGelaja(String kodeGelaja) {
        this.kodeGelaja = kodeGelaja;
    }

}
