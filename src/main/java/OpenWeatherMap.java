import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Created by ivan on 03.07.15.
 */

public class OpenWeatherMap {
    static final String URL_API = "http://api.openweathermap.org/data/2.5/";
    private static final String URL_CURRENT = "weather?";
    private static final String URL_HOURLY_FORECAST = "forecast?";
    private static final String URL_DAILY_FORECAST = "forecast/daily?";

    private static final String PARAM_COUNT = "cnt=";
    private static final String PARAM_CITY_NAME = "q=";
    private static final String PARAM_CITY_ID = "id=";
    private static final String PARAM_LATITUDE = "lat=";
    private static final String PARAM_LONGITUDE = "lon=";
    private static final String PARAM_MODE = "mode=";
    private static final String PARAM_UNITS = "units=";
    private static final String PARAM_APPID = "appId=";
    private static final String PARAM_LANG = "lang=";

    private final OWMAddress owmAddress;
    private final OWMResponse owmResponse;
    private final OWMProxy owmProxy;

    public OpenWeatherMap(String apiKey) {
        this(Units.IMPERIAL, Language.ENGLISH, apiKey);
    }

    public OpenWeatherMap(Units units, String apiKey) {
        this(units, Language.ENGLISH, apiKey);
    }

    public OpenWeatherMap(Units units, Language lang, String apiKey) {
        this.owmAddress = new OWMAddress(units, lang, apiKey);
        this.owmProxy = new OWMProxy(null, Integer.MIN_VALUE, null, null);
        this.owmResponse = new OWMResponse(owmAddress, owmProxy);
    }

    public OWMAddress getOwmAddressInstance() {
        return owmAddress;
    }

    public String getApiKey() {
        return owmAddress.getAppId();
    }

    public Units getUnits() {
        return owmAddress.getUnits();
    }

    public String getMode() {
        return owmAddress.getMode();
    }

    public Language getLang() {
        return owmAddress.getLang();
    }

    public void setUnits(Units units) {
        owmAddress.setUnits(units);
    }

    public void setApiKey(String appId) {
        owmAddress.setAppId(appId);
    }

    public void setLang(Language lang) {
        owmAddress.setLang(lang);
    }

    public void setProxy(String ip, int port) {
        owmProxy.setIp(ip);
        owmProxy.setPort(port);
        owmProxy.setUser(null);
        owmProxy.setPass(null);
    }

    public void setProxy(String ip, int port, String user, String pass) {
        owmProxy.setIp(ip);
        owmProxy.setPort(port);
        owmProxy.setUser(user);
        owmProxy.setPass(pass);
    }

