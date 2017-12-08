package test.zyl.mytestapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import lombok.Data;

@SuppressLint({
        "JavascriptInterface", "SetJavaScriptEnabled"
})
public class MainActivity extends AppCompatActivity {
    private X5WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //热修复操作
        sophix();
        setContentView(R.layout.activity_main);
//        //数据库操作
//        openDB();

        //webview操作
//        webviewShow();
    }

    /**
     * webview浏览器
     */
    private void webviewShow() {
        webview = (X5WebView) findViewById(R.id.webview);
        webview.loadUrl("http://slimorder.net:8888/12580/searchAction.action?channel_code=qbxy"
                + "&access_token=" + "LoginUser.getUserToken()"
                + "&userId=" + "user.getUserNo()"
                + "&enterpriseId=" + "user.getEnterpriseInfo().getEnterpriseIdx()");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
    }

    /**
     * 打开数据库
     */
    private void openDB() {
        DbUtils db = new DbUtils(this);
        //数据库迁移至手机data中, 每次都处理新数据库文件
        db.updateDB(this);
        insertDbData();
    }

    /**
     * 插入新数据
     */
    private void insertDbData() {
        DbUtils db = new DbUtils(this);

        ArrayList<IntlCityBean> hotelList = db.getData2List("intl_city", IntlCityBean.class);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(hotelList);
        Log.e("MainActivity", jsonStr);

//        ArrayList<ApprovalCityBean> hotelList = db.getData2List("approval_city", ApprovalCityBean.class);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(hotelList);
//        Log.e("MainActivity", jsonStr);

//        ArrayList<HotelBean> hotelList = db.getData2List("hotel_geo", HotelBean.class);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(hotelList);
//        Log.e("MainActivity", jsonStr);


//        String keyName = "city_name";
//        List<String> citys = db.getCityNames(tableName, keyName);
//        for (int i = 0; i < citys.size(); i++) {
//            Log.e("MainActivity.this", citys.get(i));
//            db.updateCity(tableName,
//                    new String[]{
//                            "first_spelling",
//                            "first_spells",
//                            "all_spells"},
//                    new String[]{
//                            PinyinUtil.firstAl(citys.get(i)).toUpperCase(),
//                            PinyinUtil.getPinYinFirst(citys.get(i)).toUpperCase(),
//                            PinyinUtil.getPinYin(citys.get(i)).toUpperCase()},
//                    keyName, citys.get(i));
//        }


    }

    @Data
    public static class IntlCityBean {
        private String city_id;
        private String city_name;
        private String city_code;
        private String area_code;
        private String first_spelling;
        private String first_spells;
        private String all_spells;
    }

    @Data
    public static class ApprovalCityBean {
        private String id;
        private String area_id;
        private String area_name;
        private String first_spelling;
        private String first_spells;
        private String all_spells;
    }


    @Data
    public static class HotelBean {
        private String geo_id;
        private String channel_id;
        private String Country;
        private String province_id;
        private String province_name;
        private String city_name;
        private String city_code;
        private String first_spelling;
        private String first_spells;
        private String all_spells;
    }

    /**
     * sophix热修复
     */
    private void sophix() {
        PermissionUtil.checkInitPermission(this);
        TextView tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setText("123456798");
    }


}
