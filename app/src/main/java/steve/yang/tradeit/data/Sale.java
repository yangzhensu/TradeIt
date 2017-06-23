package steve.yang.tradeit.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import steve.yang.tradeit.TradeIt;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class Sale {

    private String mainImageUrl;
    private String price;
    private String status;
    private String timestamp;
    private String title;
    private String uid;
    private String viewCount;
    private String details;
    private String tags;
    private String zipCode;

    private String salesId;

    public Sale() {
        this.mainImageUrl = "";
        this.price = "";
        this.status = "";
        this.timestamp = "";
        this.title = "";
        this.uid = TradeIt.getUid();
        this.viewCount = "0";
        this.details = "";
        this.tags = "";
        this.zipCode = "";
    }

    public Sale(String mainImageUrl, String price, String status, String timestamp, String title, String uid, String viewCount, String details, String tags, String zipCode) {
        this.mainImageUrl = mainImageUrl;
        this.price = price;
        this.status = status;
        this.timestamp = timestamp;
        this.title = title;
        this.uid = uid;
        this.viewCount = viewCount;
        this.details = details;
        this.tags = tags;
        this.zipCode = zipCode;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();

        result.put("mainImageUrl", mainImageUrl);
        result.put("price", price);
        result.put("status", status);
        result.put("timestamp", timestamp);
        result.put("title", title);
        result.put("uid", uid);
        result.put("viewCount", viewCount);
        result.put("details", details);
        result.put("tags", tags);

        return result;
    }
}
