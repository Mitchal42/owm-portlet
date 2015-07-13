import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by ivan on 03.07.15.
 */

public abstract class AbstractForecast extends AbstractResponse {
    static final String JSON_FORECAST_LIST = "list";
    static final String JSON_MESSAGE = "message";
    static final String JSON_CITY = "city";
    static final String JSON_FORECAST_COUNT = "cnt";

    private final double message;

    private final City city;
    private final int forecastCount;

    AbstractForecast() {
        super();

        this.message = Double.NaN;
        this.forecastCount = 0;
        this.city = null;
    }

    AbstractForecast(JSONObject jsonObj) {
        super(jsonObj);

        this.message = (jsonObj != null) ? jsonObj.optDouble(JSON_MESSAGE, Double.NaN) : Double.NaN;

        this.city = (jsonObj != null) ? new City(jsonObj.optJSONObject(JSON_CITY)) : null;

        this.forecastCount = (jsonObj != null) ? jsonObj.optInt(JSON_FORECAST_COUNT, 0) : 0;
    }

    public boolean hasMessage() {
        return (this.message != Double.NaN);
    }

    public boolean hasForecastCount() {
        return (this.forecastCount != 0);
    }

    public boolean hasCityInstance() {
        return (this.city != null);
    }

    public double getMessage() {
        return this.message;
    }

    public int getForecastCount() {
        return this.forecastCount;
    }

    public City getCityInstance() {
        return this.city;
    }

    public static class City implements Serializable {
        private static final String JSON_CITY_ID = "id";
        private static final String JSON_CITY_NAME = "name";
        private static final String JSON_CITY_COUNTRY_CODE = "country";
        private static final String JSON_CITY_POPULATION = "population";
        private static final String JSON_CITY_COORD = "coord";

        private final long cityID;
        private final String cityName;
        private final String countryCode;
        private final long population;

        private final Coord coord;

        City() {
            this.cityID = Long.MIN_VALUE;
            this.cityName = null;
            this.countryCode = null;
            this.population = Long.MIN_VALUE;

            this.coord = new Coord();
        }

        City(JSONObject jsonObj) {
            this.cityID = (jsonObj != null) ? jsonObj.optLong(JSON_CITY_ID, Long.MIN_VALUE) : Long.MIN_VALUE;
            this.cityName = (jsonObj != null) ? jsonObj.optString(JSON_CITY_NAME, null) : null;
            this.countryCode = (jsonObj != null) ? jsonObj.optString(JSON_CITY_COUNTRY_CODE, null) : null;
            this.population = (jsonObj != null) ? jsonObj.optLong(JSON_CITY_POPULATION, Long.MIN_VALUE) : Long.MIN_VALUE;

            JSONObject jsonObjCoord = (jsonObj != null) ? jsonObj.optJSONObject(JSON_CITY_COORD) : null;
            this.coord = (jsonObjCoord != null) ? new Coord(jsonObjCoord) : null;
        }

        public boolean hasCityCode() {
            return this.cityID != Long.MIN_VALUE;
        }

        public boolean hasCityName() {
            return this.cityName != null;
        }

        public boolean hasCountryCode() {
            return this.countryCode != null;
        }

        public boolean hasCityPopulation() {
            return this.population != Long.MIN_VALUE;
        }

        public boolean hasCoordInstance() {
            return coord != null;
        }

        public long getCityCode() {
            return this.cityID;
        }

        public String getCityName() {
            return this.cityName;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public long getCityPopulation() {
            return this.population;
        }

        public Coord getCoordInstance() {
            return this.coord;
        }

        public static class Coord extends AbstractWeather.Coord {
            Coord() {
                super();
            }

            Coord(JSONObject jsonObj) {
                super(jsonObj);
            }
        }
    }

    public abstract static class Forecast extends AbstractWeather {
        Forecast() {
            super();
        }

        Forecast(JSONObject jsonObj) {
            super(jsonObj);
        }
    }
}
