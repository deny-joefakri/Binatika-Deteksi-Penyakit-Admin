package joefakri.com.deteksigolongandarah_admin.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deny on bandung.
 */

public class ResponseHasil {

    @SerializedName("gol_darah")
    @Expose
    private String golDarah;
    @SerializedName("kode_penyakit")
    @Expose
    private String kodePenyakit;
    @SerializedName("kode_gejala")
    @Expose
    private String kodeGejala;
    @SerializedName("kode_pertanyaan")
    @Expose
    private String kodePertanyaan;
    @SerializedName("no")
    @Expose
    private String no;

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getKodePenyakit() {
        return kodePenyakit;
    }

    public void setKodePenyakit(String kodePenyakit) {
        this.kodePenyakit = kodePenyakit;
    }

    public String getKodeGejala() {
        return kodeGejala;
    }

    public void setKodeGejala(String kodeGejala) {
        this.kodeGejala = kodeGejala;
    }

    public String getKodePertanyaan() {
        return kodePertanyaan;
    }

    public void setKodePertanyaan(String kodePertanyaan) {
        this.kodePertanyaan = kodePertanyaan;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

}
