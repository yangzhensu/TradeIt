package steve.yang.tradeit;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class TradeIt extends Application {

    private static User user;
    private static GoogleSignInAccount account;
    private static String uid;
    private static List<Sale> sales;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        TradeIt.user = user;
    }

    public static GoogleSignInAccount getAccount() {
        return account;
    }

    public static void setAccount(GoogleSignInAccount account) {
        TradeIt.account = account;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        TradeIt.uid = uid;
    }

    public static List<Sale> getSales() {
        if (sales == null) {
            sales = new ArrayList<>();
        }
        return sales;
    }

    public static void setSales(List<Sale> sales) {
        TradeIt.sales = sales;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
