package steve.yang.tradeit.data;

import java.util.Date;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class Sale {

    private String title;
    private Date timestamp;
    private int price;
    private int viewCount;
    private SaleDetail detail;

    public enum Status {
        sale, sold
    }

    public Sale(String title, Date timestamp, int price, int viewCount, SaleDetail detail) {
        this.title = title;
        this.timestamp = timestamp;
        this.price = price;
        this.viewCount = viewCount;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public SaleDetail getDetail() {
        return detail;
    }

    public void setDetail(SaleDetail detail) {
        this.detail = detail;
    }
}
