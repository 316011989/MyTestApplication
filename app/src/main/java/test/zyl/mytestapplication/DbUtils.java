package test.zyl.mytestapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by zhangyl on 2016/9/12.
 */
public class DbUtils {
    Context context;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    private String dbDirPath = "/data/data/test.zyl.mytestapplication/databases";

    public DbUtils(Context context) {
        this.context = context;

        helper = new SQLiteOpenHelper(context, "xingyun.db", null, 5) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * @param context
     */
    public void updateDB(Context context) {
        try {
            File dbDir = new File(dbDirPath);
            if (!dbDir.exists()) // 如果不存在该目录则创建
                dbDir.mkdir();
            // 打开静态数据库文件的输入流
            InputStream is = context.getResources().openRawResource(R.raw.xingyun);
            // 打开目标数据库文件的输出流
            FileOutputStream os = new FileOutputStream(dbDirPath + "/xingyun.db");
            byte[] buffer = new byte[1024];
            int count;
            // 将静态数据库文件拷贝到目的地
            while ((count = is.read(buffer)) > 0) {
                os.write(buffer, 0, count);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initCityTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS city_train(" +
                "_id integer  PRIMARY KEY AUTOINCREMENT DEFAULT NULL," +
                "cityName  VARCHAR(100) ," +
                "cityCode  VARCHAR(100) ," +
                "pinyin  VARCHAR(100) ," +
                "firstCode VARCHAR(100));");
    }


    public void insertCitiesToTable(SQLBean list) {
        ArrayList<SQLBean.Content> content = list.getContent();
        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).getValue().size(); j++) {
                SQLBean.Content.CitiesBean b = content.get(i).getValue().get(j);
                db.execSQL("insert into city_train (cityName, cityCode ,first_spelling,first_spells,all_spells) VALUES ('"
                        + b.getName() + "', '" + b.getCode() + "','" + b.getFirst_spelling() + "','" + b.getFirst_spells() + "','" + b.getAll_spells() + "')");
            }
        }
    }

    public <C> ArrayList<C> getData2List(String dbName, Class<C> cls) {
        ArrayList<C> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + dbName, null);
        while (cursor.moveToNext()) {
            if (cls.getSimpleName().equals("HotelBean")) {
                MainActivity.HotelBean hotel = new MainActivity.HotelBean();
                hotel.setAll_spells(cursor.getString(cursor.getColumnIndex("all_spells")));
                hotel.setChannel_id(cursor.getString(cursor.getColumnIndex("channel_id")));
                hotel.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                hotel.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                hotel.setCountry(cursor.getString(cursor.getColumnIndex("Country")));
                hotel.setFirst_spelling(cursor.getString(cursor.getColumnIndex("first_spelling")));
                hotel.setFirst_spells(cursor.getString(cursor.getColumnIndex("first_spells")));
                hotel.setGeo_id(cursor.getString(cursor.getColumnIndex("geo_id")));
                hotel.setProvince_id(cursor.getString(cursor.getColumnIndex("province_id")));
                hotel.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add((C) hotel);
            } else if (cls.getSimpleName().equals("ApprovalCityBean")) {
                MainActivity.ApprovalCityBean city = new MainActivity.ApprovalCityBean();
                city.setId(cursor.getString(cursor.getColumnIndex("id")));
                city.setArea_id(cursor.getString(cursor.getColumnIndex("area_id")));
                city.setArea_name(cursor.getString(cursor.getColumnIndex("area_name")));
                city.setAll_spells(cursor.getString(cursor.getColumnIndex("all_spells")));
                city.setFirst_spelling(cursor.getString(cursor.getColumnIndex("first_spelling")));
                city.setFirst_spells(cursor.getString(cursor.getColumnIndex("first_spells")));
                list.add((C) city);
            } else if (cls.getSimpleName().equals("IntlCityBean")) {
                MainActivity.IntlCityBean city = new MainActivity.IntlCityBean();
                city.setCity_id(cursor.getString(cursor.getColumnIndex("city_id")));
                city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setArea_code(cursor.getString(cursor.getColumnIndex("area_code")));
                city.setAll_spells(cursor.getString(cursor.getColumnIndex("all_spells")));
                city.setFirst_spelling(cursor.getString(cursor.getColumnIndex("first_spelling")));
                city.setFirst_spells(cursor.getString(cursor.getColumnIndex("first_spells")));
                list.add((C) city);
            }
        }
        return list;
    }


    /**
     * 取出城市名
     *
     * @param dbName 表名
     * @param key    查询字段
     * @return 查询字段集合
     */
    public List<String> getCityNames(String dbName, String key) {
        List<String> list = new ArrayList<>();
        Cursor c = db.rawQuery("select " + key + " from " + dbName, null);
        c.moveToFirst();
        list.add(c.getString(c.getColumnIndex(key)));
        while (c.moveToNext()) {
            list.add(c.getString(c.getColumnIndex(key)));
        }
        return list;
    }


    /**
     * 修改数据库
     *
     * @param tableName 表名
     * @param colums    要修改的字段
     * @param values    要修改的值
     * @param keyName   关键字
     * @param keyValue  关键字值
     */
    public void updateCity(String tableName, String[] colums, String[] values, String keyName, String keyValue) {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("update ");
        sqlStr.append(tableName);
        if (colums != null && values != null && colums.length == values.length) {
            sqlStr.append(" set ");
            for (int i = 0; i < colums.length; i++) {
                sqlStr.append(colums[i]);
                sqlStr.append(" = '");
                sqlStr.append(values[i]);
                sqlStr.append("'");
                if (i + 1 < colums.length)
                    sqlStr.append(" , ");
            }
            sqlStr.append(" where ");
            sqlStr.append(keyName);
            sqlStr.append(" = '");
            sqlStr.append(keyValue + "'");
            db.execSQL(sqlStr.toString());
        } else {
            Toast.makeText(context, "修改字段或值不正确", Toast.LENGTH_SHORT).show();
        }
    }


    public void closeDb() {
        db.close();
    }

    /**
     * 修改数据库
     *
     * @param tableName 表名
     * @param colums    要修改的字段
     * @param values    要修改的值
     */
    public void insertCity(String tableName, String[] colums, String[] values) {
        StringBuilder sqlStr = new StringBuilder();
        if (colums != null && values != null && colums.length == values.length) {
            sqlStr.append("insert into ");//INSERT INTO
            sqlStr.append(tableName);//`xingyun_bank`
            sqlStr.append(" ( ");//(
            for (int i = 0; i < colums.length; i++) {//'city_name','first_spelling','first_spells','all_spells'
                sqlStr.append("'");
                sqlStr.append(colums[i]);
                sqlStr.append("'");
                if (i + 1 < colums.length)
                    sqlStr.append(" , ");
            }
            sqlStr.append(" ) VALUES  ( ");//) VALUES (
            for (int i = 0; i < colums.length; i++) {//'中国人民银行', 'Z', 'ZGRMYH', 'ZHONGGUORENMINYINHANG'
                sqlStr.append("'");
                sqlStr.append(values[i]);
                sqlStr.append("'");
                if (i + 1 < colums.length)
                    sqlStr.append(" , ");
            }
            sqlStr.append(" ) ");//)
            db.execSQL(sqlStr.toString());
        } else {
            Toast.makeText(context, "修改字段或值不正确", Toast.LENGTH_SHORT).show();
        }
    }
}
