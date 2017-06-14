package steve.yang.tradeit.data;

import java.net.URL;
import java.util.List;

/**
 * Created by zhensuy on 6/13/17.
 */

public class SaleDetail {

    private String zipCode;
    private String details;
    private List<URL> imageUrls;
    private List<User> watchers;

    public SaleDetail(String zipCode, String details, List<URL> imageUrls, List<User> watchers) {
        this.zipCode = zipCode;
        this.details = details;
        this.imageUrls = imageUrls;
        this.watchers = watchers;
    }
}
