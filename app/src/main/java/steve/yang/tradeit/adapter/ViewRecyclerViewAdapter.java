package steve.yang.tradeit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;

/**
 * @author zhensuy
 * @date 6/28/17
 * @description
 */

public class ViewRecyclerViewAdapter extends RecyclerView.Adapter<ViewRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private User mSeller;
    private Sale mSale;

    public ViewRecyclerViewAdapter(Context context, User seller, Sale sale) {
        mContext = context;
        mSeller = seller;
        mSale = sale;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
