package steve.yang.tradeit.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhensuy
 * @date 6/29/17
 * @description
 */

public class SaleSeller {
    private Sale mSale;
    private User mSeller;
    private int mPosition;

    public SaleSeller(Sale sale, User seller, int position) {
        mSale = sale;
        mSeller = seller;
        mPosition = position;
    }

    public Sale getSale() {
        return mSale;
    }

    public User getSeller() {
        return mSeller;
    }

    public int getPosition() {
        return mPosition;
    }

//    public void setPosition(int position) {
//        mPosition = position;
//    }
}
