package test.zyl.mytestapplication.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DbDataService {

    /**
     * 动态
     *
     * @param city
     * @param location
     * @return
     */
    @GET("/xingyun/v1/base/diff")
    Observable<DbData> getDbData(@Query("city") String city, @Query("location") String location);
}
