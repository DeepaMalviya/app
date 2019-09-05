package online.masterji.honchiSolution.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.chatAdapter;
import online.masterji.honchiSolution.chatting.ChatActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Messages;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.WaitDialog;

import static online.masterji.honchiSolution.constant.Constants.PHOTO;
import static online.masterji.honchiSolution.constant.Constants.WITH_NAME;

public class ChattingFragment extends Fragment {
    private static final String TAG = "ChattingFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    WaitDialog waitDialog;
    List<String> stringList = new ArrayList<>();

    public ChattingFragment() {
        // Required empty public constructor
    }

    public static ChattingFragment newInstance(String param1, String param2) {
        ChattingFragment fragment = new ChattingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    String COLLECTION_KEY = "Chat",fuser;
    String DOCUMENT_KEY = "Message",userid;
    String NAME_FIELD = "Name";
    String TEXT_FIELD = "Text";
    RelativeLayout linearVisible, layout2, layout1;
    EditText text_content;
    LinearLayout layoutEmptyc;
    ImageButton btn_send;
    TextView typeme;
    RecyclerView rvChatting;
    RelativeLayout frag;
    chatAdapter chatAppMsgAdapter;
    List<Messages> msgDtoList;
    List<Messages> messagesList;
    Messages msgDto;
    Firebase reference1, reference2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        view.setBackgroundColor(Color.WHITE);

        waitDialog = new WaitDialog(getContext());
        msgDtoList = new ArrayList<Messages>();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {


            getUserDAta();
            userid ="dKxjxLvOAXdgxOLiFgzUfMR733t1";
            fuser = Constants.NAME;

          // getMassage();
            initData(view);

            Firebase.setAndroidContext(getContext());
            reference1 = new Firebase("https://android-chat-app-e711d.firebaseio.com/messages/" + Constants.NAME + "_" + WITH_NAME);
            reference2 = new Firebase("https://android-chat-app-e711d.firebaseio.com/messages/" + WITH_NAME + "_" + Constants.NAME);

           /* btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = text_content.getText().toString();

                    if(!messageText.equals("")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", messageText);
                        map.put("user", Constants.NAME);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                    }
                }
            });*/
        }


        return view;

    }

