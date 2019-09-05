package online.masterji.honchiSolution.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.Notification.APIService;
import online.masterji.honchiSolution.Notification.Client;
import online.masterji.honchiSolution.Notification.Daata;
import online.masterji.honchiSolution.Notification.MyResponse;
import online.masterji.honchiSolution.Notification.Sender;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.MessageList;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.WaitDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendActivity1 extends AppCompatActivity {
    private static final String TAG = "SendActivity";
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase referenceNew, ref, reference1, reference2, reference3, reference;
    String MainActivityStr, name, image, nameStr, idid, body, title, icon, id;
    private String messageReceiverID, messageSenderImage, messageSenderName, messageReceiverName, messageReceiverImage, messageSenderID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore RootRef;
    WaitDialog waitDialog;
    private DatabaseReference mDatabase;

    private TextView userName, userLastSeen;
    private CircleImageView userImage;
    private Toolbar ChatToolBar;
    Long tsLong;
    String ts;
    List<String> uniqueNumbers = new ArrayList<>();
    List<String> msg = new ArrayList<>();
    APIService apiService;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbarrSend);
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        setSupportActionBar(ChatToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);

        userName = (TextView) findViewById(R.id.custom_profile_name);
        userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);
        userImage = (CircleImageView) findViewById(R.id.custom_profile_image);

        getUserDAta();
        waitDialog = new WaitDialog(SendActivity1.this);
        layout = (LinearLayout) findViewById(R.id.layout11Send);
        sendButton = (ImageView) findViewById(R.id.sendButtonn);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollViewSend);
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        icon = intent.getStringExtra("icon");
        title = intent.getStringExtra("title");
        body = intent.getStringExtra("body");
        idid = intent.getStringExtra("messageSenderID");
        Log.e(TAG, "onCreate:idid======== " + idid);
        if (!TextUtils.isEmpty(body)) {
            // Log.e(TAG, "onCreate: body" + body.substring(1,20));
            Log.e(TAG, "onCreate: body" + body.length());
            Log.e(TAG, "onCreate: body" + body);
            String[] items = body.split(":");
            Log.e(TAG, "onCreate: items" + items.length);
            for (int i = 0; i < items.length; i++) {

                nameStr = items[0];
                Log.e(TAG, "onCreate: item = " + items[i]);
                Log.e(TAG, "onCreate: nameStr = " + nameStr);

            }
            for (String item : items) {
                Log.e(TAG, "onCreate: item = " + item);
                // name = item;

                System.out.println("item = " + item);
            }
        }
        Log.e(TAG, "onCreate: icon" + icon);
        Log.e(TAG, "onCreate: title" + title);

        MainActivityStr = intent.getStringExtra("MainActivity");
        name = intent.getStringExtra("list");
        image = intent.getStringExtra("imagesstrings");
        id = intent.getStringExtra("id");
        Log.e(TAG, "onCreate: name " + name);
        Log.e(TAG, "onCreate: image " + image);
        Log.e(TAG, "onCreate: id " + id);
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseFirestore.getInstance();
        // updateDatabase(name, image, id);
        /*  messageReceiverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
         */
        messageReceiverID = id;
        messageReceiverName = name;
        messageReceiverImage = image;
        if (!TextUtils.isEmpty(MainActivityStr)) {
            if (MainActivityStr.equals("MainActivity")) {
                userName.setText("Masterji");
                userImage.setImageDrawable(getResources().getDrawable(R.drawable.user_image));
                // Picasso.get().load(messageReceiverImage).placeholder(R.drawable.user_image).into(userImage);
            }
        }
        userName.setText("Masterji");


        Log.e(TAG, "onCreate: Constants.NAME=== " + Constants.NAME);
        Log.e(TAG, "onCreate: messageReceiverName=== " + messageReceiverName);
        //  messageReceiverName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        messageSenderName = Constants.NAME;
        messageSenderImage = Constants.PHOTO;
        Log.e(TAG, "onCreate: messageSenderName=== " + messageSenderName);
        Log.e(TAG, "onCreate: messageReceiverID=== " + messageReceiverID);
        Log.e(TAG, "onCreate: messageReceiverImage=== " + messageReceiverID);

        //messageReceiverImage = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
        //referenceNew = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + messageReceiverName);
        //  reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID /*+ "_" + messageReceiverID*/);
        //  reference3 = new Firebase("https://project-masterji.firebaseio.com/user") /*+ messageSenderName + "_" + messageReceiverName)*/;
        reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverID + "_" + messageSenderID);
        Log.e(TAG, "onCreate: reference2" + reference2);
        //Log.e(TAG, "onCreate: reference1" + reference1);
        Log.e(TAG, "onCreate: reference3" + reference3);

        getChattingData();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                Log.e(TAG, "onClick:Timestamp " + String.valueOf(Timestamp.now()));
                notify = true;

                if (!messageText.equals("")) {
                    sendDataToFirebase(messageText);
                    setChattingData(messageText);
                    if (notify) {
                        Log.e(TAG, "onClick: notify" + notify);
                        sendNotifiaction(messageReceiverName, messageSenderName, messageText);
                    }
                    notify = false;

                    AddNumberToList(name);

                    Log.e(TAG, "onClick: after set dtaa");
                    messageArea.setText("");

                    uniqueNumbers.clear();
                }
                messageArea.setText("");


            }
        });

    }

    List<String> stringList = new ArrayList<>();
    User user;

    private void getUserDAta() {
        Log.e(TAG, "getUserDAta: ");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //  Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
        try {
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);


                    try {
                        if (!TextUtils.isEmpty(user.getPhoto())) {
                            //Photo = user.getPhoto();
                            Constants.PHOTO = user.getPhoto();
                            messageSenderImage = user.getPhoto();
                            Log.e(TAG, "onSuccess: " + Constants.PHOTO);
                          /*  Glide.with(getContext())
                                    .load(user.getPhoto())
                                    .centerInside()
                                    .placeholder(R.drawable.ic_menu_help)
                                    .into(ivPofileImage);*/
                        }
                        if (!TextUtils.isEmpty(user.getFullname())) {
                            Constants.NAME = user.getFullname();
                            Log.e(TAG, "onSuccess: " + Constants.NAME);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setChattingData(String messageText) {
        Log.e(TAG, "setChattingData: ");
        Log.e(TAG, "setChattingData: messageSenderName==" + messageSenderName);
        Log.e(TAG, "setChattingData: messageReceiverName==" + messageReceiverName);
        // TODO: 30/8/19  
       /* if (!TextUtils.isEmpty(body)) {
            reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + nameStr + "_" + messageSenderName);

        } else {
            // TODO: 30/8/19   reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
            reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + "Masterji" + "_" + messageSenderName);

        }*/
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + "Masterji" + "_" + messageSenderName);

      /*  reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
        reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + "Masterji" + "_" + messageReceiverName);
      */
        reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + messageReceiverName);
        // reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + "Masterji");

        // reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        Log.e(TAG, "setChattingData:reference1 " + reference2);
        Date currentTime = Calendar.getInstance().getTime();

        Log.e(TAG, "setChattingData: ");
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", messageText);
        map.put("SenderName", messageSenderName);
        map.put("SenderID", messageSenderID);
        map.put("ReceiverID", messageReceiverID);
        map.put("ReceiverImage", messageReceiverImage);
        map.put("ReceiverName", messageReceiverName);
        map.put("timestamp", String.valueOf(currentTime));

        reference1.push().setValue(map);
        reference2.push().setValue(map);
        //  reference2.push().setValue(map);
        Log.e(TAG, "setChattingData: after" + reference2);
        // getChattingData();
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildAdded:======s====== " + s);
                Map map = dataSnapshot.getValue(Map.class);
                Log.e(TAG, "onChildAdded: map" + map);
                String messageValue = null;
                String userNameValue = null;
                String SenderIDValue = null;
                String ReceiverIDValue = null;
                String ReceiverImageValue = null;
                String ReceiverNameValue = null;
                String timestampValue = null;


                try {
                    messageValue = map.get("message").toString();
                    stringList.add(messageValue);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("MessageList").document(messageSenderName).update("count", stringList.size());

                    userNameValue = map.get("user").toString();
                    SenderIDValue = map.get("SenderID").toString();
                    ReceiverIDValue = map.get("ReceiverID").toString();
                    ReceiverImageValue = map.get("ReceiverImage").toString();
                    ReceiverNameValue = map.get("ReceiverName").toString();
                    timestampValue = map.get("timestamp").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    String title = "<b>" + "Masterji" + "</b>";
                    String title2 = "<b>" + messageSenderName + "</b>";

                    if (userNameValue.equals(messageSenderName)) {

                        addMessageBox(Html.fromHtml(title2) + " \n" + messageValue, 2);
                    } else {
                        addMessageBox(Html.fromHtml(title) + " \n" + messageValue, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    String idStr, nameStrr;

    /*  private void sendNotifiaction(final String messageReceiverName, final String messageSenderName, final String msg) {
          Log.e(TAG, "sendNotifiaction: " + messageReceiverName + "---" + messageSenderName + "---" + msg);
          FirebaseFirestore db = FirebaseFirestore.getInstance();
          //  Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
          try {
              Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
              Log.e(TAG, "sendNotifiaction:messageReceiverID " + messageReceiverID);

              DocumentReference docRef = db.collection("MJ_Users").document(messageReceiverID);
              docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                      user = documentSnapshot.toObject(User.class);


                      try {
                          if (!TextUtils.isEmpty(user.getToken())) {
                              Constants.TOKEN_new = user.getToken();
                              Log.e(TAG, "sendNotifiaction onSuccess:getToken " + user.getToken());
                              //messageSenderName = Constants.NAME;
                              //  messageSenderImage = Constants.PHOTO;
                              if (FirebaseAuth.getInstance().getUid().equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
                                  idStr = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
                                  updateiud(idStr);
                              } else if (FirebaseAuth.getInstance().getUid().equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
                                  nameStrr = "diksha gupta";
                                  // nameStr = "Masterji";

                                  idStr = "MWklcsqWViN1m2vIwQcyqcOWo893";
                                  updateiud(idStr);

                              } else if (FirebaseAuth.getInstance().getUid().equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
                                  nameStrr = "shikha sharma";
                                  // nameStr = "Masterji";

                                  idStr = "WIQFi2OFViWMeFE5pI4RKEaTrM82";
                                  updateiud(idStr);
                              } else if (FirebaseAuth.getInstance().getUid().equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
                                  nameStrr = "utkarsh kumar";
                                  //nameStr = "Masterji";

                                  idStr = "N5KmELyGB8QrlWdgjMOUiofK3m13";
                                  updateiud(idStr);
                              } else if (FirebaseAuth.getInstance().getUid().equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
                                  //nameStr = "Masterji";

                                  nameStrr = "nirupama Lowanshi";
                                  idStr = "TwNBTwPMeaQgUf2LtaxX6B1Uw7n1";
                                  updateiud(idStr);
                              } else {
                                  nameStrr = "deeps malvi";

                                  idStr = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
                                  updateiud(idStr);

                              }


                          }

                      } catch (Exception e) {
                          e.printStackTrace();
                      }

                  }
              });
          } catch (Exception e) {
              e.printStackTrace();
          }

      }
  */
    private void sendNotifiaction(final String messageReceiverName, final String messageSenderName, final String msg) {
        Log.e(TAG, "sendNotifiaction: ");
        Log.e(TAG, "sendNotifiaction: messageReceiverName"+messageReceiverName);
        Log.e(TAG, "sendNotifiaction: messageSenderName"+messageSenderName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //  Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
        try {
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.e(TAG, "sendNotifiaction: messageReceiverID========" + messageReceiverID);
            Log.e(TAG, "sendNotifiaction: messageSenderID==========" + messageSenderID);
            DocumentReference docRef = db.collection("MJ_Users").document("MWklcsqWViN1m2vIwQcyqcOWo893");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    Log.e(TAG, "sendNotifiaction onSuccess: ");

                    try {

                        if (!TextUtils.isEmpty(user.getToken())) {
                            Constants.TOKEN_new = user.getToken();
                            Log.e(TAG, "sendNotifiaction onSuccess:getToken===== " + user.getToken());
                            //messageSenderName = Constants.NAME;
                            //  messageSenderImage = Constants.PHOTO;
                            Daata data = new Daata(user.getUid(), R.drawable.ic_stat_name_logo, messageSenderName + ": " + msg, "New Message",
                                   "MWklcsqWViN1m2vIwQcyqcOWo893", messageReceiverID);
                           /* Daata data = new Daata(user.getUid(), R.drawable.ic_stat_name_logo, messageSenderName + ": " + msg, "New Message",
                                    messageReceiverID, messageReceiverID);
                           */ Log.e(TAG, "sendNotifiaction onSuccess: after Daata========" + user.getUid()+ "== " + R.drawable.ic_stat_name_logo + "=== " + messageSenderName + ": " + msg + "New Message" + "=== " + "5KktiHZb7aSbaFqZJUiSF8fhzYn2");
                            Sender sender = new Sender(data, user.getToken());
                            Log.e(TAG, "sendNotifiaction onSuccess: afre sender");
                            apiService.sendNotification(sender)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            Log.e(TAG, "sendNotifiaction onResponse: response" + response);
                                            if (response.code() == 200) {
                                                Log.e(TAG, "sendNotifiaction onResponse:Failed " + response.body().success);
                                                Log.e(TAG, "sendNotifiaction onResponse:Failed " + response.body());

                                                Log.e(TAG, "sendNotifiaction onResponse:response.code() " + response.code());
                                                if (response.body().success != 1) {
                                                    Log.e(TAG, "sendNotifiaction onResponse:Failed " + response.body().success);
                                                    Toast.makeText(SendActivity1.this, "Failed!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e(TAG, "sendNotifiaction onFailure: ");
                                        }
                                    });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateiud(String idStr) {
        Log.e(TAG, "updateiud: idStr" + idStr);
        Log.e(TAG, "updateiud: messageSenderID" + messageSenderID);
        Log.e(TAG, "updateiud: messageReceiverID" + messageReceiverID);
        Log.e(TAG, "updateiud: messageSenderName" + messageSenderName);
        Daata data = new Daata(messageSenderID, R.drawable.ic_stat_name_logo, messageSenderName + ": " + msg, "New Message",
                idStr, messageReceiverID);
        Log.e(TAG, "updateiud=sendNotifiaction============" + messageSenderID + "---" + R.drawable.ic_stat_name_logo + "---" + messageSenderName + ": " + msg + "---" + "New Message" + "---" +
                idStr + "==" + messageReceiverID);
        Sender sender = new Sender(data, user.getToken());
        Log.e(TAG, "updateiud sendNotifiactiononSuccess: afre sender");
        apiService.sendNotification(sender)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        Log.e(TAG, "sendNotifiaction onResponse: response" + response);
                        if (response.code() == 200) {
                            Log.e(TAG, "sendNotifiaction onResponse:Failed " + response.body().success);

                            Log.e(TAG, "sendNotifiaction onResponse:response.code() " + response.code());
                            if (response.body().success != 1) {
                                Log.e(TAG, "sendNotifiaction onResponse:Failed " + response.body().success);
                                Toast.makeText(SendActivity1.this, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.e(TAG, "sendNotifiaction onFailure: ");
                    }
                });
    }


    String messageValue = null;
    String userNameValue = null;
    String SenderIDValue = null;
    String ReceiverIDValue = null;
    String ReceiverImageValue = null;
    String ReceiverNameValue = null;
    String timestampValue = null;

    private void getChattingData() {
        Log.e(TAG, "getChattingData: ");
        Log.e(TAG, "getChattingData: messageSenderName==" + messageSenderName);
        Log.e(TAG, "getChattingData: messageReceiverName==" + messageReceiverName);
        Log.e(TAG, "getChattingData: messageSenderID==" + messageSenderID);
        Log.e(TAG, "getChattingData: messageReceiverID===" + messageReceiverID);
        //reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        //reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + "Masterji");
        // TODO: 30/8/19  
      
       /* if (!TextUtils.isEmpty(body)) {
            reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + nameStr + "_" + messageSenderName);

        } else {
            // TODO: 30/8/19
            // reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
            reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + "Masterji" + "_" + messageSenderName);

        }*/
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + "Masterji" + "_" + messageSenderName);

        /* reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
         */ // reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + messageReceiverName);

        Log.e(TAG, "getChattingData: reference1" + reference1);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String messageValue = null;
                String userNameValue = null;
                String SenderIDValue = null;
                String ReceiverIDValue = null;
                String ReceiverImageValue = null;
                String ReceiverNameValue = null;
                String timestampValue = null;

                try {
                    messageValue = map.get("message").toString();
                    userNameValue = map.get("SenderName").toString();
                    SenderIDValue = map.get("SenderID").toString();
                    ReceiverIDValue = map.get("ReceiverID").toString();
                    ReceiverImageValue = map.get("ReceiverImage").toString();
                    ReceiverNameValue = map.get("ReceiverName").toString();
                    timestampValue = map.get("timestamp").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    Log.e(TAG, "onChildAdded:userName " + userNameValue);
                    Log.e(TAG, "onChildAdded:messageSenderName " + messageSenderName);
                    Log.e(TAG, "onChildAdded:message " + messageValue);
                    // String title = "<b>You</b>";
                    String title = "<b>" + "Masterji" + "</b>";
                    String title2 = "<b>" + messageSenderName + "</b>";

                    if (userNameValue.equals(messageSenderName)) {

                        addMessageBox(Html.fromHtml(title2) + " \n" + messageValue, 2);
                    } else {
                        addMessageBox(Html.fromHtml(title) + " \n" + messageValue, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    MessageList messageList = new MessageList();

    private void sendDataToFirebase(String messageText) {
        // ref = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + messageSenderName);

        //  ref = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + name);
        Map<String, Object> map1 = new HashMap<String, Object>();

        map1.put("Fullname", messageReceiverName);
        map1.put("Photo", messageReceiverImage);
        map1.put("Uid", messageReceiverID);
        map1.put("Count", stringList.size());
        if (stringList.size() < stringList.size() + 1) {
            // messageList.setToken("Update");
            map1.put("Update", "Update");
        } else {
            //messageList.setToken("");
            map1.put("Update", "");
        }
        Log.e(TAG, "sendDataToFirebase: ");
        Log.e(TAG, "sendDataToFirebase: " + id);
        Log.e(TAG, "sendDataToFirebase:messageSenderImage== " + messageSenderImage);
        Log.e(TAG, "sendDataToFirebase:messageSenderName== " + messageSenderName);
        Date d = new Date();
        CharSequence s  = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());
        Log.e(TAG, "sendDataToFirebase: setDate ========="+s );
        messageList.setFullname(messageSenderName);
        messageList.setPhoto(messageSenderImage);
        messageList.setUid(messageSenderID);
        messageList.setCount(stringList.size());
        String currentTime1 = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

        messageList.setDate(Timestamp.now());
        messageList.setTime(currentTime1);
        messageList.setCount(stringList.size() + 1);
        if (stringList.size() < stringList.size() + 1) {
            messageList.setToken("Update");

        } else {
            messageList.setToken("");

        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String getId = db.collection("MessageList").document().getId();
        // ref.push().setValue(messageList);

        db.collection("MessageList").document(messageSenderName).set(messageList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: ");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error writing document", e);
                    }
                });

        // ref.push().setValue(map1);

    }


    private void AddNumberToList(String name) {
        Log.e(TAG, "AddNumberToList: " + uniqueNumbers.size());
        for (int i = 0; i < uniqueNumbers.size(); i++) {
            if (!uniqueNumbers.get(i).equals(name)) {

                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("sender", messageSenderName);
                map1.put("receiver", messageReceiverName);
                map1.put("SenderID", messageSenderID);
                map1.put("ReceiverID", messageReceiverID);
                map1.put("fullname", name);
                map1.put("photo", image);
                map1.put("id", id);

                reference3.push().setValue(map1);

            } else {

                Toast.makeText(this, "Already exist", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void addMessageBox(String message, int type) {
        Log.e(TAG, "addMessageBox: ");
        Log.e(TAG, "addMessageBox:message " + message);
        TextView textView = new TextView(SendActivity1.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
