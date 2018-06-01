package test.zyl.mytestapplication.http;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    /**
     * 当前网络是否已连接
     */
    public static LiveData<Boolean> netConnected(Context context) {
        MutableLiveData<Boolean> isNetConnect = new MutableLiveData<>();
        if (context == null) {
            isNetConnect.setValue(false);
            return isNetConnect;
        }

        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo[] infos = connectivity.getAllNetworkInfo();
                if (infos != null) {
                    for (NetworkInfo info : infos) {
                        if (info != null && info.isConnected()) {
                            // 判断当前网络是否已经连接
                            if (info.getState() == NetworkInfo.State.CONNECTED) {
                                isNetConnect.setValue(true);
                                return isNetConnect;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isNetConnect.setValue(false);
        return isNetConnect;
    }
}
