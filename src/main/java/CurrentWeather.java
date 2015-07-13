import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ivan on 03.07.15.
 */

public class CurrentWeather extends AbstractWeather {
    private static final String JSON_RAIN = "rain";
    private static final String JSON_SNOW = "snow";
    private static final String JSON_SYS = "sys";
    private static final String JSON_BASE = "base";
    private static final String JSON_CITY_ID = "id";
    private static final String JSON_CITY_NAME = "name";

    private final String base;
    private final long cityId;
    private final String cityName;

    private final Clouds clouds;
    private final Coord coord;
    private final Main main;
    private final Rain rain;
    private final Snow snow;
    private final Sys sys;
    private final Wind wind;

    CurrentWeather(JSONObject jsonObj) {
        super(jsonObj);

        this.base = (jsonObj != null) ? jsonObj.optString(JSON_BASE, null) : null;
        this.cityId = (jsonObj != null) ? jsonObj.optLong(JSON_CITY_ID, Long.MIN_VALUE) : Long.MIN_VALUE;
        this.cityName = (jsonObj != null) ? jsonObj.optString(JSON_CITY_NAME, null) : null;

        JSONObject cloudsObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_CLOUDS) : null;
        this.clouds = (cloudsObj != null) ? new Clouds(cloudsObj) : null;

