package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

public class VerifyOTPActivity extends AppCompatActivity {
    private static final String TAG = "VerifyOTPActivity";

    LinearLayout parentLayout;

    String otpCode;
    String verificationId;
    String fullname;
    String mobile;
    String email;
    String photo;
    WaitDialog waitDialog;

    EditText et1, et2, et3, et4, et5, et6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verifyotp);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null) {
            verificationId = getIntent().getExtras().getString("verificationId");
            fullname = getIntent().getExtras().getString("fullname");
            mobile = getIntent().getExtras().getString("mobile");
            email = getIntent().getExtras().getString("email", "");
            photo = getIntent().getExtras().getString("photo", "");
        }

        waitDialog = new WaitDialog(this);

        initView();
    }

    private void initView() {

        parentLayout = findViewById(R.id.parentLayout);

        Button btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);


        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();
            }
        });


        et1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et1.getText().toString().length() == 1) {
                    et2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et2.getText().toString().length() == 1) {
                    et3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        et3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et3.getText().toString().length() == 1) {
                    et4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et4.getText().toString().length() == 1) {
                    et5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        et5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et5.getText().toString().length() == 1) {
                    et6.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
    }

    private void verifyOTP() {

        String s1 = et1.getText().toString().trim();
        String s2 = et2.getText().toString().trim();
        String s3 = et3.getText().toString().trim();
        String s4 = et4.getText().toString().trim();
        String s5 = et5.getText().toString().trim();
        String s6 = et6.getText().toString().trim();

        if (!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty() && !s4.isEmpty() && !s5.isEmpty() && !s6.isEmpty()) {
            otpCode = s1 + s2 + s3 + s4 + s5 + s6;
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
            signInWithPhoneAuthCredential(credential);
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        waitDialog.show();
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
                            SnackBarUtil.showError(VerifyOTPActivity.this, parentLayout, "Login Failed,Please Try Again!");
                            Log.e(TAG, "error", task.getException());
                        }
                    }
                });
    }


    private void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e(TAG, "getUser: ======="+ Constants.Uid);
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


        user.setFullname(fullname);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPhoto(photo);
        user.setRoleId(Constants.Role.USER);
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));
        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)
 /* db.collection("MJ_Users").document(mobile)
                .set(user)
*/
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                        Snackbar snackbar = SnackBarUtil.showSuccess(VerifyOTPActivity.this, parentLayout, "Login Successful");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
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
                        SnackBarUtil.showError(VerifyOTPActivity.this, parentLayout, "Login Failed,Please Try Again!");
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
