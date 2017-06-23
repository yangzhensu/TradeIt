package steve.yang.tradeit.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import steve.yang.tradeit.R;
import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.data.Sale;

/**
 * @author zhensuy
 * @date 6/17/17
 * @description
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private List<Sale> mDataSet;
    private LayoutInflater mInflater;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static final String TAG = ViewHolder.class.getSimpleName();

        public CardView item;
        public TextView tvTitle;
        public ImageView ivMainImage;
        public ImageView ivSellerIcon;
        public TextView tvSellerName;
        public ImageView ivPriceIcon;
        public TextView tvPrice;
        public TextView tvDescription;
        public ImageView ivTagIcon;
        public TextView tvTag;
        public ImageView ivPostTimeIcon;
        public TextView tvPostTime;
        public ImageView ivViewCountIcon;
        public TextView tvViewCount;
        public ImageView ivItemStatusIcon;
        public TextView tvItemStaus;

        public ViewHolder (View v) {
            super(v);
            item = (CardView) v.findViewById(R.id.item_card);
            tvTitle = (TextView) v.findViewById(R.id.sale_item_title);
            ivMainImage = (ImageView) v.findViewById(R.id.sale_item_photo);

            LinearLayout saleItemTags = (LinearLayout) v.findViewById(R.id.sale_item_tags);
            ivTagIcon = (ImageView) saleItemTags.findViewById(R.id.icon);
            ivTagIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_icon_tags, null));
            tvTag = (TextView) saleItemTags.findViewById(R.id.description);

            LinearLayout saleItemSellerName = (LinearLayout) v.findViewById(R.id.sale_item_seller_name);
            ivSellerIcon = (ImageView) saleItemSellerName.findViewById(R.id.icon);
            ivSellerIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_icon_seller, null));
            tvSellerName = (TextView) saleItemSellerName.findViewById(R.id.description);

            LinearLayout saleItemPrice = (LinearLayout) v.findViewById(R.id.sale_item_price);
            ivPriceIcon = (ImageView) saleItemPrice.findViewById(R.id.icon);
            ivPriceIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_icon_price, null));
            tvPrice = (TextView) saleItemPrice.findViewById(R.id.description);

            tvDescription = (TextView) v.findViewById(R.id.sale_item_detail);

            LinearLayout saleItemPostTime = (LinearLayout) v.findViewById(R.id.sale_item_post_time);
            ivPostTimeIcon = (ImageView) saleItemPostTime.findViewById(R.id.icon);
            ivPostTimeIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_icon_clock, null));
            tvPostTime = (TextView) saleItemPostTime.findViewById(R.id.description);

            LinearLayout saleItemViewCount = (LinearLayout) v.findViewById(R.id.sale_item_view_count);
            ivViewCountIcon = (ImageView) saleItemViewCount.findViewById(R.id.icon);
            ivViewCountIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_icon_view_counts, null));
            tvViewCount = (TextView) saleItemViewCount.findViewById(R.id.description);

            LinearLayout saleItemStatus = (LinearLayout) v.findViewById(R.id.sale_item_item_status);
            ivItemStatusIcon = (ImageView) saleItemStatus.findViewById(R.id.icon);
            ivItemStatusIcon.setImageDrawable(v.getResources().getDrawable(R.drawable.sale_item_status, null));
            tvItemStaus = (TextView) saleItemStatus.findViewById(R.id.description);
        }
    }

    public RecyclerViewAdapter(List<Sale> mDataSet, Context context) {
        this.mDataSet = mDataSet;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        LinearLayout cardView = (LinearLayout) mInflater.inflate(R.layout.sale_card, parent, false);
        ViewHolder vh = new ViewHolder(cardView);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position);
        Sale sale = mDataSet.get(position);

        holder.tvTitle.setText(sale.getTitle());
        holder.tvSellerName.setText(TradeIt.getUser().getUserName());
        holder.tvPrice.setText(sale.getPrice());
        holder.tvDescription.setText(sale.getDetails());
        holder.tvTag.setText(sale.getTags());
        holder.tvPostTime.setText(sale.getTimestamp());
        holder.tvViewCount.setText(sale.getViewCount());
        holder.tvItemStaus.setText(sale.getStatus());
        Glide.with(mContext).load(sale.getMainImageUrl()).into(holder.ivMainImage);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
