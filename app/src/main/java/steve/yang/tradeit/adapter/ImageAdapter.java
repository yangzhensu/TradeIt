package steve.yang.tradeit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

import steve.yang.tradeit.R;

/**
 * @author zhensuy
 * @date 6/20/17
 * @description
 */

public class ImageAdapter extends ArrayAdapter {

    private final String TAG = ImageAdapter.class.getSimpleName();

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Image> images;

    public ImageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Image> images) {
        super(context, resource, images);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.images = images;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Image item = images.get(position);
        holder.imageTitle.setText(item.getName());
        String path = item.getPath();
        Log.d(TAG, "getView, path: " + path);
        Glide.with(mContext).load(position == 0? Integer.parseInt(path) : path).into(holder.image);
        return row;
    }

    public void addImages(ArrayList<Image> images) {
        for (Image image : images) {
            this.images.add(image);
        }
    }

    public void removeImage(int idx) {
        this.images.remove(idx);
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
