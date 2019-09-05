package online.masterji.honchiSolution.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.MessageList;
import online.masterji.honchiSolution.domain.UserChatting;
import online.masterji.honchiSolution.util.WaitDialog;

public class ChatActivityMain extends AppCompatActivity {
    private static final String TAG = "ChatActivityMain";
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase referenceNew, reference1, reference2, reference3, reference;
    String Update, UserAdapterChattingStr, InboxStr, MainActivityStr, name, image, id;
    private String messageReceiverID, messageSenderImage, messageSenderName, messageReceiverName, messageReceiverImage, messageSenderID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore RootRef;
    WaitDialog waitDialog;
    private DatabaseReference mDatabase;

    private TextView userName, userLastSeen;
    private CircleImageView userImage;
    private Toolbar ChatToolBar;
    Long tsLong;
    List<String> uniqueNumbers = new ArrayList<>();
    List<String> msg = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        Firebase.setAndroidContext(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            initializeViewData();
            initView();
            getIntentData();
            getUserDAtaID();

            mAuth = FirebaseAuth.getInstance();
            messageSenderID = mAuth.getCurrentUser().getUid();
            RootRef = FirebaseFirestore.getInstance();
            messageReceiverID = id;
            messageReceiverName = name;
            messageReceiverImage = image;
            Log.e(TAG, "onCreate: Constants.NAME=== " + Constants.NAME);
            Log.e(TAG, "onCreate: messageReceiverName=== " + messageReceiverName);
           messageSenderName = Constants.NAME;
            Log.e(TAG, "onCreate: messageSenderName=== " + messageSenderName);
            Log.e(TAG, "onCreate: messageReceiverID=== " + messageReceiverID);
            Log.e(TAG, "onCreate: messageReceiverImage=== " + messageReceiverID);

            //messageReceiverImage = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
            referenceNew = new Firebase("https://project-masterji.firebaseio.com/messagesList/" + messageReceiverName);
            reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID /*+ "_" + messageReceiverID*/);
            reference3 = new Firebase("https://project-masterji.firebaseio.com/user") /*+ messageSenderName + "_" + messageReceiverName)*/;
            reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverID + "_" + messageSenderID);
            Log.e(TAG, "onCreate: reference2" + reference2);
            Log.e(TAG, "onCreate: reference1" + reference1);
            Log.e(TAG, "onCreate: reference3" + reference3);

            getChattingData();

        /*  reference = new Firebase("https://project-masterji.firebaseio.com/UserList");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("fullname", name);
        map2.put("photo", image);
        map2.put("id", id);
        reference.push().setValue(map2);
      */

            //getDatata();


       /* reference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                Log.e(TAG, "onChildAdded: map" + map);
                Chat chat = new Chat();
                // map.entrySet().add(chat);
                Object[] strings = map.entrySet().toArray();
                Log.e(TAG, "onChildAdded: strings==============" + strings.length);
                for (int i = 0; i < strings.length; i++) {
                    Log.e(TAG, "onChildAdded:===== " + i + "=======" + strings[i]);
                }


                Log.e(TAG, "onChildAdded:chat.getCount() " + chat.getCount());
                chat.getCount();


                String message = null;
                try {
                    message = map.get("message").toString();
                    msg.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String userName = null;
                try {
                    userName = map.get("user").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String SenderID = map.get("SenderID").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String ReceiverID = map.get("ReceiverID").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // String timestamp = map.get("timestamp").toString();


                try {
                    if (userName.equals(messageSenderName)) {
                        Log.e(TAG, "onChildAdded: message" + message);
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
        });*/
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = messageArea.getText().toString();
                    Log.e(TAG, "onClick:Timestamp " + String.valueOf(Timestamp.now()));

                    if (!messageText.equals("")) {
                        sendDataToFirebase(messageText);
                        setChattingData(messageText);

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

        } else {
            startActivity(new Intent(ChatActivityMain.this, LoginActivity.class));
        }


    }

    private void getIntentData() {
        intent = getIntent();
        MainActivityStr = intent.getStringExtra("MainActivity");
        InboxStr = intent.getStringExtra("Inbox");
        UserAdapterChattingStr = intent.getStringExtra("UserAdapterChatting");
        Update = intent.getStringExtra("Update");

        if (MainActivityStr != null && !MainActivityStr.isEmpty()) {


            if (MainActivityStr.equals("MainActivity")) {
                userName.setText("Masterji");
                userImage.setImageDrawable(getResources().getDrawable(R.drawable.masterji_logo));
                // Picasso.get().load(messageReceiverImage).placeholder(R.drawable.user_image).into(userImage);

            }


        }
        if (Update != null && !Update.isEmpty()) {


            if (Update.equals("Update")) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("MessageList").document(messageReceiverName).update("token", "");
            }


        }

        name = intent.getStringExtra("list");
        image = intent.getStringExtra("imagesstrings");
        id = intent.getStringExtra("id");
        Log.e(TAG, "onCreate: name " + name);
        Log.e(TAG, "onCreate: image " + image);
        Log.e(TAG, "onCreate: id " + id);
        if (UserAdapterChattingStr != null && !UserAdapterChattingStr.isEmpty()) {
            if (UserAdapterChattingStr.equals("UserAdapterChatting")) {
                userName.setText(messageReceiverName);
                Picasso.get().load(messageReceiverImage).placeholder(R.drawable.user_image).into(userImage);
            }
        }
        if (InboxStr != null && !InboxStr.isEmpty()) {
            if (InboxStr.equals("Inbox")) {
                userName.setText(messageReceiverName);
                Picasso.get().load(messageReceiverImage).placeholder(R.drawable.user_image).into(userImage);
            }
        }
    }

    private void initializeViewData() {
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

    }

    private void initView() {
        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbarr);
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


        waitDialog = new WaitDialog(ChatActivityMain.this);
        layout = (LinearLayout) findViewById(R.id.layout11);
        sendButton = (ImageView) findViewById(R.id.sendButtonn);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

    }

    private void setChattingData(String messageText) {
        Log.e(TAG, "setChattingData: ");
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        Log.e(TAG, "setChattingData:reference1 " + reference1);
        Date currentTime = Calendar.getInstance().getTime();

      /*  Chat chat = new Chat();
        chat.setMessage("Welcome To Masterji App");
        chat.setSenderName(messageSenderName);
        chat.setSenderID(messageSenderID);
        chat.setReceiverID(messageReceiverID);
        chat.setReceiverImage(messageReceiverImage);
        chat.setReceiverName(messageReceiverName);
        chat.setTimestamp(String.valueOf(currentTime));
        chat.setCount(msg.size());
     */
        Log.e(TAG, "setChattingData: ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", messageText);
        map.put("SenderName", messageSenderName);
        map.put("SenderID", messageSenderID);
        map.put("ReceiverID", messageReceiverID);
        map.put("ReceiverImage", messageReceiverImage);
        map.put("ReceiverName", messageReceiverName);
        map.put("timestamp", (Timestamp) Timestamp.now());
        Log.e(TAG, "setChattingData: Timestamp.now().toString()==============" + (Timestamp) Timestamp.now());
        reference1.push().setValue(map);
        Log.e(TAG, "setChattingData: after");
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(TAG, "onChildAdded:======s====== " + s);
                Map map = dataSnapshot.getValue(Map.class);
                Log.e(TAG, "onChildAdded: map" + map);
               /* Chat chat = new Chat();
                // map.entrySet().add(chat);
                Object[] strings = map.entrySet().toArray();
                Log.e(TAG, "onChildAdded: strings==============" + strings.length);
                for (int i = 0; i < strings.length; i++) {
                    Log.e(TAG, "onChildAdded:===== " + i + "=======" + strings[i]);
                }


                Log.e(TAG, "onChildAdded:chat.getCount() " + chat.getCount());
                chat.getCount();
*/

                String message = null;
                try {
                    message = map.get("message").toString();
                    msg.add(message);
                    Log.e(TAG, "onChildAdded:+msg.size() " + msg.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String userName = null;
                try {
                    userName = map.get("user").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String SenderID = map.get("SenderID").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String ReceiverID = map.get("ReceiverID").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // String timestamp = map.get("timestamp").toString();


                try {
                    if (userName.equals(messageReceiverName)) {
                        Log.e(TAG, "onChildAdded: message" + message);
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
    }

    String messageValue = null;
    String userNameValue = null;
    String SenderIDValue = null;
    String ReceiverIDValue = null;
    String ReceiverImageValue = null;
    String ReceiverNameValue = null;
    String timestampValue = null;
    List<String> stringList = new ArrayList<>();

    private void getChattingData() {
        Log.e(TAG, "getChattingData: ");
        Log.e(TAG, "getChattingData: messageSenderID==" + messageSenderID);
        Log.e(TAG, "getChattingData: messageReceiverID===" + messageReceiverID);
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        stringList.clear();
        Log.e(TAG, "getChattingData: reference1" + reference1);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                try {
                    messageValue = map.get("message").toString();
                    stringList.add(messageValue);
                    Log.e(TAG, "onChildAdded: stringList.size()=====" + stringList.size());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("MessageList").document(messageReceiverName).update("count", stringList.size());
                    userNameValue = map.get("SenderName").toString();
                    SenderIDValue = map.get("SenderID").toString();
                    ReceiverIDValue = map.get("ReceiverID").toString();
                    ReceiverImageValue = map.get("ReceiverImage").toString();
                    ReceiverNameValue = map.get("ReceiverName").toString();
                    timestampValue = map.get("timestamp").toString();

                    Log.e(TAG, "onChildAdded:userName " + userNameValue);
                    Log.e(TAG, "onChildAdded:messageSenderName " + messageSenderName);
                    Log.e(TAG, "onChildAdded:message " + messageValue);
                    String title = "<b>You</b>";
                    String title2 = "<b>" + messageReceiverName + "</b>";

                    if (userNameValue.equals(messageSenderName)) {

                        addMessageBox(Html.fromHtml(title) + " \n" + messageValue, 2);
                    } else {
                        addMessageBox(Html.fromHtml(title2) + " \n" + messageValue, 1);
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

        try {
            if (MainActivityStr.equals("MainActivity")) {
                Log.e(TAG, "sendDataToFirebase: if");
                Log.e(TAG, "sendDataToFirebase: " + id);
                Map<String, String> map1 = new HashMap<String, String>();

                // messageList.setSender(messageSenderName);
                //  messageList.setReceiver(messageReceiverName);
                // messageList.setSenderId(messageSenderID);
                // messageList.setReceiverId(messageReceiverID);

                Log.e(TAG, "sendDataToFirebase:messageReceiverName " + messageReceiverName);
                Log.e(TAG, "sendDataToFirebase: messageReceiverImage" + messageReceiverImage);
                Log.e(TAG, "sendDataToFirebase:messageReceiverID " + messageReceiverID);
                messageList.setFullname(messageSenderName);
                messageList.setPhoto(messageSenderImage);
                messageList.setUid(messageSenderID);
                messageList.setDate((Timestamp) Timestamp.now());

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
            } else {
                Log.e(TAG, "sendDataToFirebase:else==== ");

                Log.e(TAG, "sendDataToFirebase: ");
                Log.e(TAG, "sendDataToFirebase: " + id);
                Map<String, String> map1 = new HashMap<String, String>();

                // messageList.setSender(messageSenderName);
                //  messageList.setReceiver(messageReceiverName);
                // messageList.setSenderId(messageSenderID);
                // messageList.setReceiverId(messageReceiverID);
                messageList.setFullname(name);
                messageList.setPhoto(image);
                messageList.setUid(id);
                messageList.setDate((Timestamp) Timestamp.now());

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

                db.collection("MessageList").document(name).set(messageList)
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserDAtaID() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReferenceRef;
        collectionReferenceRef = db.collection("MJ_Users");
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e(TAG, "getUserDAtaID: " + Constants.Uid);
        collectionReferenceRef.whereEqualTo("userId", Constants.Uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => \n" + document.getData().get("fullname") + " => \n" + document.getData().get("photo"));
                                Log.e(TAG, "onComplete: " + document.getData().get("fullname"));
                                Constants.NAME = (String) document.getData().get("fullname");
                                messageSenderName = (String) document.getData().get("fullname");
                                messageSenderImage = (String) document.getData().get("photo");

                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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
        TextView textView = new TextView(ChatActivityMain.this);
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