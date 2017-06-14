package steve.yang.tradeit.data;

import java.util.Date;
import java.util.List;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class User {

    private String name;
    private Date dateOfBirth;
    private String emailAddress;
    private String phoneNumber;
    private String zipCode;
    private List<Sale> sales;
    private List<Sale> watchings;

    public User() {

    }

    public User(String name, String emailAddress, String zipCode) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.zipCode = zipCode;
    }

    public User(String name, Date dateOfBirth, String emailAddress, String phoneNumber, String zipCode, List<Sale> sales, List<Sale> watchings) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.sales = sales;
        this.watchings = watchings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public List<Sale> getWatchings() {
        return watchings;
    }

    public void setWatchings(List<Sale> watchings) {
        this.watchings = watchings;
    }
}
