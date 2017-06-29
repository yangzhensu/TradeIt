package steve.yang.tradeit.util;

import android.content.Context;
import android.content.Intent;
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
import steve.yang.tradeit.ui.HomeActivity;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class FirebaseDbHelper implements IDbHelper  {

    public interface OnUserDataLoadedListener {
        void onUserDataLoaded(User user);
    }
    public interface OnSaleDataLoadedListener {
        void onSaleDataLoaded(Sale sale);
    }
    public interface OnSaleIdLoadedListener {
        void onSaleIdLoaded(String sid);
    }
    public interface OnSellerDataLoadedListener {
        void onSellerDataLoaded(Sale sale, User seller);
    }

    public static final String TAG = FirebaseDbHelper.class.getSimpleName();
    public static final String DATABASE_URL = "https://trade-your-way.firebaseio.com/";
    public static DatabaseReference mDb;
    private static User currentUser;
    public static DataSnapshot snapshot;
    private static FirebaseDbHelper firebaseDbHelper;
    private OnUserDataLoadedListener mOnUserDataLoadedListener;
    private OnSaleDataLoadedListener mOnSaleDataLoadedListener;
    private OnSaleIdLoadedListener mOnSaleIdLoadedListener;
    private OnSellerDataLoadedListener mOnSellerDataLoadedListener;
    private Context mContext;
    private int count;

    public interface OnSalesLoadedListener {
        public void onSalesLoaded();
    }

    private FirebaseDbHelper() {
        mDb = FirebaseDatabase.getInstance().getReference();
        count = 0;
    }

    public static FirebaseDbHelper getInstance() {
        if (firebaseDbHelper == null) {
            firebaseDbHelper = new FirebaseDbHelper();
        }
        return firebaseDbHelper;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setUserDataLoadedListener(OnUserDataLoadedListener listener) {
        mOnUserDataLoadedListener = listener;
    }

    public void setOnSaleDataLoadedListener(OnSaleDataLoadedListener listener) {
        mOnSaleDataLoadedListener = listener;
    }

    public void setOnSaleIdLoadedListener(OnSaleIdLoadedListener listener) {
        mOnSaleIdLoadedListener = listener;
    }

    public void setOnSellerDataLoadedListener(OnSellerDataLoadedListener listener) {
        mOnSellerDataLoadedListener = listener;
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

    public void getSale(String sid) {
        DatabaseReference saleRef = mDb.child("sales").child(sid);
        saleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Sale sale = dataSnapshot.getValue(Sale.class);
//                TradeIt.getSales().add(sale);
                if (mOnSaleDataLoadedListener != null) {
                    mOnSaleDataLoadedListener.onSaleDataLoaded(sale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fetchUserInfo() {
        DatabaseReference userRef = mDb.child("users").child(TradeIt.getUid());
        final DatabaseReference salesRef = mDb.child("sales");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                if (currentUser == null) {
                    GoogleSignInAccount account = TradeIt.getAccount();
                    currentUser = new User(account.getDisplayName());
                    Log.d(TAG, "fetchUserInfo, user zip code: " + currentUser.getZipCode());
                    updateUser(currentUser);
                }
                TradeIt.setUser(currentUser);
                if (mOnUserDataLoadedListener != null) {
                    mOnUserDataLoadedListener.onUserDataLoaded(currentUser);
                }
//                final Map<String, Object> saleIds = currentUser.getSales();
//                for (String saleId : saleIds.keySet()) {
//                    DatabaseReference saleRef = salesRef.child(saleId);
//
//                    saleRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Sale sale = dataSnapshot.getValue(Sale.class);
//                            TradeIt.getSales().add(sale);
//                            if (++count >= saleIds.keySet().size()) {
//                                startHomeActivity();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                startHomeActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        salesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<String> salesIds = TradeIt.getSales();
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    String saleId = child.getKey();
//                    Log.d(TAG, "saleId:" + saleId);
//                    salesIds.add(saleId);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    
    public void fetchSaleIds(String zipCode) {
        DatabaseReference zipCodeRef = mDb.child("zipCodes").child(zipCode);
//        Log.d(TAG, "fetchSales near zipCode: " + zipCode);
        zipCodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String sid = snapshot.getKey();
                    TradeIt.addSellersCount();
                    Log.d(TAG, "fetchSaleIds, sid: " + sid);

                    if (mOnSaleIdLoadedListener != null) {
                        mOnSaleIdLoadedListener.onSaleIdLoaded(sid);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addSale(Sale sale) {
//        DatabaseReference salesRef = mDb.child("sales");
//        String salesId = salesRef.push().getKey();
//        Log.d(TAG, "salesId: " + salesId);
//        sale.setSalesId(salesId);
//        sale.setUid(TradeIt.getUid());
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
        Map<String, String> urls = new HashMap<>();
        List<String> imageUrls = sale.getImageUrls();
        for (int i = 0; i < imageUrls.size(); i++) {
            urls.put("" + i, imageUrls.get(i));
        }
        values.put("imageUrls", urls);

        childUpdates.put(path, values);
        mDb.updateChildren(childUpdates);
    }

    public void getUser(String uid) {
        Log.d(TAG, "uid:" + uid);
        DatabaseReference userRef = mDb.child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void getSeller(final Sale sale, String uid) {
        Log.d(TAG, "seller uid: " + uid);
        DatabaseReference sellerRef = mDb.child("users").child(uid);
        sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User seller = dataSnapshot.getValue(User.class);
                if (mOnSellerDataLoadedListener != null) {
                    mOnSellerDataLoadedListener.onSellerDataLoaded(sale, seller);
                }
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
