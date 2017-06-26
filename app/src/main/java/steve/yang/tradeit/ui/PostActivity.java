package steve.yang.tradeit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

import steve.yang.tradeit.R;
import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.adapter.ImageAdapter;
import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.util.DbHelper;

public class PostActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TAG = PostActivity.class.getSimpleName();
    private final int REQUEST_IMAGE = 100;
    private final int REQUEST_CODE_PICKER = 500;
    private final int MAX_IMAGE_NUMBER = 5;
    private final String IMAGE_VIEW_IDX = "image_view_idx";

    private TextView tvTitle;
    private EditText etTitle;
    private TextView tvTags;
    private EditText etTags;
    private TextView tvPrice;
    private EditText etPrice;
    private TextView tvChoose;
    private TextView tvDetail;
    private EditText etDetail;
    private EditText etZipcode;
    private GridView selectedImages;
    private Button btnBack;
    private Button btnConfirm;

    private boolean removeMode;
    private boolean isConfirming;
    private int uploadedImageCount;

    private LayoutInflater mInflater;
    private ImageAdapter mAdapter;
    private DbHelper dbHelper;

    private Sale newSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        newSale = new Sale();

        tvTitle = (TextView) findViewById(R.id.post_title);
        etTitle = (EditText) findViewById(R.id.post_title_edit);
        tvTags = (TextView) findViewById(R.id.post_Tags);
        etTags = (EditText) findViewById(R.id.post_tags_edit);
        tvPrice = (TextView) findViewById(R.id.post_price);
        etPrice = (EditText) findViewById(R.id.post_price_edit);
        tvChoose = (TextView) findViewById(R.id.post_choose);
        tvDetail = (TextView) findViewById(R.id.post_detail);
        etDetail = (EditText) findViewById(R.id.post_detail_edit);
        etZipcode = (EditText) findViewById(R.id.post_zipcode_edit);
        btnBack = (Button) findViewById(R.id.post_btn_back);
        btnConfirm = (Button) findViewById(R.id.post_btn_confirm);

        isConfirming = false;

        btnBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        dbHelper = DbHelper.getInstance();

        removeMode = false;
        uploadedImageCount = 0;

        String addButtonIconPath = "" + R.drawable.add_button;
        Log.d(TAG, "addButtonIconPath: " + addButtonIconPath);

        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image(R.drawable.add_button, "Add Button", addButtonIconPath));
        mAdapter = new ImageAdapter(this, R.layout.selected_images, images);

        selectedImages = (GridView) findViewById(R.id.selected_images);
        selectedImages.setOnItemClickListener(this);
        selectedImages.setAdapter(mAdapter);

        etZipcode.setText(TradeIt.getUser().getZipCode());

    }

    private void updateUI() {
        if (isConfirming) {
            btnConfirm.setText("Post");
            btnBack.setText("Edit");
            etTitle.setFocusable(false);
            etTags.setFocusable(false);
            etPrice.setFocusable(false);
            etDetail.setFocusable(false);
        } else {
            btnConfirm.setText("Confirm");
            btnBack.setText("Back");
            etTitle.setFocusableInTouchMode(true);
            etTags.setFocusableInTouchMode(true);
            etPrice.setFocusableInTouchMode(true);
            etDetail.setFocusableInTouchMode(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "request code: " + requestCode + ", result code: " + resultCode);
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            mAdapter.addImages(images);
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick, position: " + position);
        if (position == 0) {
            ImagePicker.create(this)
                    .start(REQUEST_CODE_PICKER);
        } else {
            mAdapter.removeImage(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick, is back button: " + (v.getId() == R.id.post_btn_back));

        if (isConfirming && v.getId() == R.id.post_btn_confirm) {
            postItem();
            return;
        }
        isConfirming = v.getId() == R.id.post_btn_confirm;
        updateUI();
    }

    private void postItem() {
        Log.d(TAG, "postItem is executed");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DatabaseReference salesRef = dbHelper.mDb.child("sales");
        String salesId = salesRef.push().getKey();
        newSale.setSalesId(salesId);
        StorageReference imagesRef = storage.getReference().child("images").child(TradeIt.getUid()).child(salesId);
        TradeIt.getSales().add(newSale);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Image image = mAdapter.getItem(i);
            Log.d(TAG, "imagePath: " + image.getPath());
            UploadTask task = imagesRef.child("" + (i - 1) + ".jpg").putFile(Uri.parse("file://" + image.getPath()));
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, "Your post failed, please try again", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    // TODO fix potential problem, the first uploaded image might not be the main image
                    if ("".equals(newSale.getMainImageUrl())) {
                        newSale.setMainImageUrl(downloadUrl.toString());
                    }
                    Log.d(TAG, "download url: " + downloadUrl);
                    if (++uploadedImageCount == mAdapter.getCount() - 1) {
                        Log.d(TAG, "Upload task succeeded.");
                        postToFirebase();
                        finish();
                    }
                }
            });
        }

    }

    private void postToFirebase() {
        Log.d(TAG, "postToFirebase");

        newSale.setUid(TradeIt.getUid());
        newSale.setTitle(etTitle.getText().toString());
        newSale.setPrice(etPrice.getText().toString());
        newSale.setStatus("sale");
        Date currentTime = new Date(); // TODO unify the format of date and time
        newSale.setTimestamp(currentTime.toString());
        newSale.setDetails(etDetail.getText().toString());
        newSale.setTags(etTags.getText().toString());
        newSale.setZipCode(etZipcode.getText().toString());

        dbHelper.addSale(newSale);
    }
}
