package joefakri.com.deteksigolongandarah_admin.model;

import java.io.Serializable;

/**
 * Created by deny on bandung.
 */

public class GejalaModel implements Serializable{

    private String gejala;
    private String kode_gelaja;

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getKode_gelaja() {
        return kode_gelaja;
    }

    public void setKode_gelaja(String kode_gelaja) {
        this.kode_gelaja = kode_gelaja;
    }
}
