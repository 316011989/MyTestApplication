package test.zyl.mytestapplication;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
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
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import lombok.Data;
import sdk.LineResponse;
import sdk.Sdk;
import test.zyl.mytestapplication.databinding.ActivityMainBinding;
import test.zyl.mytestapplication.http.DBViewModel;
import test.zyl.mytestapplication.http.DbData;

@SuppressLint({
        "JavascriptInterface", "SetJavaScriptEnabled"
})
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DBViewModel model;
    private X5WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //热修复操作
//        sophix();
//        setContentView(R.layout.activity_main);
        //数据库操作
//        openDB();

        //webview操作
//        webviewShow();

        //sdk.aar测试
//        testAAR();

        //http测试
        testHttp();
    }

    DbData dbData;

    private void testHttp() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SharedPreferences dbVersion = getSharedPreferences("dbVersion", Context.MODE_PRIVATE);
        int citysVersion = dbVersion.getInt("citysVersion", 0);
        int locationsVersion = dbVersion.getInt("locationsVersion", 0);
        model = new DBViewModel(getApplication(), citysVersion, locationsVersion);
        //订阅数据变化来刷新UI
        model.getLiveObservableData().observe(this, new Observer<DbData>() {
            @Override
            public void onChanged(@Nullable DbData data) {
                model.setUiObservableData(data);
                binding.setData(data);
                dbData = data;
                openDB();
            }
        });
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
                    webview.loadUrl("javascript:$('.form-group').eq(0).find('input').val('李西林');" +
                            "$('.form-group').eq(1).find('input').val('880-5159492978');" +
                            "$('.form-group').eq(2).find('input').val('13523237067');" +
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
        //接口请求成功
        if (dbData != null && dbData.getResult() != null) {
            //citys数据不为空
            if (dbData.getResult().getCitys() != null) {
                List<DbData.ResultBean.CitysBean.cBean> insert_citys = dbData.getResult().getCitys().getInserts();
                if (insert_citys != null)
                    for (int i = 0; i < insert_citys.size(); i++) {
                        DbData.ResultBean.CitysBean.cBean c = insert_citys.get(i);
                        db.insertCity("t_base_citys",
                                new String[]{"xycode", "nation_code", "name", "short_name", "english_name", "short_spell", "first_letter", "air_code", "train_code"
                                        , "post_code", "taxi", "long_bus", "tags", "enabled"},
                                new String[]{c.getXycode(), c.getNation_code(), c.getName(), c.getShort_name(), c.getEnglish_name(), c.getShort_spell(), c.getFirst_letter(), c.getAir_code()
                                        , c.getTrain_code(), c.getPost_code(), c.getTaxi(), c.getLong_bus(), c.getTags(), c.getEnabled()});
                        LogUtils.e("插入t_base_citys,城市名称" + c.getName());
                    }

                List<DbData.ResultBean.CitysBean.cBean> update_citys = dbData.getResult().getCitys().getUpdates();
                if (update_citys != null)
                    for (int i = 0; i < update_citys.size(); i++) {
                        DbData.ResultBean.CitysBean.cBean c = update_citys.get(i);
                        db.updateCity("t_base_citys",
                                new String[]{"xycode", "nation_code", "name", "short_name", "english_name", "short_spell", "first_letter", "air_code", "train_code"
                                        , "post_code", "taxi", "long_bus", "tags", "enabled"},
                                new String[]{c.getXycode(), c.getNation_code(), c.getName(), c.getShort_name(), c.getEnglish_name(), c.getShort_spell(), c.getFirst_letter(), c.getAir_code()
                                        , c.getTrain_code(), c.getPost_code(), c.getTaxi(), c.getLong_bus(), c.getTags(), c.getEnabled()},
                                "xycode", c.getXycode());
                        LogUtils.e("更新t_base_citys,城市名称" + c.getName());
                    }
                int citysVersion = dbData.getResult().getCitys().getVersion();
                SharedPreferences sp = getSharedPreferences("dbVersion", MODE_PRIVATE);
                sp.edit().putInt("citysVersion", citysVersion).commit();

            }
            //locations数据不为空
            if (dbData.getResult().getLocations() != null) {
                List<DbData.ResultBean.LocationsBean.lBean> insert_locations = dbData.getResult().getLocations().getInserts();
                if (insert_locations != null)
                    for (int i = 0; i < insert_locations.size(); i++) {
                        DbData.ResultBean.LocationsBean.lBean l = insert_locations.get(i);
                        db.insertCity("t_base_locations",
                                new String[]{"id", "catalog", "code", "name", "xycode", "lng", "lat", "enabled"},
                                new String[]{l.getId(), l.getCatalog(), l.getCode(), l.getName(), l.getXycode(), l.getLng(), l.getLat(), l.getEnabled()});
                        LogUtils.e("插入t_base_locations,位置名称" + l.getName());
                    }

                List<DbData.ResultBean.LocationsBean.lBean> update_locations = dbData.getResult().getLocations().getUpdates();
                if (update_locations != null)
                    for (int i = 0; i < update_locations.size(); i++) {
                        DbData.ResultBean.LocationsBean.lBean l = update_locations.get(i);
                        db.updateCity("t_base_locations", new String[]{"id", "catalog", "code", "name", "xycode", "lng", "lat", "enabled"},
                                new String[]{l.getId(), l.getCatalog(), l.getCode(), l.getName(), l.getXycode(), l.getLng(), l.getLat(), l.getEnabled()},
                                "id", l.getId());
                        LogUtils.e("更新t_base_locations,位置名称" + l.getName());
                    }
                int locationsVersion = dbData.getResult().getLocations().getVersion();
                SharedPreferences sp = getSharedPreferences("dbVersion", MODE_PRIVATE);
                sp.edit().putInt("locationsVersion", locationsVersion).commit();
            }
        }


//        SQLBean bean = new Gson().fromJson(SQLBean.getJsonStr(), SQLBean.class);
//        List<SQLBean.ContentBean> contents = bean.getContent();
//        for (int i = 0; i < contents.size(); i++) {
//            String letter = contents.get(i).getLetter();
//            List<SQLBean.ContentBean.ValueBean> values = contents.get(i).getValue();
//            for (int j = 0; j < values.size(); j++) {
//                String code = values.get(j).getCode();
//                String name = values.get(j).getName();
//                db.insertCity("city_train",
//                        new String[]{"cityName", "cityCode", "first_spelling", "first_spells", "all_spells"},
//                        new String[]{name, code, letter.toUpperCase(), PinyinUtil.getPinYinFirst(name), PinyinUtil.getPinYin(name)});
//            }
//        }

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
