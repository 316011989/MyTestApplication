package test.zyl.mytestapplication.http;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.apkfuns.logutils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DBViewModel extends AndroidViewModel {
    Application app;
    int citysVersion;
    int locationsVersion;
    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    //生命周期观察的数据，可使uiObservableData变更
    private LiveData<DbData> mLiveObservableData;
    //UI使用可观察的数据 ObservableField是一个包装类，可使UI变更
    public ObservableField<DbData> uiObservableData = new ObservableField<>();
    //动态数据，请求获取，可使mLiveObservableData变更
    private MutableLiveData<DbData> applyData = new MutableLiveData<>();

    //后台调度，可关闭
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public DBViewModel(@NonNull Application application, int citysVersion, int locationsVersion) {
        super(application);
        this.app = application;
        this.citysVersion = citysVersion;
        this.locationsVersion = locationsVersion;
        mLiveObservableData = Transformations.switchMap(NetUtils.netConnected(application), new Function<Boolean, LiveData<DbData>>() {
            @Override
            public LiveData<DbData> apply(Boolean isNetConnected) {
                if (!isNetConnected) {
                    Toast.makeText(app, "网络异常", Toast.LENGTH_SHORT).show();
                    return ABSENT;
                }
                LoadData();
                return applyData;
            }
        });
    }

    /**
     * 请求数据接口
     */
    private void LoadData() {
        DbDataRepository.getDataRepository(String.valueOf(citysVersion), String.valueOf(locationsVersion))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DbData>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(DbData value) {
                        LogUtils.e(value);
                        applyData.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete");
                    }
                });
    }

    /**
     * 设置
     *
     * @param product
     */

    public void setUiObservableData(DbData product) {
        this.uiObservableData.set(product);
    }

    /**
     * LiveData支持了lifecycle生命周期检测
     *
     * @return
     */
    public LiveData<DbData> getLiveObservableData() {
        return mLiveObservableData;
    }
}
