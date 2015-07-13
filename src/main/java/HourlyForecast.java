import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HourlyForecast extends AbstractForecast {
    private final List<Forecast> forecastList;

    HourlyForecast(JSONObject jsonObj) {
        super(jsonObj);

        JSONArray forecastArr = (jsonObj != null) ? jsonObj.optJSONArray(this.JSON_FORECAST_LIST) : new JSONArray();
        this.forecastList = (forecastArr != null) ? new ArrayList<Forecast>(forecastArr.length()) : Collections.EMPTY_LIST;
        if (forecastArr != null && this.forecastList != Collections.EMPTY_LIST) {
            for (int i = 0; i < forecastArr.length(); i++) {
                JSONObject forecastObj = forecastArr.optJSONObject(i);
                if (forecastObj != null) {
                    this.forecastList.add(new Forecast(forecastObj));
                }
            }
        }
    }

    public Forecast getForecastInstance(int index) {
        return this.forecastList.get(index);
    }

    public static class Forecast extends AbstractForecast.Forecast {
        private static final String JSON_SYS = "sys";
        private static final String JSON_DT_TEXT = "dt_txt";

        private final String dateTimeText;

        private final Clouds clouds;
        private final Main main;
        private final Sys sys;
        private final Wind wind;

        Forecast(JSONObject jsonObj) {
            super(jsonObj);

            this.dateTimeText = (jsonObj != null) ? jsonObj.optString(JSON_DT_TEXT, null) : null;

            JSONObject jsonObjClouds = (jsonObj != null) ? jsonObj.optJSONObject(JSON_CLOUDS) : null;
            this.clouds = (jsonObjClouds != null) ? new Clouds(jsonObjClouds) : null;

            JSONObject jsonObjMain = (jsonObj != null) ? jsonObj.optJSONObject(JSON_MAIN) : null;
            this.main = (jsonObjMain != null) ? new Main(jsonObjMain) : null;

            JSONObject jsonObjSys = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SYS) : null;
            this.sys = (jsonObjSys != null) ? new Sys(jsonObjSys) : null;

            JSONObject jsonObjWind = (jsonObj != null) ? jsonObj.optJSONObject(JSON_WIND) : null;
            this.wind = (jsonObjWind != null) ? new Wind(jsonObjWind) : null;
        }

        public boolean hasDateTimeText() {
            return this.dateTimeText != null;
        }

        public boolean hasCloudsInstance() {
            return clouds != null;
        }

        public boolean hasMainInstance() {
            return main != null;
        }

        public boolean hasSysInstance() {
            return sys != null;
        }

        public boolean hasWindInstance() {
            return wind != null;
        }

        public String getDateTimeText() {
            return this.dateTimeText;
        }

        public Clouds getCloudsInstance() {
            return this.clouds;
        }

        public Main getMainInstance() {
            return this.main;
        }

        public Sys getSysInstance() {
            return this.sys;
        }

        public Wind getWindInstance() {
            return this.wind;
        }

        public static class Clouds extends AbstractForecast.Forecast.Clouds {
            Clouds() {
                super();
            }

            Clouds(JSONObject jsonObj) {
                super(jsonObj);
            }
        }

        public static class Main extends AbstractForecast.Forecast.Main {
            private static final String JSON_MAIN_SEA_LEVEL = "sea_level";
            private static final String JSON_MAIN_GRND_LEVEL = "grnd_level";
            private static final String JSON_MAIN_TMP_KF = "temp_kf";

            private final float seaLevel;
            private final float groundLevel;
            private final float tempKF;

            Main() {
                super();

                this.seaLevel = Float.NaN;
                this.groundLevel = Float.NaN;
                this.tempKF = Float.NaN;
            }

            Main(JSONObject jsonObj) {
                super(jsonObj);

                this.seaLevel = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_SEA_LEVEL, Float.NaN) : Float.NaN;
                this.groundLevel = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_GRND_LEVEL, Float.NaN) : Float.NaN;
                this.tempKF = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_MAIN_TMP_KF, Float.NaN) : Float.NaN;
            }

            public boolean hasSeaLevel() {
                return !Float.isNaN(this.seaLevel);
            }

            public boolean hasGroundLevel() {
                return !Float.isNaN(this.groundLevel);
            }

            public boolean hasTempKF() {
                return !Float.isNaN(this.tempKF);
            }

            public float getSeaLevel() {
                return this.seaLevel;
            }

            public float getGroundLevel() {
                return this.groundLevel;
            }

            public float getTempKF() {
                return this.tempKF;
            }
        }

        public static class Sys implements Serializable {
            private static final String JSON_SYS_POD = "pod";

            private final String pod;

            Sys() {
                this.pod = null;
            }

            Sys(JSONObject jsonObj) {
                this.pod = (jsonObj != null) ? jsonObj.optString(JSON_SYS_POD, null) : null;
            }

            public boolean hasPod() {
                return this.pod != null && (! "".equals(this.pod));
            }

            public String getPod() {
                return this.pod;
            }
        }

        public static class Wind extends AbstractWeather.Wind {
            Wind() {
                super();
            }

            Wind(JSONObject jsonObj) {
                super(jsonObj);
            }
        }
    }
}