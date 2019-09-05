package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

public class PhoneNumberVerificationActivity extends AppCompatActivity {
    private static final String TAG = "PhoneNumberVerification";

    LinearLayout parentLayout;
    WaitDialog waitDialog;
    EditText etMobileNumber;
    String fullname;
    String email,mobile;
    String photo;
    TextView tvUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_number_verification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentLayout = findViewById(R.id.parentLayout);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        Button btnVerifyMobile = findViewById(R.id.btnVerifyMobile);
        tvUserName = findViewById(R.id.tvUserName);

        btnVerifyMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authMobileNumber();
            }
        });

        waitDialog = new WaitDialog(this);


        if (getIntent().getExtras() != null) {
            fullname = getIntent().getExtras().getString("fullname");
            Log.e(TAG, "onCreate:fullname " + fullname);

            email = getIntent().getExtras().getString("email");
            photo = getIntent().getExtras().getString("photo");
            if (!TextUtils.isEmpty(fullname)) {
                tvUserName.setText(fullname);

            }

        }

    }


    /*Mobile Number authentication*/
    private void authMobileNumber() {
        String phoneNumber = (etMobileNumber).getText().toString().trim();

        if (phoneNumber.length() < 10) {
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

                SnackBarUtil.showError(PhoneNumberVerificationActivity.this, parentLayout, "Login Failed,Please Try Again!");

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

                Intent intent = new Intent(PhoneNumberVerificationActivity.this, VerifyOTPActivity.class);
                intent.putExtra("verificationId", verificationId);
                intent.putExtra("fullname", fullname);
                intent.putExtra("mobile", etMobileNumber.getText().toString().trim());
                intent.putExtra("email", email);
                intent.putExtra("photo", photo);

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
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();

                            getUser();


                            // ...
                        } else {
                            waitDialog.dismiss();
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            SnackBarUtil.showError(PhoneNumberVerificationActivity.this, parentLayout, "Login Failed,Please Try Again!");
                            Log.e(TAG, "error", task.getException());
                        }
                    }
                });


    }


    private void getUser() {

        String mobile = etMobileNumber.getText().toString().trim();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User user = documentSnapshot.toObject(User.class);
                saveUserInfo(user);

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
            user=new User();
         mobile = etMobileNumber.getText().toString().trim();
        Log.e(TAG, "saveUserInfo: mobile"+mobile );
        if (!TextUtils.isEmpty(mobile)) {
            user.setMobile(mobile);//getting null

        }
        if (!TextUtils.isEmpty(fullname)) {
            user.setFullname(fullname);

        } else {
            user.setFullname(tvUserName.getText().toString());

        }
        user.setEmail(email);
        user.setPhoto(photo);
        user.setRoleId(Constants.Role.USER);
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e(TAG, "saveUserInfo:  Constants.Uid---"+ Constants.Uid );
        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                        Snackbar snackbar = SnackBarUtil.showSuccess(PhoneNumberVerificationActivity.this, parentLayout, "Login Successful");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);

                                Intent intent = new Intent(PhoneNumberVerificationActivity.this, MainActivity.class);
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
                        SnackBarUtil.showError(PhoneNumberVerificationActivity.this, parentLayout, "Login Failed,Please Try Again!");
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
