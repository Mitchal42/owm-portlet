import com.liferay.faces.util.portal.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import org.json.JSONException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ivan on 03.07.15.
 */

@ManagedBean
@ViewScoped
public class WeatherPortlet {
    private String modeOfView;
    private static final String API_KEY = "1ce0cfc5716f1962d7303d5c6131265c";
    private CurrentWeather cw;
    private OpenWeatherMap owm;
    private String cityName;
    private String unit;
    private boolean renderedWind;
    private boolean renderedCloudiness;
    private boolean renderedPressure;
    private boolean renderedHumidity;
    private boolean renderedPrecipitation;
    private boolean renderedSunriseSunset;
    private boolean renderedGeocoord;

    @PostConstruct
    private void init() {
        PortletRequest portletRequest = (PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        PortletPreferences portletPreferences = portletRequest.getPreferences();
        modeOfView = String.valueOf(portletPreferences.getValue("modeOfView", "1"));
        cityName = portletPreferences.getValue("cityName", "Saint-Petersburg");
        unit = portletPreferences.getValue("unit", "metric");
        renderedWind = Boolean.valueOf(portletPreferences.getValue("renderedWind", "true"));
        renderedCloudiness = Boolean.valueOf(portletPreferences.getValue("renderedCloudiness", "true"));
        renderedPressure = Boolean.valueOf(portletPreferences.getValue("renderedPressure", "true"));
        renderedHumidity = Boolean.valueOf(portletPreferences.getValue("renderedHumidity", "true"));
        renderedPrecipitation = Boolean.valueOf(portletPreferences.getValue("renderedPrecipitation", "true"));
        renderedSunriseSunset = Boolean.valueOf(portletPreferences.getValue("renderedSunriseSunset", "true"));
        renderedGeocoord = Boolean.valueOf(portletPreferences.getValue("renderedGeocoord", "true"));
        if(this.isCurrentMode())
        {
            owm = new OpenWeatherMap(unit.equals("metric")? OpenWeatherMap.Units.METRIC: OpenWeatherMap.Units.IMPERIAL, OpenWeatherMap.Language.fromString(this.getUser().getLocale().getLanguage()), API_KEY);
            try {
                cw = owm.currentWeatherByCityName(cityName);
            } catch (IOException e) {
            } catch (JSONException e) {
            }
        }
    }

    public String convertDateTime2CurrentTimeZone(Date inputDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setTimeZone(this.getUser().getTimeZone());
        return sdf.format(inputDate);
    }

    public static String convertDegree2Direction(float degree)
            throws IllegalArgumentException {
        String direction;

        if ((degree < 0.0f) || (degree > 360.0f)) {
            throw new IllegalArgumentException("Degree cannot be less than 0 or more than 360.");
        }

        if (degree <= 11.25f) {
            direction = "N";
        } else if (degree <= 33.75f) {
            direction = "NNE";
        } else if (degree <= 56.25f) {
            direction = "NE";
        } else if (degree <= 78.75f) {
            direction = "ENE";
        } else if (degree <= 101.25f) {
            direction = "E";
        } else if (degree <= 123.75f) {
            direction = "ESE";
        } else if (degree <= 146.25f) {
            direction = "SE";
        } else if (degree <= 168.75f) {
            direction = "SSE";
        } else if (degree <= 191.25f) {
            direction = "S";
        } else if (degree <= 213.75f) {
            direction = "SSW";
        } else if (degree <= 236.25f) {
            direction = "SW";
        } else if (degree <= 258.75f) {
            direction = "WSW";
        } else if (degree <= 281.25f) {
            direction = "W";
        } else if (degree <= 303.75f) {
            direction = "WNW";
        } else if (degree <= 326.25f) {
            direction = "NW";
        } else if (degree <= 348.75f) {
            direction = "NNW";
        } else {
            direction = "N";
        }
        return direction;
    }

    public boolean isCurrentMode() {
        return modeOfView.equals("1");
    }
    public boolean isHourlyForecastMode() {
        return modeOfView.equals("2");
    }
    public boolean isDailyForecastMode() {
        return modeOfView.equals("3");
    }

    public User getUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ThemeDisplay themeDisplay = (ThemeDisplay)facesContext.getExternalContext().getRequestMap().get(WebKeys.THEME_DISPLAY);
        return themeDisplay.getUser();
    }

    public CurrentWeather getCW()
    {
        return cw;
    }

    public String getUnitString()
    {
        return unit.equals("metric")? " °C": " °F";
    }
    public String getDegree()
    {
        return convertDegree2Direction(cw.getWindInstance().getWindDegree());
    }

    public boolean isRenderedWind() {
        return renderedWind;
    }
    public boolean isRenderedCloudiness() {
        return renderedCloudiness;
    }
    public boolean isRenderedPressure() {
        return renderedPressure;
    }
    public boolean isRenderedHumidity() {
        return renderedHumidity;
    }
    public boolean isRenderedRain() {
        return renderedPrecipitation&&cw.hasRainInstance();
    }
    public boolean isRenderedSnow() {
        return renderedPrecipitation&&cw.hasSnowInstance();
    }
    public boolean isRenderedSunriseSunset() {
        return renderedSunriseSunset;
    }
    public boolean isRenderedGeocoord() {
        return renderedGeocoord;
    }
}
