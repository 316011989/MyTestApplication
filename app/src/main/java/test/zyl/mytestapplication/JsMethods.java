package test.zyl.mytestapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by zhangyl on 2016/8/31.
 */
public class JsMethods {
    Context context;

    public JsMethods(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void doIntent(Intent intent) {
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void showDialog(String title, String content, String btnSure, String btnCancel, Intent sureIntent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.show();
    }

    @JavascriptInterface
    public void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
