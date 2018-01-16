package test.zyl.mytestapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import lombok.Data;
import sdk.LineResponse;
import sdk.Sdk;

@SuppressLint({
        "JavascriptInterface", "SetJavaScriptEnabled"
})
public class MainActivity extends AppCompatActivity {
    private X5WebView webview;
    String responseData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //热修复操作
//        sophix();
        setContentView(R.layout.activity_main);
        //数据库操作
//        openDB();

        //webview操作
        webviewShow();

        //sdk.aar测试
//        testAAR();
    }

    private void testAAR() {
        Sdk.setEnv();
        LineResponse lines = Sdk.getCoachLines("45260", "北京");
        Log.e("MainActivity", "lines===" + lines.getDepCity());
    }

    /**
     * webview浏览器
     */
    private void webviewShow() {
        webview = (X5WebView) findViewById(R.id.webview);
        webview.setVisibility(View.INVISIBLE);
        //东航hack
//        eastPlane();

        //海航hack
        HainanPlane();
    }

    /**
     * 海南值机破解
     */
    private void HainanPlane() {
        webview.loadUrl("http://webckipe.travelsky.com/cki/login.do?orgId=HUAIRNEW");//主页
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                //如果是首页,加载完成后自动填写
                if (s.contains("login.do")) {
                    webview.loadUrl("javascript:$('.form-group').eq(0).find('input').val('陈何珍');" +
                            "$('.form-group').eq(1).find('input').val('7812431411234');" +
                            "$('.form-group').eq(2).find('input').val('15020227830');" +
                            "preSubmit();");
                } else {
                    webview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 东航值机破解
     */
    private void eastPlane() {
        webview.loadUrl("https://m.ceair.com/pages/checkin/index.html?channel=wechat_fuwu");//主页
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                //如果是首页,加载完成后自动填写
                if (s.contains("index.html")) {
                    webview.loadUrl("javascript:$('.main input').eq(0).val('7812431411234');" +
                            "$('.main input').eq(1).val('陈');" +
                            "$('.main input').eq(2).val('何珍');" +
                            "$('.main input').eq(3).val('15020227830');" +
                            "confirmReadTheRules();" +
                            "$('.js_comint').eq(0).click()");
                } else {
                    //首页隐藏页面
                    Log.e("MainActivity", "22222" + s);
                    webview.setVisibility(View.VISIBLE);
                    webview.loadUrl("javascript:$('.js-head-wrap').remove()");

                }
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

//        ArrayList<IntlCityBean> hotelList = db.getData2List("intl_city", IntlCityBean.class);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(hotelList);
//        Log.e("MainActivity", jsonStr);


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
