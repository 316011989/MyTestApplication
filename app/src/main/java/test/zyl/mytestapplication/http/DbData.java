package test.zyl.mytestapplication.http;

import java.util.List;

import lombok.Data;

@Data
public class DbData {

    private int status;
    private String msg;
    private ResultBean result;


    @Data
    public static class ResultBean {

        private CitysBean citys;
        private LocationsBean locations;

        @Data
        public static class CitysBean {

            private int version;
            private List<cBean> inserts;
            private List<cBean> updates;

            @Data
            public static class cBean {
                private String xycode;
                private String nation_code;
                private String name;
                private String short_name;
                private String english_name;
                private String short_spell;
                private String first_letter;
                private String air_code;
                private String train_code;
                private String post_code;
                private String taxi;
                private String long_bus;
                private String tags;
                private String enabled;
            }

        }

        @Data
        public static class LocationsBean {

            private int version;
            private List<lBean> inserts;
            private List<lBean> updates;

            @Data
            public static class lBean {
                private String id;
                private String catalog;
                private String code;
                private String name;
                private String xycode;
                private String lng;
                private String lat;
                private String enabled;
            }
        }
    }
}
