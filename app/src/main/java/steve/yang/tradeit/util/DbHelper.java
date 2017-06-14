package steve.yang.tradeit.util;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DbHelper() {
        mDb = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void addSale(Sale sale) {

    }

    @Override
    public void removeSale(Sale sale) {

    }

    @Override
    public void addUser(User user) {
        mDb.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null)
                    Log.e(TAG, "errorMessage:" + databaseError.getMessage());
                Log.e(TAG, "referenceKey:" + databaseReference.getKey());
            }
        });
//        mDb.child("users").child("abcdefg").setValue(user);
    }

    @Override
    public void removeUser(User user) {

    }
}