    private void initData(View view) {
     //   layoutEmptyc = view.findViewById(R.id.layoutEmptyc);
        frag = view.findViewById(R.id.frag);
        //linearVisible = view.findViewById(R.id.linearVisible);
       /* layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
      */
        text_content = view.findViewById(R.id.text_content);
        text_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_send = view.findViewById(R.id.btn_sendd);
      //  rvChatting = view.findViewById(R.id.rvChatting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvChatting.setLayoutManager(linearLayoutManager);


        msgDto = new Messages(Messages.MSG_TYPE_RECEIVED, "admin");
        msgDtoList.add(msgDto);

        // Create the data adapter with above data list.
       // chatAppMsgAdapter = new chatAdapter(getContext(), msgDtoList);

        // Set data adapter to RecyclerView.
        rvChatting.setAdapter(chatAppMsgAdapter);
        rvChatting.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    rvChatting.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (msgDtoList.size() > 0) {
                                rvChatting.smoothScrollToPosition(
                                        rvChatting.getAdapter().getItemCount() - 1);
                            }

                        }
                    }, 100);
                }
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // getMassage();
                String messagePushID = FirebaseFirestore.getInstance().getApp().getPersistenceKey();
                Log.e(TAG, "onClick: messagePushID" + messagePushID);
                String msgContent = text_content.getText().toString();
                if (!TextUtils.isEmpty(msgContent)) {
                    // Add a new sent message to the list.

                    // msgDto = new Messages(Messages.MSG_TYPE_SENT, msgContent);
                    // msgDtoList.add(msgDto);

                    msgDto.setMessage(msgContent);
                    msgDto.setMessageID(messagePushID);
                    setMessage(msgDto, msgDtoList, msgContent);
                    rvChatting.setAdapter(chatAppMsgAdapter);
                    msgDtoList.add(msgDto);
                    chatAppMsgAdapter.notifyDataSetChanged();
                    getMassage();
                    int newMsgPosition = msgDtoList.size() - 1;


                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    rvChatting.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    text_content.setText("");
                    if(!msgContent.equals("")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", msgContent);
                        map.put("user", Constants.NAME);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                    }
                }
            }
        });

    }
    private String messageReceiverID, messageReceiverName, messageReceiverImage, messageSenderID;

    User user;
    Map messageTextBody;
    private void getUserDAta() {
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
    private FirebaseFirestore RootRef;
    private String saveCurrentTime, saveCurrentDate;

    private void setMessage(Messages msgDto, List<Messages> msgDtoList, String msgContent) {
       String messageText = text_content.getText().toString();
       Calendar calendar = Calendar.getInstance();

       SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
       saveCurrentDate = currentDate.format(calendar.getTime());

       SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
       saveCurrentTime = currentTime.format(calendar.getTime());
       if (TextUtils.isEmpty(messageText)) {
           Toast.makeText(getContext(), "first write your message...", Toast.LENGTH_SHORT).show();
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


           RootRef.collection("Messages").document(messageSenderID).collection(messageReceiverID).document()
                   // RootRef.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()

                   .set(messageTextBody)
                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           Log.d("TAG", "DocumentSnapshot successfully written!");

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
           RootRef.collection("Messages").document(messageSenderID).collection(messageReceiverID).document()
                   /* RootRef.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()*/.update(messageTextBody).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {
                   if (task.isSuccessful()) {
                       Toast.makeText(getContext(), "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                   }
                   text_content.setText("");
               }
           });
       }
   }



    //Messages msgDto = new Messages();

    private void getMassage() {
        Log.e(TAG, "getMassage: api");

        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Chatting");
        // Log.e(TAG, "getMassage: " + db.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME /*+ "_" + WITH_NAME*/).document().get());
        db.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + "deeksha gupta" /*+ "_" + WITH_NAME*/).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.e(TAG, "onSuccess:getMassage ");
                        // messagesList = new ArrayList<>();
                        //msgDto.setMessage("Hello Welcome To Masterji App");
                        // msgDto.setMessage("How may i help you");
                        /*  if (msgDtoList != null && !msgDtoList.isEmpty()) {
                         *//*  messagesList.set(0, msgDto);
                            messagesList.set(1, msgDto);*//*
                            msgDtoList.add(msgDto);

                        }*/
                        msgDtoList = queryDocumentSnapshots.toObjects(Messages.class);
                        Log.e(TAG, "onSuccess: " + msgDtoList.size());
                        for (int i = 0; i < msgDtoList.size(); i++) {
                            Log.e(TAG, "onSuccess: getMessage " + msgDtoList.get(i).getMessage());
                            Log.e(TAG, "onSuccess: getMessage " + msgDtoList.size());
                        }

                        if (!msgDtoList.isEmpty()) {
                            initAdapter(msgDtoList);

                        }


                    }
                });
        db.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + "deeksha gupta" /*+ "_" + WITH_NAME*/).document().get();

        collectionReference.whereEqualTo("userId", Constants.Uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                    }
                    try {
                        //  messagesList = task.getResult().toObjects(Messages.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
                waitDialog.dismiss();
            }
        });


    }

    private void initAdapter(List<Messages> messagesList) {
        // Create the data adapter with above data list.
      //  chatAppMsgAdapter = new chatAdapter(getContext(), messagesList);

        // Set data adapter to RecyclerView.
        rvChatting.setAdapter(chatAppMsgAdapter);

        int newMsgPosition = msgDtoList.size() - 1;

        // Notify recycler view insert one new data.
        chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

        // Scroll RecyclerView to the last message.
        rvChatting.scrollToPosition(newMsgPosition);

        // Empty the input edit text box.
        text_content.setText("");
    }

   /* private void setMessage(List<Messages> messagesList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Messages user = new Messages();
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (Constants.Uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            msgDto.setBelongsToCurrentUser(true);

        } else {
            msgDto.setBelongsToCurrentUser(false);

        }
        msgDto.setDate(Timestamp.now());
        msgDto.setFrom("admin");
        msgDto.setMessageID("1");
        msgDto.setName(Constants.NAME);
        msgDto.setTime(Timestamp.now());
        msgDto.setTo("New");
        msgDto.setType("user");
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Constants.NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Log.e(TAG, "setMessage: Constants.Uid"+Constants.Uid );
        Log.e(TAG, "setMessage: Constants.Uid"+Constants.NAME );

        if (Constants.Uid.equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            Log.e(TAG, "setMessage: " + WITH_NAME);
            msgDto.setBelongsToCurrentUser(false);

        } else if (Constants.Uid.equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        } else if (Constants.Uid.equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        }  else if (Constants.Uid.equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        } else {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(true);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        }

        try {
            msgDto.setId(Constants.Uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgDto.setDate(Timestamp.now());
        msgDto.setUserphoto(Constants.PHOTO);

        db.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()
                .set(msgDto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                       *//* Snackbar snackbar = SnackBarUtil.showSuccess(getContext(), frag, "Saved Successfully");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                ChattingFragment fragment=new ChattingFragment();
                                if (fragment != null) {
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, fragment)
                                            .commit();

                                }
                            }
                        });*//*
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(getContext(), frag, "Failed,Please Try Again!");
                    }
                });
    }

    private void setMessage(Messages msgDto, String msgContent) {

        Log.e(TAG, "setMessage: " + msgContent);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

       // Messages user = new Messages();
        msgDto.setMessage(msgContent);
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (Constants.Uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            msgDto.setBelongsToCurrentUser(true);

        } else {
            msgDto.setBelongsToCurrentUser(false);

        }
        msgDto.setDate(Timestamp.now());
        msgDto.setFrom("admin");
        msgDto.setMessageID("1");
        msgDto.setName(Constants.NAME);
        msgDto.setTime(Timestamp.now());
        msgDto.setTo("New");
        msgDto.setType("user");
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Constants.NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Log.e(TAG, "setMessage: Constants.Uid"+Constants.Uid );
        Log.e(TAG, "setMessage: Constants.Uid"+Constants.NAME );

        if (Constants.Uid.equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            Log.e(TAG, "setMessage: " + WITH_NAME);
            msgDto.setBelongsToCurrentUser(false);

        } else if (Constants.Uid.equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        } else if (Constants.Uid.equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        }  else if (Constants.Uid.equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(false);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        } else {
            WITH_NAME = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            msgDto.setBelongsToCurrentUser(true);

            Log.e(TAG, "setMessage: " + WITH_NAME);


        }

        try {
            msgDto.setId(Constants.Uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgDto.setDate(Timestamp.now());
        msgDto.setUserphoto(Constants.PHOTO);

        db.collection("Chatting").document(Constants.Uid).collection(Constants.NAME + "_" + WITH_NAME).document()
                .set(msgDto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                       *//* Snackbar snackbar = SnackBarUtil.showSuccess(getContext(), frag, "Saved Successfully");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                ChattingFragment fragment=new ChattingFragment();
                                if (fragment != null) {
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, fragment)
                                            .commit();

                                }
                            }
                        });*//*
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(getContext(), frag, "Failed,Please Try Again!");
                    }
                });

    }
*/
}
