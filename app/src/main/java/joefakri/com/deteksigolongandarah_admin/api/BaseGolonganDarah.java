package joefakri.com.deteksigolongandarah_admin.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by deny on bandung.
 */

public class BaseGolonganDarah {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ResponseGolonganDarah> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResponseGolonganDarah> getData() {
        return data;
    }

    public void setData(List<ResponseGolonganDarah> data) {
        this.data = data;
    }

}
