import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by ivan on 03.07.15.
 */
abstract class AbstractResponse implements Serializable {
    private static final String JSON_RESPONSE_CODE = "cod";

    private final int responseCode;
    private final String rawResponse;

    AbstractResponse() {
        this.rawResponse = null;
        this.responseCode = Integer.MIN_VALUE;
    }

    AbstractResponse(JSONObject jsonObj) {
        this.rawResponse = (jsonObj != null) ? jsonObj.toString() : null;
        this.responseCode = (jsonObj != null) ? jsonObj.optInt(JSON_RESPONSE_CODE, Integer.MIN_VALUE) : Integer.MIN_VALUE;
    }

    public boolean isValid() {
        return this.responseCode == 200;
    }

    public boolean hasResponseCode() {
        return this.responseCode != Integer.MIN_VALUE;
    }

    public boolean hasRawResponse() {
        return this.rawResponse != null;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }
}
