package online.masterji.honchiSolution.chatting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;

public class ChatActivityNew extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private String messageReceiverID, messageReceiverName, messageReceiverImage, messageSenderID;

    private TextView userName, userLastSeen;
    private CircleImageView userImage;

    private Toolbar ChatToolBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore RootRef;

    private ImageButton SendMessageButton, SendFilesButton;
    private EditText MessageInputText;

    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapterNew messageAdapter;
    private RecyclerView userMessagesList;


    private String saveCurrentTime, saveCurrentDate;
    List<String> list = new ArrayList<>();
    List<String> Imagesstrings = new ArrayList<>();
    List<String> idList = new ArrayList<>();
    List<Messages> arrayList = new ArrayList<>();
    Messages messages = new Messages();

    String name, image, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        name = intent.getStringExtra("list");
        image = intent.getStringExtra("imagesstrings");
        id = intent.getStringExtra("id");
        Log.e(TAG, "onCreate: name " + name);
        Log.e(TAG, "onCreate: image " + image);
        Log.e(TAG, "onCreate: id " + id);
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseFirestore.getInstance();


        // messageReceiverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messageReceiverID = id;
        messageReceiverName = name;
        messageReceiverImage = image;
/*
 messageReceiverName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        messageReceiverImage = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());
*/


        IntializeControllers();
        getMessgages(id, name, image);

        userName.setText("" + name);
        Picasso.get().load(image).placeholder(R.drawable.ic_image_placeholder).into(userImage);


        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });


    }


    private void getMessgages(final String id, final String name, final String image) {
        Log.e(TAG, "getMessgages:messageReceiverID " + messageReceiverID);
        Log.e(TAG, "getMessgages:messageSenderID " + messageSenderID);
        Log.e(TAG, "name, image, id---- " + name + image + id);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /* String document = db.collection("Messages").document(messageSenderID).collection(messageReceiverID).getId();
         */
        CollectionReference collectionReferenceRef;
        // collectionReferenceRef = db.collection("Messages").document(messageReceiverID).collection(messageSenderID);
        collectionReferenceRef = db.collection("Messages").document(messageSenderID).collection(messageReceiverID);

        collectionReferenceRef/*.orderBy("timstamp", Query.Direction.ASCENDING).limit(100)*/.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, "======getMessgages======" + document.getId() + " ==============> " + document.getData());
                       /* list.add((String) document.getData().get("message"));
                        Imagesstrings.add((String) document.getData().get("time"));
                        idList.add((String) document.getId());
*/
                    }
                    arrayList = task.getResult().toObjects(Messages.class);

                    initAdapter(arrayList, name, image, id);

                 /*   List<Messages> bookingTailors= new ArrayList<>();
                    try {
                        bookingTailors = task.getResult().toObjects(Messages.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/



                   /* if (CollectionUtils.isEmpty(bookingTailors)) {
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutEmptyo.setVisibility(View.GONE);

                        bookingTailors = task.getResult().toObjects(BookingTailor.class);

                        Log.d(TAG, "onComplete() called with: task = [" + bookingTailors.size() + "]");


                        for (int i = 0; i < bookingTailors.size(); i++) {
                            if (!TextUtils.isEmpty(Constants.Uid)) {
                                if (bookingTailors.get(i).getId().equals(Constants.Uid)) {
                                    initAdapter(bookingTailors);

                                }
                            }
                        }
                    } else {


                        recyclerView.setVisibility(View.GONE);
                        layoutEmptyo.setVisibility(View.VISIBLE);

                    }
*/ // stopping swipe refresh
                    // swipeRefreshLayout.setRefreshing(false);

                } else { // stopping swipe refresh
                    //swipeRefreshLayout.setRefreshing(false);

                    Log.e(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
/*.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();



                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData().get("fullname"));

                            Log.e("TAG", document.getId() + " => \n" + document.getData().get("fullname") + " => \n" + document.getData().get("photo"));
                            list.add((String) document.getData().get("fullname"));
                            Imagesstrings.add((String) document.getData().get("photo"));
                            id.add((String) document.getId());

                        initAdapter(list,Imagesstrings,id);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
    }

    private void initAdapter(List<Messages> arrayList, String name, String image, String id) {
        MessageAdapterNew userAdapter = new MessageAdapterNew(ChatActivityNew.this, arrayList, name, image, id);
        userMessagesList.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }

    //private SwipeRefreshLayout swipeRefreshLayout;

    private void IntializeControllers() {
       /* swipeRefreshLayout = findViewById(R.id.swipe_refresh_layoutChat);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getMessgages();
                                    }
                                }
        );*/
        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(ChatToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);

        userName = (TextView) findViewById(R.id.custom_profile_name);
        //userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);
        userImage = (CircleImageView) findViewById(R.id.custom_profile_image);

        SendMessageButton = (ImageButton) findViewById(R.id.send_message_btn);
        SendFilesButton = (ImageButton) findViewById(R.id.send_files_btn);
        MessageInputText = (EditText) findViewById(R.id.input_message);
        MessageInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    // Toast.makeText(ChatActivity.this, "isTyping", Toast.LENGTH_SHORT).show();
                    //MessageInputText.setHint(Constants.NAME + ".isTyping");
                } else {
                    //Toast.makeText(ChatActivity.this, "isTyping", Toast.LENGTH_SHORT).show();

                    // MessageInputText.setText(Constants.NAME + ".isTyping");
                }
            }
        });
      /*  messages = new Messages("Welcome to master ji app");
        messagesList.add(messages);
      */
        messageAdapter = new MessageAdapterNew(this, messagesList);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        int newMsgPosition = messagesList.size() - 1;
        messageAdapter.notifyItemInserted(newMsgPosition);
        userMessagesList.scrollToPosition(newMsgPosition);
        //text_content.setText("");
        userMessagesList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    userMessagesList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*if (messagesList.size() > 0) {
                                try {
                                    userMessagesList.smoothScrollToPosition(
                                            userMessagesList.getAdapter().getItemCount() - 1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
*/
                        }
                    }, 100);
                }
            }
        });

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());
    }

    Map messageTextBody;

    private void SendMessage() {
        String messageText = MessageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            MessageInputText.setHint("first write your message...");
            //  Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        } else {
            String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID;
            String messagePushID = FirebaseFirestore.getInstance().getApp().getPersistenceKey();

            messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            messageTextBody.put("to", messageReceiverID);
            messageTextBody.put("messageID", messagePushID);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);
            messageTextBody.put("ReceiverID", messageReceiverID);
            messageTextBody.put("ReceiverName", messageReceiverName);
            messageTextBody.put("ReceiverImage", messageReceiverImage);


            RootRef.collection("Messages").document(messageSenderID).collection(messageReceiverID).document()
                    // RootRef.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()

                    .set(messageTextBody)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully written!");
                            MessageInputText.setText("");
                            getMessgages(id, name, image);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error writing document", e);
                            // waitDialog.dismiss();
                            // SnackBarUtil.showError(getContext(), frag, "Failed,Please Try Again!");
                        }
                    });
            // String messagePushID = FirebaseFirestore.getInstance().getApp().getPersistenceKey();

          /*  Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put(messageReceiverRef + "/" + messagePushID, messageTextBody);
            messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            messageTextBody.put("to", messageReceiverID);
            messageTextBody.put("messageID", messagePushID);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);
            messageTextBody.put("messageBodyDetails", messageBodyDetails);
*/
            /* RootRef.collection("Messages").document(messageSenderID).collection(messageReceiverID).document()
             *//* RootRef.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()*//*.update(messageTextBody).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChatActivity.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            });*/
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
   /* @Override
    public void onRefresh() {
        getMessgages();
    }*/



    /*@Override
    protected void onStart() {
        super.onStart();
        getMessgages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessgages();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMessgages();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMessgages();
    }*/
}