        JSONObject coordObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_COORD) : null;
        this.coord = (coordObj != null) ? new Coord(coordObj) : null;

        JSONObject mainObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_MAIN) : null;
        this.main = (mainObj != null) ? new Main(mainObj) : null;

        JSONObject rainObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_RAIN) : null;
        this.rain = (rainObj != null) ? new Rain(rainObj) : null;

        JSONObject snowObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SNOW) : null;
        this.snow = (snowObj != null) ? new Snow(snowObj) : null;

        JSONObject sysObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_SYS) : null;
        this.sys = (sysObj != null) ? new Sys(sysObj) : null;

        JSONObject windObj = (jsonObj != null) ? jsonObj.optJSONObject(JSON_WIND) : null;
        this.wind = (windObj != null) ? new Wind(windObj) : null;
    }

    public boolean hasBaseStation() {
        return this.base != null && (! "".equals(this.base));
    }

    public boolean hasCityCode() {
        return this.cityId != Long.MIN_VALUE;
    }

    public boolean hasCityName() {
        return this.cityName != null && (! "".equals(this.cityName));
    }

    public boolean hasCloudsInstance() {
        return clouds != null;
    }

    public boolean hasCoordInstance() {
        return coord != null;
    }

    public boolean hasMainInstance() {
        return main != null;
    }

    public boolean hasRainInstance() {
        return rain != null;
    }

    public boolean hasSnowInstance() {
        return snow != null;
    }

    public boolean hasSysInstance() {
        return sys != null;
    }

    public boolean hasWindInstance() {
        return wind != null;
    }

    public String getBaseStation() {
        return this.base;
    }

    public long getCityCode() {
        return this.cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Clouds getCloudsInstance() {
        return this.clouds;
    }

    public Coord getCoordInstance() {
        return this.coord;
    }

    public Main getMainInstance() {
        return this.main;
    }

    public Rain getRainInstance() {
        return this.rain;
    }

    public Snow getSnowInstance() {
        return this.snow;
    }

    public Sys getSysInstance() {
        return this.sys;
    }

    public Wind getWindInstance() {
        return this.wind;
    }

    public static class Clouds extends AbstractWeather.Clouds {
        Clouds() {
            super();
        }

        Clouds(JSONObject jsonObj) {
            super(jsonObj);
        }
    }

    public static class Coord extends AbstractWeather.Coord {
        Coord() {
            super();
        }

        Coord(JSONObject jsonObj) {
            super(jsonObj);
        }
    }

    public static class Main extends AbstractWeather.Main {
        Main() {
            super();
        }

        Main(JSONObject jsonObj) {
            super(jsonObj);
        }
    }

    public static class Rain implements Serializable {
        private static final String JSON_RAIN_1HOUR = "1h";
        private static final String JSON_RAIN_3HOUR = "3h";

        private final float rain1h;
        private final float rain3h;

        Rain() {
            this.rain1h = Float.NaN;
            this.rain3h = Float.NaN;
        }

        Rain(JSONObject jsonObj) {
            this.rain1h = (float) jsonObj.optDouble(JSON_RAIN_1HOUR, Double.NaN);
            this.rain3h = (float) jsonObj.optDouble(JSON_RAIN_3HOUR, Double.NaN);
        }

        public boolean hasRain1h() {
            return !Float.isNaN(this.rain1h);
        }

        public boolean hasRain3h() {
            return !Float.isNaN(this.rain3h);
        }

        public float getRain1h() {
            return this.rain1h;
        }

        public float getRain3h() {
            return this.rain3h;
        }
    }

    public static class Snow implements Serializable {
        private static final String JSON_SNOW_1HOUR = "1h";
        private static final String JSON_SNOW_3HOUR = "3h";

        private final float snow1h;
        private final float snow3h;

        Snow() {
            this.snow1h = Float.NaN;
            this.snow3h = Float.NaN;
        }

        Snow(JSONObject jsonObj) {
            this.snow1h = (float) jsonObj.optDouble(JSON_SNOW_1HOUR, Double.NaN);
            this.snow3h = (float) jsonObj.optDouble(JSON_SNOW_3HOUR, Double.NaN);
        }

        public boolean hasSnow1h() {
            return !Float.isNaN(this.snow1h);
        }

        public boolean hasSnow3h() {
            return !Float.isNaN(this.snow3h);
        }

        public float getSnow1h() {
            return this.snow1h;
        }

        public float getSnow3h() {
            return this.snow3h;
        }
    }

    public static class Sys implements Serializable {
        private static final String JSON_SYS_TYPE = "type";
        private static final String JSON_SYS_ID = "id";
        private static final String JSON_SYS_MESSAGE = "message";
        private static final String JSON_SYS_COUNTRY_CODE = "country";
        private static final String JSON_SYS_SUNRISE = "sunrise";
        private static final String JSON_SYS_SUNSET = "sunset";

        private final int type;
        private final int id;
        private final double message;
        private final String countryCode;
        private final Date sunrise;
        private final Date sunset;

        Sys() {
            this.type = Integer.MIN_VALUE;
            this.id = Integer.MIN_VALUE;
            this.message = Double.NaN;
            this.countryCode = null;
            this.sunrise = null;
            this.sunset = null;
        }

        Sys(JSONObject jsonObj) {
            this.type = jsonObj.optInt(JSON_SYS_TYPE, Integer.MIN_VALUE);
            this.id = jsonObj.optInt(JSON_SYS_ID, Integer.MIN_VALUE);
            this.message = jsonObj.optDouble(JSON_SYS_MESSAGE, Double.NaN);
            this.countryCode = jsonObj.optString(JSON_SYS_COUNTRY_CODE, null);

            long sr_secs = jsonObj.optLong(JSON_SYS_SUNRISE, Long.MIN_VALUE);
            if (sr_secs != Long.MIN_VALUE) {
                this.sunrise = new Date(sr_secs * 1000);
            } else {
                this.sunrise = null;
            }

            long ss_secs = jsonObj.optLong(JSON_SYS_SUNSET, Long.MIN_VALUE);
            if (ss_secs != Long.MIN_VALUE) {
                this.sunset = new Date(ss_secs * 1000);
            } else {
                this.sunset = null;
            }
        }

        public boolean hasType() {
            return this.type != Integer.MIN_VALUE;
        }

        public boolean hasId() {
            return this.id != Integer.MIN_VALUE;
        }

        public boolean hasMessage() {
            return !Double.isNaN(this.message);
        }

        public boolean hasCountryCode() {
            return this.countryCode != null && (! "".equals(this.countryCode));
        }

        public boolean hasSunriseTime() {
            return this.sunrise != null;
        }

        public boolean hasSunsetTime() {
            return this.sunset != null;
        }

        public int getType() {
            return this.type;
        }

        public int getId() {
            return this.id;
        }

        public double getMessage() {
            return this.message;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public Date getSunriseTime() {
            return this.sunrise;
        }

        public Date getSunsetTime() {
            return this.sunset;
        }
    }

    public static class Wind extends AbstractWeather.Wind {
        private static final String JSON_WIND_GUST = "gust";

        private final float gust;

        Wind() {
            super();

            this.gust = Float.NaN;
        }

        Wind(JSONObject jsonObj) {
            super(jsonObj);

            this.gust = (float) jsonObj.optDouble(JSON_WIND_GUST, Double.NaN);
        }

        public boolean hasWindGust() {
            return !Float.isNaN(this.gust);
        }

        public float getWindGust() {
            return this.gust;
        }
    }
}