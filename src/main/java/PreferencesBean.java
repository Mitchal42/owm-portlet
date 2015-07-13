import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ivan on 05.07.15.
 */

@ManagedBean
@ViewScoped
public class PreferencesBean {
    private PortletRequest portletRequest;
    private PortletPreferences portletPreferences;
    private String modeOfView;
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
    public void init() {
        portletRequest = (PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        portletPreferences = portletRequest.getPreferences();
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
    }

    public String save() {
        try {
            portletPreferences.setValue("modeOfView", modeOfView);
            portletPreferences.setValue("cityName", cityName);
            portletPreferences.setValue("unit", unit);
            portletPreferences.setValue("renderedWind", String.valueOf(renderedWind));
            portletPreferences.setValue("renderedCloudiness", String.valueOf(renderedCloudiness));
            portletPreferences.setValue("renderedPressure", String.valueOf(renderedPressure));
            portletPreferences.setValue("renderedHumidity", String.valueOf(renderedHumidity));
            portletPreferences.setValue("renderedPrecipitation", String.valueOf(renderedPrecipitation));
            portletPreferences.setValue("renderedSunriseSunset", String.valueOf(renderedSunriseSunset));
            portletPreferences.setValue("renderedGeocoord", String.valueOf(renderedGeocoord));
            portletPreferences.store();
            return "success";
        }
        catch (ValidatorException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ReadOnlyException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public String getModeOfView() {
        return modeOfView;
    }

    public void setModeOfView(String modeOfView) {
        this.modeOfView = modeOfView;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isRenderedWind() {
        return renderedWind;
    }

    public void setRenderedWind(boolean renderedWind) {
        this.renderedWind = renderedWind;
    }

    public boolean isRenderedCloudiness() {
        return renderedCloudiness;
    }

    public void setRenderedCloudiness(boolean renderedCloudiness) {
        this.renderedCloudiness = renderedCloudiness;
    }

    public boolean isRenderedPressure() {
        return renderedPressure;
    }

    public void setRenderedPressure(boolean renderedPressure) {
        this.renderedPressure = renderedPressure;
    }

    public boolean isRenderedHumidity() {
        return renderedHumidity;
    }

    public void setRenderedHumidity(boolean renderedHumidity) {
        this.renderedHumidity = renderedHumidity;
    }

    public boolean isRenderedPrecipitation() {
        return renderedPrecipitation;
    }

    public void setRenderedPrecipitation(boolean renderedPrecipitation) {
        this.renderedPrecipitation = renderedPrecipitation;
    }

    public boolean isRenderedSunriseSunset() {
        return renderedSunriseSunset;
    }

    public void setRenderedSunriseSunset(boolean renderedSunriseSunset) {
        this.renderedSunriseSunset = renderedSunriseSunset;
    }

    public boolean isRenderedGeocoord() {
        return renderedGeocoord;
    }

    public void setRenderedGeocoord(boolean renderedGeocoord) {
        this.renderedGeocoord = renderedGeocoord;
    }
}
