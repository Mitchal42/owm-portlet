import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ivan on 03.07.15.
 */

public abstract class AbstractWeather extends AbstractResponse {
    static final String JSON_CLOUDS = "clouds";
    static final String JSON_COORD = "coord";
    static final String JSON_MAIN = "main";
    static final String JSON_WIND = "wind";
    private static final String JSON_WEATHER = "weather";
    private static final String JSON_DATE_TIME = "dt";

    private final Date dateTime;

    private final int weatherCount;
    private final List<Weather> weatherList;

    AbstractWeather() {
        super();

        this.weatherCount = 0;
        this.weatherList = null;
        this.dateTime = null;
    }

    AbstractWeather(JSONObject jsonObj) {
        super(jsonObj);

        long sec = (jsonObj != null) ? jsonObj.optLong(JSON_DATE_TIME, Long.MIN_VALUE) : Long.MIN_VALUE;
        if (sec != Long.MIN_VALUE) { // converting seconds to Date object
            this.dateTime = new Date(sec * 1000);
        } else {
            this.dateTime = null;
        }

        JSONArray weatherArray = (jsonObj != null) ? jsonObj.optJSONArray(JSON_WEATHER) : new JSONArray();
        this.weatherList = (weatherArray != null) ? new ArrayList<Weather>(weatherArray.length()) : Collections.EMPTY_LIST;
        if (weatherArray != null && this.weatherList != Collections.EMPTY_LIST) {
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObj = weatherArray.optJSONObject(i);
                if (weatherObj != null) {
                    this.weatherList.add(new Weather(weatherObj));
                }
            }
        }
        this.weatherCount = this.weatherList.size();
    }

    public boolean hasDateTime() {
        return this.dateTime != null;
    }

    public boolean hasWeatherInstance() {
        return weatherCount != 0;
    }

    public int getWeatherCount() {
        return this.weatherCount;
    }

    public Weather getWeatherInstance(int index) {
        return this.weatherList.get(index);
    }

    abstract public static class Clouds implements Serializable {
        private static final String JSON_CLOUDS_ALL = "all";

        private final float percentOfClouds;

        Clouds() {
            this.percentOfClouds = Float.NaN;
        }

        Clouds(JSONObject jsonObj) {
            this.percentOfClouds = (float) jsonObj.optDouble(JSON_CLOUDS_ALL, Double.NaN);
        }

        public boolean hasPercentageOfClouds() {
            return !Float.isNaN(this.percentOfClouds);
        }

        public float getPercentageOfClouds() {
            return this.percentOfClouds;
        }
    }

    abstract public static class Coord implements Serializable {
        private static final String JSON_COORD_LATITUDE = "lat";
        private static final String JSON_COORD_LONGITUDE = "lon";

        private final float lat;
        private final float lon;

        Coord() {
            this.lat = Float.NaN;
            this.lon = Float.NaN;
        }

        Coord(JSONObject jsonObj) {
            this.lat = (float) jsonObj.optDouble(JSON_COORD_LATITUDE, Double.NaN);
            this.lon = (float) jsonObj.optDouble(JSON_COORD_LONGITUDE, Double.NaN);
        }

        public boolean hasLatitude() {
            return !Float.isNaN(this.lat);
        }

        public boolean hasLongitude() {
            return !Float.isNaN(this.lon);
        }

        public float getLatitude() {
            return this.lat;
        }

        public float getLongitude() {
            return this.lon;
        }
    }

    abstract public static class Main implements Serializable {
        private static final String JSON_MAIN_TEMP = "temp";
        private static final String JSON_MAIN_TEMP_MIN = "temp_min";
        private static final String JSON_MAIN_TEMP_MAX = "temp_max";
        private static final String JSON_MAIN_PRESSURE = "pressure";
        private static final String JSON_MAIN_HUMIDITY = "humidity";

        private final float temp;
        private final float minTemp;
        private final float maxTemp;
        private final float pressure;
        private final float humidity;

        Main() {
            this.temp = Float.NaN;
            this.minTemp = Float.NaN;
            this.maxTemp = Float.NaN;
            this.pressure = Float.NaN;
            this.humidity = Float.NaN;
        }

        Main(JSONObject jsonObj) {
            this.temp = (float) jsonObj.optDouble(JSON_MAIN_TEMP, Double.NaN);
            this.minTemp = (float) jsonObj.optDouble(JSON_MAIN_TEMP_MIN, Double.NaN);
            this.maxTemp = (float) jsonObj.optDouble(JSON_MAIN_TEMP_MAX, Double.NaN);
            this.pressure = (float) jsonObj.optDouble(JSON_MAIN_PRESSURE, Double.NaN);
            this.humidity = (float) jsonObj.optDouble(JSON_MAIN_HUMIDITY, Double.NaN);
        }

        public boolean hasTemperature() {
            return !Float.isNaN(this.temp);
        }

        public boolean hasMinTemperature() {
            return !Float.isNaN(this.minTemp);
        }

        public boolean hasMaxTemperature() {
            return !Float.isNaN(this.maxTemp);
        }

        public boolean hasPressure() {
            return !Float.isNaN(this.pressure);
        }

        public boolean hasHumidity() {
            return !Float.isNaN(this.humidity);
        }

        public float getTemperature() {
            return this.temp;
        }

        public float getMinTemperature() {
            return this.minTemp;
        }

        public float getMaxTemperature() {
            return this.maxTemp;
        }

        public float getPressure() {
            return this.pressure;
        }

        public float getHumidity() {
            return this.humidity;
        }
    }

    public static class Weather implements Serializable {
        private static final String JSON_WEATHER_ID = "id";
        private static final String JSON_WEATHER_MAIN = "main";
        private static final String JSON_WEATHER_DESCRIPTION = "description";
        private static final String JSON_WEATHER_ICON = "icon";

        private final int id;
        private final String name;
        private final String description;
        private final String icon;

        Weather() {
            this.id = Integer.MIN_VALUE;
            this.name = null;
            this.description = null;
            this.icon = null;
        }

        Weather(JSONObject jsonObj) {
            this.id = jsonObj.optInt(JSON_WEATHER_ID, Integer.MIN_VALUE);
            this.name = jsonObj.optString(JSON_WEATHER_MAIN, null);
            this.description = jsonObj.optString(JSON_WEATHER_DESCRIPTION, null);
            this.icon = "http://openweathermap.org/img/w/"+jsonObj.optString(JSON_WEATHER_ICON, null)+".png";
        }

        public boolean hasWeatherCode() {
            return this.id != Integer.MIN_VALUE;
        }

        public boolean hasWeatherName() {
            return this.name != null && (! "".equals(this.name));
        }

        public boolean hasWeatherDescription() {
            return this.description != null && (! "".equals(this.description));
        }

        public boolean hasWeatherIconName() {
            return this.icon != null && (! "".equals(this.icon));
        }

        public int getWeatherCode() {
            return this.id;
        }

        public String getWeatherName() {
            return this.name;
        }

        public String getWeatherDescription() {
            return this.description;
        }

        public String getWeatherIconName() {
            return this.icon;
        }
    }

    abstract public static class Wind implements Serializable {
        private static final String JSON_WIND_SPEED = "speed";
        private static final String JSON_WIND_DEGREE = "deg";

        private final float speed;
        private final float degree;

        Wind() {
            this.speed = Float.NaN;
            this.degree = Float.NaN;
        }

        Wind(JSONObject jsonObj) {
            this.speed = (float) jsonObj.optDouble(JSON_WIND_SPEED, Double.NaN);
            this.degree = (float) jsonObj.optDouble(JSON_WIND_DEGREE, Double.NaN);
        }

        public boolean hasWindSpeed() {
            return !Float.isNaN(this.speed);
        }

        public boolean hasWindDegree() {
            return this.hasWindSpeed() && (! Float.isNaN(this.degree));
        }

        public float getWindSpeed() {
            return this.speed;
        }

        public float getWindDegree() {
            return this.degree;
        }
    }
}