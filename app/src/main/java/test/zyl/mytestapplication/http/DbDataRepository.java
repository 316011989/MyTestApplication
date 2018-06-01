package test.zyl.mytestapplication.http;

import io.reactivex.Observable;

/**
 * Created by dxx on 2017/11/8.
 */

public class DbDataRepository {

    public static Observable<DbData> getDataRepository(String size, String index) {
        Observable<DbData> observableForGetAndroidDataFromNetWork = ApiClient.getGankDataService().getDbData(size, index);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }
}
