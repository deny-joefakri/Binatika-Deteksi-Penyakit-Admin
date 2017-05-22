package joefakri.com.deteksigolongandarah_admin.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by deny on bandung.
 */

public class BaseGejala {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ResponseGejala> data = null;

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

    public List<ResponseGejala> getData() {
        return data;
    }

    public void setData(List<ResponseGejala> data) {
        this.data = data;
    }

}
