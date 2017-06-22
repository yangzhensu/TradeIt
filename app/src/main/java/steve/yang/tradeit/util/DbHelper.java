package steve.yang.tradeit.util;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;
import steve.yang.tradeit.interfaces.IDbHelper;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class DbHelper implements IDbHelper {

    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DATABASE_URL = "https://trade-your-way.firebaseio.com/";
    public static DatabaseReference mDb;
    private static User currentUser;
    private List<Sale> sales;
    public static DataSnapshot snapshot;
    private static DbHelper dbHelper;

    private DbHelper() {
        mDb = FirebaseDatabase.getInstance().getReference();
    }

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
        }
        return dbHelper;
    }

    private void getSales() {
        DatabaseReference salesRef = mDb.child("users").child(TradeIt.getUid()).child("sales");
        salesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> saleIds = new ArrayList<String>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    saleIds.add(child.getValue(String.class));
                }
                System.out.println();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fetchInfo() {
        DatabaseReference userRef = mDb.child("users").child(TradeIt.getUid());
        DatabaseReference salesRef = userRef.child("sales");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                if (currentUser == null) {
                    GoogleSignInAccount account = TradeIt.getAccount();
                    currentUser = new User(account.getDisplayName());
                    updateUser(currentUser);
                }
                TradeIt.setUser(currentUser);
                Log.d(TAG, "currentUser:" + currentUser.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        salesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> salesIds = TradeIt.getSalesId();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    String saleId = child.getKey();
                    Log.d(TAG, "saleId:" + saleId);
                    salesIds.add(saleId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addSale(Sale sale) {
        DatabaseReference salesRef = mDb.child("sales");
        String salesId = salesRef.push().getKey();
        Log.d(TAG, "salesId: " + salesId);
        sale.setSalesId(salesId);
        sale.setUid(TradeIt.getUid());
//        salesRef.setValue(sale);

        updateSale(sale);
    }

    @Override
    public void removeSale(Sale sale) {

    }

    @Override
    public void updateSale(Sale sale) {
        String salesId = sale.getSalesId();
        sale.setUid(TradeIt.getUid());
//        DatabaseReference users = mDb.child("users");

        Map<String, String> values = sale.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        Log.d(TAG, "updateSale, salesId: " + salesId);
        childUpdates.put("/sales/" + salesId + "/", values);

        mDb.updateChildren(childUpdates);

        updateSalesInUsers(salesId, "sale");
        updateZipCode(sale);
        updateSalesDetail(sale);
    }

    private void updateSalesInUsers(String salesId, String status) {
        Map<String, Object> childUpdates = new HashMap<>();
        String path = "/users/" + TradeIt.getUid() + "/sales/" + salesId;
        Map<String, Object> values = new HashMap<>();
        values.put("status", status);
        childUpdates.put(path, values);
        mDb.updateChildren(childUpdates);
    }

    private void updateSalesDetail(Sale sale) {
        Map<String, Object> childUpdates = new HashMap<>();
        String path = "/salesDetail/" + sale.getSalesId() + "/";
        Map<String, Object> values = new HashMap<>();
        values.put("uid", TradeIt.getUid());
        values.put("zipCode", sale.getZipCode());
        Map<String, Object> urls = new HashMap<>();

    }

    public void getUser(String uid) {
        Log.d(TAG, "uid:" + uid);
        DatabaseReference userRef = mDb.child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "singleValueListener");
                currentUser = dataSnapshot.getValue(User.class);
                getSales();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void addUser(User user) {
    }

    @Override
    public void removeUser(User user) {
    }

    @Override
    public void updateUser(User user) {
        String key = TradeIt.getUid();
        Log.d(TAG, "updateUser: key" + key);
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + key, postValues);

        mDb.updateChildren(childUpdates);
    }

    public void updateZipCode(Sale sale) {
        Map<String, Object> postValues = new HashMap<>();
        postValues.put("timestamp", sale.getTimestamp());
        postValues.put("viewCount", sale.getViewCount());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/zipCodes/" + sale.getZipCode() + "/" + sale.getSalesId() + "/", postValues);
        mDb.updateChildren(childUpdates);
    }

}
