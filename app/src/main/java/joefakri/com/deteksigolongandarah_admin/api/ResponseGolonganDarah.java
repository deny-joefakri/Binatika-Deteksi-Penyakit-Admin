package joefakri.com.deteksigolongandarah_admin.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deny on bandung.
 */

public class ResponseGolonganDarah {

    @SerializedName("gol_darah")
    @Expose
    private String golDarah;
    @SerializedName("pengobatan")
    @Expose
    private String pengobatan;

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getPengobatan() {
        return pengobatan;
    }

    public void setPengobatan(String pengobatan) {
        this.pengobatan = pengobatan;
    }

}
