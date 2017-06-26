package steve.yang.tradeit.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import steve.yang.tradeit.R;
import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.adapter.RecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener{

    public static final String TAG = HomeActivity.class.getSimpleName();
    private final int START_POST_ACTIVITY = 101;
    private final int START_PROFILE_ACTIVITY = 102;

    private RecyclerView mRecycleView;
    private Button btnPost;
    private Button btnHome;
    private Button btnMy;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    // TODO: Add left hand and right hand mode to rearrange the position of buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecycleView = (RecyclerView) findViewById(R.id.item_list);
        btnPost = (Button) findViewById(R.id.home_button_post);
        btnHome = (Button) findViewById(R.id.home_button_search);
        btnMy = (Button) findViewById(R.id.home_button_my);

        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged, newState:" + newState);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideButtons();
                } else {
                    displayButtons();
                }
            }
        });
        btnPost.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnMy.setOnClickListener(this);

        mRecycleView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(TradeIt.getSales(), this);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_button_post) {
            startPostActivity();
        } else if (v.getId() == R.id.home_button_my) {
            startProfileActivity();
        } else {
            startSearch();
        }
    }

    private void startPostActivity() {
        Intent intent = new Intent(this, PostActivity.class);
        startActivityForResult(intent, START_POST_ACTIVITY);
        mAdapter.notifyDataSetChanged();
    }

    private void startProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivityForResult(intent, START_PROFILE_ACTIVITY);
    }

    private void startSearch() {

    }

    private void hideButtons() {
        btnMy.setVisibility(View.GONE);
        btnPost.setVisibility(View.GONE);
        btnHome.setVisibility(View.GONE);
    }

    private void displayButtons() {
        btnMy.setVisibility(View.VISIBLE);
        btnPost.setVisibility(View.VISIBLE);
        btnHome.setVisibility(View.VISIBLE);
    }
}
