package steve.yang.tradeit.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import steve.yang.tradeit.R;
import steve.yang.tradeit.TradeIt;
import steve.yang.tradeit.data.Sale;
import steve.yang.tradeit.data.User;
import steve.yang.tradeit.util.FirebaseDbHelper;
import steve.yang.tradeit.util.NetworkHelper;

/**
 * @author zhensuy
 * @date 6/13/17
 * @desciption
 */

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private static final String TAG = SignInActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private final int MAX_SALES_DISPLAY_COUNT = 30;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    private SignInButton signInButton;
    private Button signOutButton;
    private Button disconnectButton;
    private TextView statusTextView;
    private FirebaseDbHelper mFirebaseDbHelper;
    private NetworkHelper mNetworkHelper;

    private int mZipCodeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);

        statusTextView = (TextView) findViewById(R.id.status_text_view);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);
        disconnectButton = (Button) findViewById(R.id.disconnect_button);
        disconnectButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDbHelper = FirebaseDbHelper.getInstance();
        mFirebaseDbHelper.setContext(this);
        mFirebaseDbHelper.setOnSaleDataLoadedListener(new FirebaseDbHelper.OnSaleDataLoadedListener() {
            @Override
            public void onSaleDataLoaded(Sale sale) {
                mFirebaseDbHelper.getSeller(sale, sale.getUid());
            }
        });
        mFirebaseDbHelper.setUserDataLoadedListener(new FirebaseDbHelper.OnUserDataLoadedListener() {
            @Override
            public void onUserDataLoaded(User user) {
                mNetworkHelper.getZipCodeByDistance(user.getZipCode());
            }
        });
        mFirebaseDbHelper.setOnSaleIdLoadedListener(new FirebaseDbHelper.OnSaleIdLoadedListener() {
            @Override
            public void onSaleIdLoaded(String sid) {
                mFirebaseDbHelper.getSale(sid);
            }
        });
        mFirebaseDbHelper.setOnSellerDataLoadedListener(new FirebaseDbHelper.OnSellerDataLoadedListener() {
            @Override
            public void onSellerDataLoaded(Sale sale, User seller) {
                TradeIt.reduceSellersCount();
                TradeIt.getSaleUserMap().put(sale, seller);

                if (TradeIt.getSellersCount() <= 0) {
                    startHomeActivity();
                }
            }
        });
        mNetworkHelper = new NetworkHelper(this);
        mNetworkHelper.setOnZipCodeResponseListener(new NetworkHelper.OnZipCodeResponseListener() {
            @Override
            public void onZipCodeResponse(List<String> response) {
                mZipCodeNum = response.size();
                for (String zipCode : response) {
                    mFirebaseDbHelper.fetchSaleIds(zipCode);
                }
            }
        });

        mZipCodeNum = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectoinFailed:" + connectionResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            default:
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            TradeIt.setAccount(account);
            firebaseAuthWithGoogle(account);
            statusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            TradeIt.setUid(currentUser. getUid());

                            if (FirebaseDbHelper.snapshot == null) {
                                Log.w(TAG, "DataSnapshot is null!");
                            }
                            fetchUserData();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void fetchUserData() {
        mFirebaseDbHelper.fetchUserInfo();
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            signInButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
            disconnectButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
            disconnectButton.setVisibility(View.GONE);
        }
    }

}