    public CurrentWeather currentWeatherByCityName(String cityName)
            throws IOException, JSONException {
        String response = owmResponse.currentWeatherByCityName(cityName);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCityName(String cityName, String countryCode)
            throws IOException, JSONException {
        String response = owmResponse.currentWeatherByCityName(cityName, countryCode);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCityCode(long cityCode)
            throws JSONException {
        String response = owmResponse.currentWeatherByCityCode(cityCode);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherByCoordinates(float latitude, float longitude)
            throws JSONException {
        String response = owmResponse.currentWeatherByCoordinates(latitude, longitude);
        return this.currentWeatherFromRawResponse(response);
    }

    public CurrentWeather currentWeatherFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new CurrentWeather(jsonObj);
    }

    public HourlyForecast hourlyForecastByCityName(String cityName)
            throws IOException, JSONException {
        String response = owmResponse.hourlyForecastByCityName(cityName);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCityName(String cityName, String countryCode)
            throws IOException, JSONException {
        String response = owmResponse.hourlyForecastByCityName(cityName, countryCode);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCityCode(long cityCode)
            throws JSONException {
        String response = owmResponse.hourlyForecastByCityCode(cityCode);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastByCoordinates(float latitude, float longitude)
            throws JSONException {
        String response = owmResponse.hourlyForecastByCoordinates(latitude, longitude);
        return this.hourlyForecastFromRawResponse(response);
    }

    public HourlyForecast hourlyForecastFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new HourlyForecast(jsonObj);
    }

    public DailyForecast dailyForecastByCityName(String cityName, byte count)
            throws IOException, JSONException {
        String response = owmResponse.dailyForecastByCityName(cityName, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCityName(String cityName, String countryCode, byte count)
            throws IOException, JSONException {
        String response = owmResponse.dailyForecastByCityName(cityName, countryCode, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCityCode(long cityCode, byte count)
            throws JSONException {
        String response = owmResponse.dailyForecastByCityCode(cityCode, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastByCoordinates(float latitude, float longitude, byte count)
            throws JSONException {
        String response = owmResponse.dailyForecastByCoordinates(latitude, longitude, count);
        return this.dailyForecastFromRawResponse(response);
    }

    public DailyForecast dailyForecastFromRawResponse(String response)
            throws JSONException {
        JSONObject jsonObj = (response != null) ? new JSONObject(response) : null;
        return new DailyForecast(jsonObj);
    }

    public static enum Units {
        METRIC("metric"),
        IMPERIAL("imperial");

        private final String unit;

        Units(String unit) {
            this.unit = unit;
        }
    }

    public static enum Language {
        ENGLISH("en"),
        RUSSIAN("ru"),
        ITALIAN("it"),
        SPANISH("es"),
        UKRAINIAN("uk"),
        GERMAN("de"),
        PORTUGUESE("pt"),
        ROMANIAN("ro"),
        POLISH("pl"),
        FINNISH("fi"),
        DUTCH("nl"),
        FRENCH("FR"),
        BULGARIAN("bg"),
        SWEDISH("sv"),
        CHINESE_TRADITIONAL("zh_tw"),
        CHINESE_SIMPLIFIED("zh"),
        TURKISH("tr"),
        CROATIAN("hr"),
        CATALAN("ca");

        private final String lang;

        Language(String lang) {
            this.lang = lang;
        }

        public static Language fromString(String text) {
            if (text != null) {
                for (Language l : Language.values()) {
                    if (text.equalsIgnoreCase(l.lang)) {
                        return l;
                    }
                }
            }
            return Language.ENGLISH;
        }
    }

    private static class OWMProxy {
        private String ip;
        private int port;
        private String user;
        private String pass;

        private OWMProxy(String ip, int port, String user, String pass) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.pass = pass;
        }

        public Proxy getProxy() {
            Proxy proxy = null;

            if (ip != null && (! "".equals(ip)) && port != Integer.MIN_VALUE) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            }

            if (user != null && (! "".equals(user)) && pass != null && (! "".equals(pass))) {
                Authenticator.setDefault(getAuthenticatorInstance(user, pass));
            }

            return proxy;
        }

        private Authenticator getAuthenticatorInstance(final String user, final String pass) {
            Authenticator authenticator = new Authenticator() {

                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(user, pass.toCharArray()));
                }
            };

            return authenticator;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
    }

    public static class OWMAddress {
        private static final String MODE = "json";
        private static final String ENCODING = "UTF-8";

        private String mode;
        private Units units;
        private String appId;
        private Language lang;

        private OWMAddress(String appId) {
            this(Units.IMPERIAL, Language.ENGLISH, appId);
        }

        private OWMAddress(Units units, String appId) {
            this(units, Language.ENGLISH, appId);
        }

        private OWMAddress(Units units, Language lang, String appId) {
            this.mode = MODE;
            this.units = units;
            this.lang = lang;
            this.appId = appId;
        }

        private String getAppId() {
            return this.appId;
        }

        private Units getUnits() {
            return this.units;
        }

        private String getMode() {
            return this.mode;
        }

        private Language getLang() {
            return this.lang;
        }

        private void setUnits(Units units) {
            this.units = units;
        }

        private void setAppId(String appId) {
            this.appId = appId;
        }

        private void setLang(Language lang) {
            this.lang = lang;
        }

        public String currentWeatherByCityName(String cityName) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String currentWeatherByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            return currentWeatherByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString());
        }

        public String currentWeatherByCityCode(long cityCode) {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String currentWeatherByCoordinates(float latitude, float longitude) {
            return new StringBuilder()
                    .append(URL_API).append(URL_CURRENT)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String hourlyForecastByCityName(String cityName) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String hourlyForecastByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            return hourlyForecastByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString());
        }

        public String hourlyForecastByCityCode(long cityCode) {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String hourlyForecastByCoordinates(float latitude, float longitude) {
            return new StringBuilder()
                    .append(URL_API).append(URL_HOURLY_FORECAST)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String dailyForecastByCityName(String cityName, byte count) throws UnsupportedEncodingException {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_CITY_NAME).append(URLEncoder.encode(cityName, ENCODING)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String dailyForecastByCityName(String cityName, String countryCode, byte count) throws UnsupportedEncodingException {
            return dailyForecastByCityName(new StringBuilder()
                    .append(cityName).append(",").append(countryCode)
                    .toString(), count);
        }

        public String dailyForecastByCityCode(long cityCode, byte count) {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_CITY_ID).append(Long.toString(cityCode)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }

        public String dailyForecastByCoordinates(float latitude, float longitude, byte count) {
            return new StringBuilder()
                    .append(URL_API).append(URL_DAILY_FORECAST)
                    .append(PARAM_LATITUDE).append(Float.toString(latitude)).append("&")
                    .append(PARAM_LONGITUDE).append(Float.toString(longitude)).append("&")
                    .append(PARAM_COUNT).append(Byte.toString(count)).append("&")
                    .append(PARAM_MODE).append(this.mode).append("&")
                    .append(PARAM_UNITS).append(this.units).append("&")
                    .append(PARAM_LANG).append(this.lang).append("&")
                    .append(PARAM_APPID).append(this.appId)
                    .toString();
        }
    }

    private static class OWMResponse {
        private final OWMAddress owmAddress;
        private final OWMProxy owmProxy;

        public OWMResponse(OWMAddress owmAddress, OWMProxy owmProxy) {
            this.owmAddress = owmAddress;
            this.owmProxy = owmProxy;
        }

        public String currentWeatherByCityName(String cityName) throws UnsupportedEncodingException {
            String address = owmAddress.currentWeatherByCityName(cityName);
            return httpGET(address);
        }

        public String currentWeatherByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            String address = owmAddress.currentWeatherByCityName(cityName, countryCode);
            return httpGET(address);
        }

        public String currentWeatherByCityCode(long cityCode) {
            String address = owmAddress.currentWeatherByCityCode(cityCode);
            return httpGET(address);
        }

        public String currentWeatherByCoordinates(float latitude, float longitude) {
            String address = owmAddress.currentWeatherByCoordinates(latitude, longitude);
            return httpGET(address);
        }

        public String hourlyForecastByCityName(String cityName) throws UnsupportedEncodingException {
            String address = owmAddress.hourlyForecastByCityName(cityName);
            return httpGET(address);
        }

        public String hourlyForecastByCityName(String cityName, String countryCode) throws UnsupportedEncodingException {
            String address = owmAddress.hourlyForecastByCityName(cityName, countryCode);
            return httpGET(address);
        }

        public String hourlyForecastByCityCode(long cityCode) {
            String address = owmAddress.hourlyForecastByCityCode(cityCode);
            return httpGET(address);
        }

        public String hourlyForecastByCoordinates(float latitude, float longitude) {
            String address = owmAddress.hourlyForecastByCoordinates(latitude, longitude);
            return httpGET(address);
        }

        public String dailyForecastByCityName(String cityName, byte count) throws UnsupportedEncodingException {
            String address = owmAddress.dailyForecastByCityName(cityName, count);
            return httpGET(address);
        }

        public String dailyForecastByCityName(String cityName, String countryCode, byte count) throws UnsupportedEncodingException {
            String address = owmAddress.dailyForecastByCityName(cityName, countryCode, count);
            return httpGET(address);
        }

        public String dailyForecastByCityCode(long cityCode, byte count) {
            String address = owmAddress.dailyForecastByCityCode(cityCode, count);
            return httpGET(address);
        }

        public String dailyForecastByCoordinates(float latitude, float longitude, byte count) {
            String address = owmAddress.dailyForecastByCoordinates(latitude, longitude, count);
            return httpGET(address);
        }

        private String httpGET(String requestAddress) {
            URL request;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            String tmpStr;
            String response = null;

            try {
                request = new URL(requestAddress);

                if (owmProxy.getProxy() != null) {
                    connection = (HttpURLConnection) request.openConnection(owmProxy.getProxy());
                } else {
                    connection = (HttpURLConnection) request.openConnection();
                }

                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(false);
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String encoding = connection.getContentEncoding();

                    try {
                        if (encoding != null && "gzip".equalsIgnoreCase(encoding)) {
                            reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                        } else if (encoding != null && "deflate".equalsIgnoreCase(encoding)) {
                            reader = new BufferedReader(new InputStreamReader(new InflaterInputStream(connection.getInputStream(), new Inflater(true))));
                        } else {
                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        }

                        while ((tmpStr = reader.readLine()) != null) {
                            response = tmpStr;
                        }
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                System.err.println("Error: " + e.getMessage());
                            }
                        }
                    }
                } else {
                    try {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        while ((tmpStr = reader.readLine()) != null) {
                            response = tmpStr;
                        }
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                System.err.println("Error: " + e.getMessage());
                            }
                        }
                    }

                    System.err.println("Bad Response: " + response + "\n");
                    return null;
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                response = null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return response;
        }
    }
}