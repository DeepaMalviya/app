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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import online.masterji.honchiSolution.domain.UserChatting;
import online.masterji.honchiSolution.util.WaitDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivityMainChat extends AppCompatActivity {
    private static final String TAG = "ChatActivityMain";
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase referenceNew, ref, reference1, reference2, reference3, reference;
    String body, title, icon, userid, MainActivityStr, name, image, UpdateStr, id;
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
        setContentView(R.layout.activity_chat_main);
        Firebase.setAndroidContext(this);
        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbarr);
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();
        Log.e(TAG, "onCreate: ===========" + ts);
        setSupportActionBar(ChatToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);

        userName = (TextView) findViewById(R.id.custom_profile_name);
        userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);
        userImage = (CircleImageView) findViewById(R.id.custom_profile_image);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        getUserDAta();
        messageSenderName = Constants.NAME;

        waitDialog = new WaitDialog(ChatActivityMainChat.this);
        layout = (LinearLayout) findViewById(R.id.layout11);
        sendButton = (ImageView) findViewById(R.id.sendButtonn);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

       /* reference1 = new Firebase("https://android-chat-app-e711d.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://android-chat-app-e711d.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
      */
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        icon = intent.getStringExtra("icon");
        title = intent.getStringExtra("title");
        body = intent.getStringExtra("body");

        Log.e(TAG, "onCreate: body" + body);
        Log.e(TAG, "onCreate: icon" + icon);
        Log.e(TAG, "onCreate: title" + title);

        MainActivityStr = intent.getStringExtra("MainActivity");
        name = intent.getStringExtra("list");
        image = intent.getStringExtra("imagesstrings");
        id = intent.getStringExtra("id");
        UpdateStr = intent.getStringExtra("Update");
       /* if (UpdateStr.equals("Update")) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("MessageList").document(messageReceiverName).update("token", "")
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
        }*/

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

        if (!TextUtils.isEmpty(messageReceiverName)) {
            userName.setText(messageReceiverName);

        }
        if (!TextUtils.isEmpty(messageReceiverImage)) {
            Picasso.get().load(messageReceiverImage).placeholder(R.drawable.user_image).into(userImage);

        }


        Log.e(TAG, "onCreate: Constants.NAME=== " + Constants.NAME);
        Log.e(TAG, "onCreate: messageReceiverName=== " + messageReceiverName);
        //  messageReceiverName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Log.e(TAG, "onCreate: messageSenderName=== " + messageSenderName);
        Log.e(TAG, "onCreate: messageReceiverID=== " + messageReceiverID);
        Log.e(TAG, "onCreate: messageReceiverImage=== " + messageReceiverID);

        //messageReceiverImage = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
        referenceNew = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + messageReceiverName);
        //  reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID /*+ "_" + messageReceiverID*/);
        reference3 = new Firebase("https://project-masterji.firebaseio.com/user") /*+ messageSenderName + "_" + messageReceiverName)*/;
        reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverID + "_" + messageSenderID);
        Log.e(TAG, "onCreate: reference2" + reference2);
        Log.e(TAG, "onCreate: reference1" + reference1);
        Log.e(TAG, "onCreate: reference3" + reference3);

        getChattingData();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;

                String messageText = messageArea.getText().toString();
                Log.e(TAG, "onClick:Timestamp " + String.valueOf(Timestamp.now()));

                if (!messageText.equals("")) {
                    sendDataToFirebaseMessagelList(messageText);
                    sendDataToFirebase(messageText);
                    setChattingData(messageText);
                    if (notify) {
                        Log.e(TAG, "onClick:notify " + notify);
                        sendNotifiaction(messageReceiverName, messageSenderName, messageText);
                    }
                    notify = false;

                    AddNumberToList(name);

                    // String key = mDatabase.child("messages").push().getKey();
                   /* Date currentTime = Calendar.getInstance().getTime();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("message", Arrays.asList(messageText));
                    map.put("SenderName", messageSenderName);
                    map.put("SenderID", messageSenderID);
                    map.put("ReceiverID", messageReceiverID);
                    map.put("ReceiverImage", messageReceiverImage);
                    map.put("ReceiverName", messageReceiverName);
                    map.put("timestamp", String.valueOf(FieldValue.serverTimestamp()));

                    reference1.push().setValue(map);
                    reference1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Map map = dataSnapshot.getValue(Map.class);
                            String message = null;
                            String userName = null;
                            try {
                                message = map.get("message").toString();
                                userName = map.get("SenderName").toString();
                                String SenderID = map.get("SenderID").toString();
                                String ReceiverID = map.get("ReceiverID").toString();
                                String ReceiverImage = map.get("ReceiverImage").toString();
                                String ReceiverName = map.get("ReceiverName").toString();
                                String timestamp = map.get("timestamp").toString();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (userName.equals(messageSenderName)) {
                                    addMessageBox(messageSenderName + " :-\n" + message, 1);
                                } else {
                                    addMessageBox(messageReceiverName + " :-\n" + message, 2);
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
*/                    //  reference2.push().setValue(map);
                    Log.e(TAG, "onClick: after set dtaa");
                    messageArea.setText("");

                    uniqueNumbers.clear();
                }
                messageArea.setText("");


            }
        });


    }

    private void sendDataToFirebaseMessagelList(String messageText) {
        ref = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + messageReceiverName);
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
        ref.push().setValue(map1);
    }

    List<String> stringList = new ArrayList<>();

    private void setChattingData(String messageText) {
        Log.e(TAG, "setChattingData: ");
        Log.e(TAG, "setChattingData: messageSenderName==" + messageSenderName);
        Log.e(TAG, "setChattingData: messageReceiverName==" + messageReceiverName);

       // reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
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

       // reference1.push().setValue(map);
        reference2.push().setValue(map);

        reference2.addChildEventListener(new ChildEventListener() {
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

                String myString = null;

                if (!TextUtils.isEmpty(UpdateStr)) {
                    if (UpdateStr.equals("Update")) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        db.collection("MessageList").document(messageReceiverName).update("token", "")
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
                    }
                    return; // or break, continue, throw
                }


                try {
                    messageValue = map.get("message").toString();
                    stringList.add(messageValue);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("MessageList").document(messageReceiverName).update("count", stringList.size());

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
                    String title2 = "<b>" + "You" + "</b>";

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

    private void sendNotifiaction(final String messageReceiverName, final String messageSenderName, final String msg) {
        Log.e(TAG, "sendNotifiaction: ");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //  Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
        try {
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.e(TAG, "sendNotifiaction: messageReceiverID" + messageReceiverID);
            DocumentReference docRef = db.collection("MJ_Users").document(messageReceiverID);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    Log.e(TAG, "sendNotifiaction onSuccess: ");

                    try {

                        if (!TextUtils.isEmpty(user.getToken())) {
                            Constants.TOKEN_new = user.getToken();
                            Log.e(TAG, "sendNotifiaction onSuccess:getToken " + user.getToken());
                            //messageSenderName = Constants.NAME;
                            //  messageSenderImage = Constants.PHOTO;
                            Daata data = new Daata(user.getUid(), R.drawable.ic_stat_name_logo, messageSenderName + ": " + msg, "New Message",
                                    messageReceiverID);
                            Log.e(TAG, "sendNotifiaction onSuccess: after Daata"+user.getUid()+ "== " + R.drawable.ic_stat_name_logo+ "=== " +messageSenderName + ": " + msg+ "New Message"+ "=== " + messageReceiverID);
                            Sender sender = new Sender(data, user.getToken());
                            Log.e(TAG, "sendNotifiaction onSuccess: afre sender");
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
                                                    Toast.makeText(ChatActivityMainChat.this, "Failed!", Toast.LENGTH_SHORT).show();
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


    private void getChattingData() {
        Log.e(TAG, "getChattingData: ");
        Log.e(TAG, "getChattingData: messageSenderName==" + messageSenderName);
        Log.e(TAG, "getChattingData: messageReceiverName==" + messageReceiverName);
        Log.e(TAG, "getChattingData: messageSenderID==" + messageSenderID);
        Log.e(TAG, "getChattingData: messageReceiverID===" + messageReceiverID);
        //reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        //reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + "Masterji");
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverName + "_" + messageSenderName);
        //reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderName + "_" + messageReceiverName);

        Log.e(TAG, "getChattingData: reference1" + reference1);
        // Log.e(TAG, "getChattingData: reference1" + reference2);
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
                    String title2 = "<b>" + "You" + "</b>";

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
       /* ref = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + name);
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
        */
        Log.e(TAG, "sendDataToFirebase: ");
        Log.e(TAG, "sendDataToFirebase:messageSenderName ==" + messageSenderName);
        Log.e(TAG, "sendDataToFirebase:messageReceiverName ==" + messageReceiverName);
        Log.e(TAG, "sendDataToFirebase: " + id);
        Log.e(TAG, "sendDataToFirebase:messageSenderImage= " + messageSenderImage);

        // messageList.setSender(messageSenderName);
        //  messageList.setReceiver(messageReceiverName);
        // messageList.setSenderId(messageSenderID);
        // messageList.setReceiverId(messageReceiverID);
        messageList.setFullname(messageReceiverName);
        messageList.setPhoto(messageReceiverImage);
        messageList.setUid(messageReceiverID);
        // messageList.setCount(msg.size());
        messageList.setCount(stringList.size() + 1);
        if (stringList.size() < stringList.size() + 1) {
            messageList.setToken("Update");

        } else {
            messageList.setToken("");

        }
        //  messageList.setMsg(messageText);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String getId = db.collection("MessageList").document().getId();

        db.collection("MessageList").document(messageReceiverName).set(messageList)
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

    private void updateDatabase(String fullname, String photo, String id) {
        UserChatting user = new UserChatting(fullname, photo, id);

        //  String key = reference.push().getKey();
        //reference = new Firebase("https://project-masterji.firebaseio.com/UserList/" + messageSenderID);
       /* reference = new Firebase("https://project-masterji.firebaseio.com/UserList");
        Log.e(TAG, "updateDatabase:fullname+photo+id " + fullname + photo + id);
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e(TAG, "getUserDAtaID: " + Constants.Uid);


        Map<String, String> map = new HashMap<String, String>();
        map.put("fullname", fullname);
        map.put("photo", photo);
        map.put("id", id);
        // reference.child(key).push().setValue(map);
        reference.push().setValue(map);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String fullname = null;
                try {
                    fullname = map.get("fullname").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String photo = null;
                try {
                    photo = map.get("photo").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String id = map.get("id").toString();

                Log.e(TAG, "onChildAdded:fullname  " + fullname);
                Log.e(TAG, "onChildAdded:photo  " + photo);
                Log.e(TAG, "onChildAdded:id  " + id);
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
        });*/

     /*  Map messageTextBody = new HashMap();
        messageTextBody.put("fullname", fullname);
        messageTextBody.put("photo", photo);
        messageTextBody.put("id", id);
        messageTextBody.put("time", saveCurrentTime);
        messageTextBody.put("date", saveCurrentDate);

        reference.updateChildren(messageTextBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }

    private void getDatata() {
        Log.e(TAG, "getDatata: ");
        // reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);

        referenceNew.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
               /* String userName = map.get("user").toString();

                if (userName.equals(messageReceiverID)) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(messageSenderID + ":-\n" + message, 2);
                }*/
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

    public void addMessageBox(String message, int type) {
        Log.e(TAG, "addMessageBox: ");
        Log.e(TAG, "addMessageBox:message " + message);
        TextView textView = new TextView(ChatActivityMainChat.this);
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
                            Log.e(TAG, "onSuccess: getPhoto" + Constants.PHOTO);
                          /*  Glide.with(getContext())
                                    .load(user.getPhoto())
                                    .centerInside()
                                    .placeholder(R.drawable.ic_menu_help)
                                    .into(ivPofileImage);*/
                            // messageSenderName = Constants.NAME;
                            messageSenderImage = Constants.PHOTO;

                        }
                        if (!TextUtils.isEmpty(user.getFullname())) {
                            Constants.NAME = user.getFullname();
                            Log.e(TAG, "onSuccess:getFullname " + Constants.NAME);
                            messageSenderName = Constants.NAME;
                            //  messageSenderImage = Constants.PHOTO;

                        }
                        if (!TextUtils.isEmpty(user.getToken())) {
                            Constants.TOKEN_new = user.getToken();
                            Log.e(TAG, "onSuccess:getToken " + user.getToken());
                            //messageSenderName = Constants.NAME;
                            //  messageSenderImage = Constants.PHOTO;

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}