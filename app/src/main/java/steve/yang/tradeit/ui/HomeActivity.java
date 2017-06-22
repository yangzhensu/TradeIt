package steve.yang.tradeit.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import steve.yang.tradeit.R;
import steve.yang.tradeit.adapter.RecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecycleView = (RecyclerView) findViewById(R.id.item_list);
        mRecycleView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        String[] mDataSet = {"a", "b", "c"};

        mAdapter = new RecyclerViewAdapter(mDataSet);
        mRecycleView.setAdapter(mAdapter);
    }
}
