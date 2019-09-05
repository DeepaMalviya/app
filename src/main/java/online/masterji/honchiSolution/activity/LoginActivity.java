package online.masterji.honchiSolution.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.Config;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private static final int GOOGLE_SING_IN_REQUEST = 1;
    RelativeLayout parentLayout;
    AccessToken accessToken;
    EditText etFullName, etMobileNumber;
    Button btnLogin;
    ImageView ivSkipLogin;
    ImageView ivNext;
    TextView ivFacebook, ivGoogleplus;
    String token;
    WaitDialog waitDialog;
    LinearLayout Googleplus, linearFacebook;
    private FirebaseAuth mAuth;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    LoginButton loginButton;
    boolean isLoggedIn;
    AccessTokenTracker accessTokenTracker;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //FirebaseAuth.getInstance().signOut();
        //setContentView(R.layout.activity_log_in);
        setContentView(R.layout.login);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e(TAG, "onSuccess: loginResult 1" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        initView();
        printHashKey(this);
        waitDialog = new WaitDialog(this);
        SharedPreferences prefs = getSharedPreferences(Config.SHARED_PREF, 0);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            token = prefs.getString("regId", "");//"No name defined" is the default value.
            Log.e(TAG, "onCreate: token------------" + token);
        }

        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        Log.e(TAG, "onCreate:isLoggedIn " + isLoggedIn);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void initView() {


     /*   ivSkipLogin = findViewById(R.id.ivSkipLogin);
        ivSkipLogin.setOnClickListener(this);*/
        parentLayout = findViewById(R.id.parentLayoutt);
      /*  etFullName = findViewById(R.id.etFullName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        btnLogin = findViewById(R.id.btnLogin);
      */
        ivGoogleplus = findViewById(R.id.ivGooglepluss);
        ivFacebook = findViewById(R.id.ivFacebookk);
        ivNext = findViewById(R.id.ivNext);
/*
Googleplus = findViewById(R.id.linearGoogle);
        linearFacebook = findViewById(R.id.linearFacebook);
*/


        ivGoogleplus.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        //  btnLogin.setOnClickListener(this);
      /*  Googleplus.setOnClickListener(this);
        linearFacebook.setOnClickListener(this);
        ivSkipLogin.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.btnLogin:
              //  authMobileNumber();
                break;*/
            case R.id.ivGooglepluss:
                authGoogle();
                break;
            case R.id.ivFacebookk:


                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                fbLogin();
                break;
            case R.id.ivNext:

                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;/*   case R.id.linearGoogle:
                authGoogle();
                break;
            case R.id.linearFacebook:


                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                fbLogin();
                break;*/
          /*  case R.id.ivSkipLogin:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;*/
        }

    }

    private void fbLogin() {
        Log.d(TAG, "fbLogin: ");
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess: 3" + loginResult.toString());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                updateUI(null);// ...
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "onSuccess: " + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        updateUI(null);
                        Log.e(TAG, "onCancel: ");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        updateUI(null);
                        Log.e(TAG, "onError: " + exception.toString());
                        // App code
                    }
                });

    }

    /*Mobile Number authentication*/
    private void authMobileNumber() {
        String phoneNumber = etMobileNumber.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        if (fullName.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Valid Full Name");
            return;
        } else if (phoneNumber.length() < 10) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Valid Mobile Number");
            return;
        }

        waitDialog.show();

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                waitDialog.dismiss();
                SnackBarUtil.showError(LoginActivity.this, parentLayout, "Login Failed,Please Try Again!");

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                Log.e(TAG, "error", e);

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
              /*  mVerificationId = verificationId;
                mResendToken = token;*/

                waitDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, VerifyOTPActivity.class);
                intent.putExtra("verificationId", verificationId);
                intent.putExtra("fullname", etFullName.getText().toString().trim());
                intent.putExtra("mobile", etMobileNumber.getText().toString().trim());
                startActivity(intent);

                // ...
            }
        };


        phoneNumber = "+91" + phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
       /* FirebaseAuth.getInstance().getCurrentUser().getUid();
        //user register device token field add in database */


        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            getUser();

                        } else {
                            waitDialog.dismiss();
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            SnackBarUtil.showError(LoginActivity.this, parentLayout, "Login Failed,Please Try Again!");
                            Log.e(TAG, "error", task.getException());
                        }
                    }
                });
    }


    private void getUser() {
        // String mobile = etMobileNumber.getText().toString().trim();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null)
                    saveUserInfo(user);
                else
                    saveUserInfo(new User());

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        saveUserInfo(new User());
                    }
                })
        ;
    }


    private void saveUserInfo(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String mobile = etMobileNumber.getText().toString().trim();
        Constants.userMobile = mobile;
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Constants.TOKEN = FirebaseInstanceId.getInstance().getToken();
        user.setFullname(etFullName.getText().toString().trim());
        user.setMobile(mobile);
        user.setRoleId(Constants.Role.USER);
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));

        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)
/*
  db.collection("MJ_Users").orderBy("mobile","asc").document(mobile)
                .set(user)
*/

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                        Snackbar snackbar = SnackBarUtil.showSuccess(LoginActivity.this, parentLayout, "Login Successful");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(LoginActivity.this, parentLayout, "Login Failed,Please Try Again!");
                    }
                });


    }


    /*Google Authentication*/
    private void authGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SING_IN_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SING_IN_REQUEST) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Intent intent = new Intent(LoginActivity.this, PhoneNumberVerificationActivity.class);
                intent.putExtra("fullname", account.getDisplayName());
                intent.putExtra("email", account.getEmail());
                intent.putExtra("photo", account.getPhotoUrl().toString());
                Log.d(TAG, "PhotoUrl: " + (account.getPhotoUrl().toString()));

                startActivity(intent);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else {

            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Log.d(TAG, "updateUI: user");

        //
        // getUser();
        try {
            if (user.getDisplayName() != null) {
                Log.e(TAG, "onComplete: user" + user.getDisplayName());

            }
            if (user.getUid() != null) {
                Log.e(TAG, "onComplete: user" + user.getUid());

            }
            if (user.getEmail() != null) {
                Log.e(TAG, "onComplete: user" + user.getEmail());

            }
            if (user.getPhoneNumber() != null) {
                Log.e(TAG, "onComplete: user" + user.getPhoneNumber());

            }
            if (user.getProviderId() != null) {
                Log.e(TAG, "onComplete: user" + user.getProviderId());

            }
            if (user.getPhotoUrl() != null) {
                Log.e(TAG, "onComplete: user" + user.getPhotoUrl());

            }
            if (user.getMetadata() != null) {
                Log.e(TAG, "onComplete: user" + user.getMetadata());

            }
            Intent intent = new Intent(LoginActivity.this, PhoneNumberVerificationActivity.class);
            intent.putExtra("fullname", user.getDisplayName());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("photo", user.getPhotoUrl().toString());
            intent.putExtra("PhoneNumber", user.getPhoneNumber());
            Log.d(TAG, "PhotoUrl: " + (user.getPhotoUrl().toString()));

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
