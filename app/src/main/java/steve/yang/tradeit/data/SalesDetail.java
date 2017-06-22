package steve.yang.tradeit.data;

import java.net.URL;
import java.util.List;

/**
 * Created by zhensuy on 6/13/17.
 */

public class SalesDetail {

    private String details;
    private List<String> imageUrls;
    private String uid;
    private String zipCode;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
