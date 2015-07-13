import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ivan on 03.07.15.
 */

public class DailyForecast extends AbstractForecast {
    private final List<Forecast> forecastList;

    DailyForecast(JSONObject jsonObj) {
        super(jsonObj);

        JSONArray dataArray = (jsonObj != null) ? jsonObj.optJSONArray(JSON_FORECAST_LIST) : new JSONArray();
        this.forecastList = (dataArray != null) ? new ArrayList<Forecast>(dataArray.length()) : Collections.EMPTY_LIST;
        if (dataArray != null && this.forecastList != Collections.EMPTY_LIST) {
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject forecastObj = dataArray.optJSONObject(i);
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
        public static final String JSON_TEMP = "temp";

        private static final String JSON_FORECAST_PRESSURE = "pressure";
        private static final String JSON_FORECAST_HUMIDITY = "humidity";
        private static final String JSON_FORECAST_WIND_SPEED = "speed";
        private static final String JSON_FORECAST_WIND_DEGREE = "deg";
        private static final String JSON_FORECAST_CLOUDS = "clouds";
        private static final String JSON_FORECAST_RAIN = "rain";
        private static final String JSON_FORECAST_SNOW = "snow";

        private final float pressure;
        private final float humidity;
        private final float windSpeed;
        private final float windDegree;
        private final float cloudsPercent;
        private final float rain;
        private final float snow;

        private final Temperature temp;

        Forecast() {
            super();

            this.pressure = Float.NaN;
            this.humidity = Float.NaN;
            this.windSpeed = Float.NaN;
            this.windDegree = Float.NaN;
            this.cloudsPercent = Float.NaN;
            this.rain = Float.NaN;
            this.snow = Float.NaN;

            this.temp = new Temperature();
        }

        Forecast(JSONObject jsonObj) {
            super(jsonObj);

            JSONObject jsonObjTemp = (jsonObj != null) ? jsonObj.optJSONObject(JSON_TEMP) : null;
            this.temp = (jsonObjTemp != null) ? new Temperature(jsonObjTemp) : new Temperature();

            this.humidity = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_HUMIDITY, Double.NaN) : Float.NaN;
            this.pressure = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_PRESSURE, Double.NaN) : Float.NaN;
            this.windSpeed = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_WIND_SPEED, Double.NaN) : Float.NaN;
            this.windDegree = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_WIND_DEGREE, Double.NaN) : Float.NaN;
            this.cloudsPercent = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_CLOUDS, Double.NaN) : Float.NaN;
            this.rain = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_RAIN, Double.NaN) : Float.NaN;
            this.snow = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_FORECAST_SNOW, Double.NaN) : Float.NaN;
        }

        public boolean hasHumidity() {
            return !Float.isNaN(this.humidity);
        }

        public boolean hasPressure() {
            return !Float.isNaN(this.pressure);
        }

        public boolean hasWindSpeed() {
            return !Float.isNaN(this.windSpeed);
        }

        public boolean hasWindDegree() {
            return !Float.isNaN(this.windDegree);
        }

        public boolean hasPercentageOfClouds() {
            return !Float.isNaN(this.cloudsPercent);
        }

        public boolean hasRain() {
            return !Float.isNaN(this.rain);
        }

        public boolean hasSnow() {
            return !Float.isNaN(this.snow);
        }

        public float getHumidity() {
            return this.humidity;
        }

        public float getPressure() {
            return this.pressure;
        }

        public float getWindSpeed() {
            return this.windSpeed;
        }

        public float getWindDegree() {
            return this.windDegree;
        }

        public float getPercentageOfClouds() {
            return this.cloudsPercent;
        }

        public float getRain() {
            return this.rain;
        }

        public float getSnow() {
            return this.snow;
        }

        public Temperature getTemperatureInstance() {
            return this.temp;
        }
    }

    public static class Temperature implements Serializable {
        private static final String JSON_TEMP_DAY = "day";
        private static final String JSON_TEMP_MIN = "min";
        private static final String JSON_TEMP_MAX = "max";
        private static final String JSON_TEMP_NIGHT = "night";
        private static final String JSON_TEMP_EVENING = "eve";
        private static final String JSON_TEMP_MORNING = "morn";

        private final float dayTemp;
        private final float minTemp;
        private final float maxTemp;
        private final float nightTemp;
        private final float eveTemp;
        private final float mornTemp;

        Temperature() {
            this.dayTemp = Float.NaN;
            this.minTemp = Float.NaN;
            this.maxTemp = Float.NaN;
            this.nightTemp = Float.NaN;
            this.eveTemp = Float.NaN;
            this.mornTemp = Float.NaN;
        }

        Temperature(JSONObject jsonObj) {
            this.dayTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_DAY, Double.NaN) : Float.NaN;
            this.minTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_MIN, Double.NaN) : Float.NaN;
            this.maxTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_MAX, Double.NaN) : Float.NaN;
            this.nightTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_NIGHT, Double.NaN) : Float.NaN;
            this.eveTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_EVENING, Double.NaN) : Float.NaN;
            this.mornTemp = (jsonObj != null) ? (float) jsonObj.optDouble(JSON_TEMP_MORNING, Double.NaN) : Float.NaN;
        }

        public boolean hasDayTemperature() {
            return !Float.isNaN(this.dayTemp);
        }

        public boolean hasMinimumTemperature() {
            return !Float.isNaN(this.minTemp);
        }

        public boolean hasMaximumTemperature() {
            return !Float.isNaN(this.maxTemp);
        }

        public boolean hasNightTemperature() {
            return !Float.isNaN(this.nightTemp);
        }

        public boolean hasEveningTemperature() {
            return !Float.isNaN(this.eveTemp);
        }

        public boolean hasMorningTemperature() {
            return !Float.isNaN(this.mornTemp);
        }

        public float getDayTemperature() {
            return this.dayTemp;
        }

        public float getMinimumTemperature() {
            return this.minTemp;
        }

        public float getMaximumTemperature() {
            return this.maxTemp;
        }

        public float getNightTemperature() {
            return this.nightTemp;
        }

        public float getEveningTemperature() {
            return this.eveTemp;
        }

        public float getMorningTemperature() {
            return this.mornTemp;
        }
    }
}