package test.zyl.mytestapplication;

import android.app.Application;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangyl on 2016/8/15.
 */
public class MyAppliacation extends Application implements ReactApplication {
    private String idSecret = "24687353-1";
    private String appSecret = "0f41b614d19dfcebf9039718a8ba4495";
    private String rsaSecret = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQaK3EFBVFLrgzVjTXm82bwutdGVB8nNRQ9Gm3ily/ngSJBciAK8r4HVMWFEbt8e98YQp8NjyWANvKUQDIgZRZJx1UfcGIOtwqw7Bt0T6MosgZrXgFIan8TFMu8wT1u82tiVa/zyUg4njoUyuwiwK3G81+zw1ItpSNAUSebyM3DpaKYUuJlSQGiql+rCAmLUXvuGD0J0YciQJMrIr8ifu1WFXhXhioXPAnfv3iOfvrpzknqe5og1H8r2M6JwSZKBS4z7kMMS8d3ENtcpimA1INOoO7SPNiLwkx4fEZ/Ky0XZf8Ng0y7nOd1zkcDMiYd6f9C0jID4K/EiGxjI6PaOLRAgMBAAECggEAH8+/f7NA4EWjdEOoTlrXF1oACTk7XsgZiV/eLf1/4wJHU3v/qCR9iNzE4ddDrMUgEZE7AxPbktvLjyz69DB/YohYULWHPaBhwdXr9rUih06Qr/9jshWxYY/avzvvusZNT84yWvyh3DWnlt5DgJeO2JF6WlX4Qi1eWhWObSxkYIs0ZBbjtsKyXPijzME60G5sbpuJNC6qk/w0kRQmoV34k/2+pE4KimzimL//ruGReqh4qjvmYM0EDZztTQTOY7jXmr5Zrm6SBGfktxXUJNHZCMibOC3So/g5WsspOh54W6QTiqajRcox0Lp+qnGwrg1rgjM14pCj9pJmMTseo4e6MQKBgQDGPoT6zfvOwuXlWGYn7yIgSM3otSXQnf5ZpqgUx2FXZB1aG1DC2vZvd1bEN325Xbsgho2nDXlDGiVOmtaxc2RB2+bNMCoD3SgV964KULEr/w0O1p/mt3xIlyEjZLax3rS4o9/ogCsVRthq+FsGW6w8+l6gKtbM5VXcmHbwwleo/QKBgQC6ewFusFBhaqwTcOPUVL2kWSAPT9X724P2xYhXZcmdqxv58kOdwFwTZKkYZ0cPtkykc/1cUgY7BuiTulFqvV8Ls7f9mHa7aoSZkt/cCsZZt0K3rTF3dDEbO8m49NlF246Jb/Pfbb24DGh7dId7oeJagiyPQtTDINFE38Aqy81DZQKBgQCbq+ZyqCOvFf6/XO5DN5Ydse33aaA7NIXR0nu0bWhz3orqAf8mNBlGKHk69bRFRmQ2Q1KitoLdfX+EPTSP/ePhj5Oq1sJ3bEgZD/hb40dtHAgd+c1wt+7hdcOw7lpETHSvdQfTpCmwuIQjY8jeskgLTjPWJC2ukfwdkJ/9qS565QKBgHPfySURk/DkiuZNqJQgz/JWmzBIiIvGfHqThcPg8OXzFCA85L0acVxvmQ8b5bEisv8hP9bk9RAqm8xn4v1zjKLF2tdOrMCKULjo5gdh/pxy3pcpnGpAnd7xSMiQBI5+/lPyZ9R26YN62lWUNDejgPaCmtOuUGyrcL3eVxeAqhxtAoGABQ2uv0kz5n3w/7NVIAlYyM2B5klbxL5ZZp7n71xKtQpcJyQOn1ww84jzesYYL435iWvdXUppNXzDpUkYsjFVosphp08hYPFgHCyk+uY9dKRdrrcY5yHHu8QzBJat8pTISjJQ735618b/cr8zyWYpnrDs8manmmkTYijgPEvSj/E=";

    @Override
    public void onCreate() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setSecretMetaData(idSecret, appSecret, rsaSecret)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.i("code", "mode = " + mode + "info = " + info);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.e("SophixManager", "表明补丁加载成功");
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            Log.e("SophixManager", "新补丁生效,用户可以监听进入后台事件, 然后应用自杀");
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                            Log.e("SophixManager", "内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.e("SophixManager", "其它错误信息, 查看PatchStatus类说明");
                        }
                    }
                }).initialize();
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();

    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
