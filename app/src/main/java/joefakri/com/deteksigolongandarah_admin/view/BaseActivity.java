package joefakri.com.deteksigolongandarah_admin.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by deny on bandung.
 */

public class BaseActivity extends AppCompatActivity{


    public ProgressDialog progressDialog;

    public void showProgressMessage(Context context, String message, boolean cancelable) {
        progressDialog = createProgressDialog(context, message, cancelable);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {}
        });
        progressDialog.show();
    }

    public void dismissProgressMessage() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static ProgressDialog createProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
}
