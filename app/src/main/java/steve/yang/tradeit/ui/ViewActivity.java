package steve.yang.tradeit.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import steve.yang.tradeit.R;
import steve.yang.tradeit.adapter.HomeRecyclerViewAdapter;
import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;

public class ViewActivity extends AppCompatActivity {

    public static final String TAG = ViewActivity.class.getSimpleName();

    private Sale mSale;
    private User mSeller;

    private TextView mTitle;
    private TextView mPrice;
    private TextView mZipcode;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Log.d(TAG, "onCreate, position" + getIntent().getIntExtra(HomeRecyclerViewAdapter.SALE_SELLER_POSITION, 0));
        mTitle = (TextView) findViewById(R.id.view_title);
        mPrice = (TextView) findViewById(R.id.view_price);
        mZipcode = (TextView) findViewById(R.id.view_zipcode);
        mDescription = (TextView) findViewById(R.id.view_description);

    }
}
