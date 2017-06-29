package steve.yang.tradeit;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static Map<Sale, User> saleUserMap;
    private static int sellersCount;

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

    public static void addSellersCount() {
        sellersCount++;
    }

    public static void reduceSellersCount() {
        sellersCount--;
    }

    public static int getSellersCount() {
        return sellersCount;
    }

    public static Map<Sale, User> getSaleUserMap() {
        if (saleUserMap == null) {
            saleUserMap = new HashMap<>();
        }
        return saleUserMap;
    }

    public static void setSaleUserMap(Map<Sale, User> saleUserMap) {
        TradeIt.saleUserMap = saleUserMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sellersCount = 0;
    }
}
