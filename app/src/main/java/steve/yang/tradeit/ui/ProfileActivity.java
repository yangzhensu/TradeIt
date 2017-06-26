package steve.yang.tradeit.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import steve.yang.tradeit.R;
import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.data.User;
import steve.yang.tradeit.util.DbHelper;

public class ProfileActivity extends AppCompatActivity
    implements View.OnClickListener{

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private EditText etUsername;
    private EditText etDateOfBirth;
    private EditText etPhoneNumber;
    private EditText etZipcode;
    private EditText etWhatsup;
    private Button btnBack;
    private Button btnRight;

    private boolean isEditing;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etUsername = (EditText) findViewById(R.id.profile_username_edit);
        etDateOfBirth = (EditText) findViewById(R.id.profile_date_of_birth_edit);
        etPhoneNumber = (EditText) findViewById(R.id.profile_phone_number_edit);
        etZipcode = (EditText) findViewById(R.id.profile_zipcode_edit);
        etWhatsup = (EditText) findViewById(R.id.profile_whatsup_edit);
        btnBack = (Button) findViewById(R.id.profile_button_back);
        btnRight = (Button) findViewById(R.id.profile_button_right);

        btnBack.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        currentUser = TradeIt.getUser();
        etUsername.setText(currentUser.getUserName());
        etDateOfBirth.setText(currentUser.getDateOfBirth());
        etZipcode.setText(currentUser.getZipCode());
        etWhatsup.setText(currentUser.getWhatsup());

        isEditing = false;
        updateUI();
    }

    private void updateUI() {
        if (isEditing) {
            etUsername.setFocusableInTouchMode(true);
            etDateOfBirth.setFocusableInTouchMode(true);
            etPhoneNumber.setFocusableInTouchMode(true);
            etZipcode.setFocusableInTouchMode(true);
            etWhatsup.setFocusableInTouchMode(true);
            btnRight.setText(getResources().getString(R.string.profile_confirm_button_text));
        } else {
            etUsername.setFocusable(false);
            etDateOfBirth.setFocusable(false);
            etPhoneNumber.setFocusable(false);
            etZipcode.setFocusable(false);
            etWhatsup.setFocusable(false);
            btnRight.setText(getResources().getString(R.string.profile_edit_button_text));
        }
    }

    @Override
    public void onClick(View v) {
        if (isEditing) {
            if (v.getId() == R.id.profile_button_back) {
                isEditing = false;
                updateUI();
            } else {
                updateUserProfile();
                finish();
            }
        } else {
            if (v.getId() == R.id.profile_button_back) {
                finish();
            } else {
                isEditing = true;
                updateUI();
            }
        }
    }

    private void updateUserProfile() {
        currentUser.setUserName(etUsername.getText().toString());
        currentUser.setDateOfBirth(etDateOfBirth.getText().toString());
        currentUser.setPhoneNumber(etPhoneNumber.getText().toString());
        currentUser.setZipCode(etZipcode.getText().toString());
        currentUser.setWhatsup(etWhatsup.getText().toString());

        DbHelper helper = DbHelper.getInstance();
        helper.updateUser(currentUser);
    }

}
