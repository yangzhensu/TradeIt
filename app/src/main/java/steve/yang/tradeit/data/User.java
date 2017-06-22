package steve.yang.tradeit.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class User {

    private String userName;
    private String dateOfBirth;
    private String phoneNumber;
    private String zipCode;
    private String photoURL;
    private String whatsup;
    private HashMap<String, Object> sales;
    private List<String> watchings;

    public User() {
        this.userName = "name";
        this.dateOfBirth = "dateOfBirth";
        this.phoneNumber = "phoneNumber";
        this.zipCode = "zipCode";
        this.photoURL = "photoUrl";
        this.whatsup = "whatsup";
        this.sales = new HashMap<>();
        this.watchings = new ArrayList<>();
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String dateOfBirth, String emailAddress, String phoneNumber, String zipCode, String photoURL, String whatsup, HashMap<String, Object> sales, List<String> watchings) {
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.photoURL = photoURL;
        this.whatsup = whatsup;
        this.sales = sales;
        this.watchings = watchings;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public HashMap<String, Object> getSales() {
        return sales;
    }

    public void setSales(HashMap<String, Object> sales) {
        this.sales = sales;
    }

    public List<String> getWatchings() {
        return watchings;
    }

    public void setWatchings(List<String> watchings) {
        this.watchings = watchings;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("dateOfBirth", dateOfBirth);
        result.put("phoneNumber", phoneNumber);
        result.put("zipCode", zipCode);
        result.put("photoURL", photoURL);
        result.put("whatsup", whatsup);

        return result;
    }
}
