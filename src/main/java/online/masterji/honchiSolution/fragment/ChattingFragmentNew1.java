package online.masterji.honchiSolution.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.LoginActivity;
import online.masterji.honchiSolution.chatting.MessageAdapterNew;
import online.masterji.honchiSolution.chatting.Messages;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Chatlist;
import online.masterji.honchiSolution.domain.Images;
import online.masterji.honchiSolution.domain.MessageList;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.WaitDialog;

public class ChattingFragmentNew extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ChattingFragmentNew";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    WaitDialog waitDialog;
    List<String> stringList = new ArrayList<>();
    RecyclerView recyclerView;
    EditText text_content;
    LinearLayout layoutEmptyc;
    ImageButton btn_send;
    TextView typeme;
    // RecyclerView rvChatting;
    LinearLayout frag;
    MessageAdapterNew chatAppMsgAdapter;
    List<Messages> msgDtoList = new ArrayList<>();
    List<Messages> messagesList;
    Messages msgDto;
    Firebase reference1, reference2;
    //  private UserAdapter userAdapter;
    private List<User> mUsers;
    private FirebaseAuth mAuth;
    FirebaseUser fuser;
    //  DatabaseReference reference;

    private List<Chatlist> usersList;
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    public ChattingFragmentNew() {
        // Required empty public constructor
    }

    public static ChattingFragmentNew newInstance(String param1, String param2) {
        ChattingFragmentNew fragment = new ChattingFragmentNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getMassage();
    }

    @Override
    public void onPause() {
        super.onPause();
        //getMassage();
    }

    @Override
    public void onStop() {
        super.onStop();
        // getMassage();
    }

    DatabaseReference mDatabaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;

    private TextView userName, userLastSeen;
    private CircleImageView userImage;
    private Toolbar ChatToolBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatting, container, false);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            view = inflater.inflate(R.layout.fragment_chatting, container, false);
            view.setBackgroundColor(Color.WHITE);
            layout = (LinearLayout) view.findViewById(R.id.layout111);
            sendButton = (ImageView) view.findViewById(R.id.sendButtonn);
            messageArea = (EditText) view.findViewById(R.id.messageArea);
            scrollView = (ScrollView) view.findViewById(R.id.scrollVieww);

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                getUserDAtaID();

                initData(view);
                getMassage();
                Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.e(TAG, "getUserDAtaID: " + Constants.Uid);
                Firebase.setAndroidContext(getContext());


            }


            userName = (TextView) view.findViewById(R.id.custom_profile_name);
            userLastSeen = (TextView) view.findViewById(R.id.custom_user_last_seen);
            userImage = (CircleImageView) view.findViewById(R.id.custom_profile_image);
            messageSenderName = Constants.NAME;

            userName.setText(messageSenderName);
            Picasso.get().load(messageSenderImage).placeholder(R.drawable.user_image).into(userImage);


/*
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            Log.e(TAG, "run:==== " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                            ;
                                            //getData();
                                            getMassage();

                                        }
                                    }
                                }
        );*/


            //  new LongOperation().execute();

            // Inflate the layout for this fragment
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
           /* if (FirebaseAuth.getInstance().getUid().equals("5KktiHZb7aSbaFqZJUiSF8fhzYn2")) {
                Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.e(TAG, "getUserDAtaID: " + Constants.Uid);

                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
               *//* searchView = (SearchView) view.findViewById(R.id.searchView);
                searchView.setQueryHint("Search User");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // Toast.makeText(getContext(), "query" + query, Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // getUserDAta();
                        return false;
                    }
                });
*//*
                 *//*  final EditText ETsearch = view.findViewById(R.id.ETsearchChat);
                ETsearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchData(ETsearch.getText().toString());

                    }
                });*//*
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    //getData();

                    getUserDAta();
                }
                //  messageReceiverID = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
                //  messageReceiverName = "deeps malvi";
                //  messageReceiverImage = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";


            } else*/
           /* if (FirebaseAuth.getInstance().getUid().equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
                Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.e(TAG, "getUserDAtaID: " + Constants.Uid);

                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                recyclerView = view.findViewById(R.id.recycler_vieww);

                view.setBackgroundColor(Color.WHITE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } else if (FirebaseAuth.getInstance().getUid().equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } else if (FirebaseAuth.getInstance().getUid().equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } else if (FirebaseAuth.getInstance().getUid().equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } else if (FirebaseAuth.getInstance().getUid().equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            } else if (FirebaseAuth.getInstance().getUid().equals("dKxjxLvOAXdgxOLiFgzUfMR733t1")) {
                view = inflater.inflate(R.layout.fragment_chatting1, container, false);
                view.setBackgroundColor(Color.WHITE);
                recyclerView = view.findViewById(R.id.recycler_vieww);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            } else {*/

                waitDialog = new WaitDialog(getContext());
                fuser = FirebaseAuth.getInstance().getCurrentUser();
//        recyclerView = view.findViewById(R.id.recycler_vieww);

                usersList = new ArrayList<>();

                //}


            }


       /* View view = inflater.inflate(R.layout.fragment_chatting1, container, false);
        view.setBackgroundColor(Color.WHITE);
*/

        }/* else {
            startActivity(new Intent(getContext(), LoginActivity.class));

        }*/


        return view;

    }

    private void searchData(String s) {

    }

    private void getData() {
        Log.e(TAG, "getData: ");
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Messages");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " getData=> " + document.getData());
                            }
                            List<Images> sliders = task.getResult().toObjects(Images.class);
                            // setOffers(sliders.get(0));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


    }

    private DatabaseReference mConvDatabase;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mMessageDatabase;

    private String mCurrent_user_id;

    private View mMainView;

    private String messageReceiverID, messageSenderName, messageReceiverName, messageReceiverImage, messageSenderImage, messageSenderID;

    private void initData(View view) {
        mAuth = FirebaseAuth.getInstance();

        messageSenderID = mAuth.getCurrentUser().getUid();
        //messageReceiverID = mAuth.getCurrentUser().getUid();


/*
        messageReceiverID = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
        messageReceiverName = "deeps malvi";
        messageReceiverImage = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";
   */
        messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
        messageReceiverName = "deeksha gupta";
        messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
        if (messageSenderID.equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
            messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
            messageReceiverName = "deeksha gupta";
            messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";

        } else if (messageSenderID.equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
            messageReceiverName = "diksha gupta";
            messageReceiverImage = "https://lh5.googleusercontent.com/-YZHqM0b2Po8/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3reQxSpodnBzUp3nxmzNpS_ZtLk8dg/s96-c/photo.jpg";

            messageReceiverID = "MWklcsqWViN1m2vIwQcyqcOWo893";

        } else if (messageSenderID.equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
            messageReceiverName = "shikha sharma";
            messageReceiverImage = "https://lh3.googleusercontent.com/-r8oLxAgnzik/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rcdd_1KyH6eJtnZitopSNyJ0lpuWg/s96-c/photo.jpg";
            messageReceiverID = "WIQFi2OFViWMeFE5pI4RKEaTrM82";

        } else if (messageReceiverID.equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
            messageReceiverName = "utkarsh kumar";
            messageReceiverImage = "https://lh5.googleusercontent.com/-wLbQRKzgGXY/AAAAAAAAAAI/AAAAAAAAqrI/PPHMMaQUN_o/s96-c/photo.jpg";
            messageReceiverID = "N5KmELyGB8QrlWdgjMOUiofK3m13";

        } else if (messageSenderID.equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
            messageReceiverName = "nirupama Lowanshi";
            messageReceiverID = "TwNBTwPMeaQgUf2LtaxX6B1Uw7n1";

            messageReceiverImage = "https://lh5.googleusercontent.com/-aSi5PSDcMfY/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rchliQkzgHC21qyIYqkK43JcJ5qQg/s96-c/photo.jpg";
        } else {
           /* messageReceiverID = "dKxjxLvOAXdgxOLiFgzUfMR733t1";
            messageReceiverName = "Sanjay";

            messageReceiverImage = "https://lh6.googleusercontent.com/-WwrwoX0QviU/AAAAAAAAAAI/AAAAAAAAF-M/oa_JXLE8wd0/s96-c/photo.jpg";
       */
          /*  messageReceiverID = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
            messageReceiverName = "deeps malvi";
            messageReceiverImage = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";
*/
        }

        Log.e(TAG, "initData: " + messageReceiverID);
        Log.e(TAG, "initData: " + messageReceiverName);
        Log.e(TAG, "initData: " + messageReceiverImage);
        layoutEmptyc = view.findViewById(R.id.layoutEmptyc);
        frag = view.findViewById(R.id.frag);

        text_content = view.findViewById(R.id.text_content);

        btn_send = view.findViewById(R.id.btn_sendd);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    text_content.getText().toString().trim();
                    String messageText = text_content.getText().toString();

                    if (!messageText.equals("")) {
                        sendData();
                        sendDataToFirebase(messageText);
                    } else {
                        text_content.setText("");
                        text_content.setHint("type message here");
                    }
                } else {
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }


            }
        });

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

   /* @Override
    public void onStart() {
        super.onStart();

        getMassage();
    }*/

    private void sendData() {
        setDataToDB();

        messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
        messageReceiverName = "deeksha gupta";
        messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
        if (messageSenderID.equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
            messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
            messageReceiverName = "deeksha gupta";
            messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
            // setDataToDB();

        } else if (messageSenderID.equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
            messageReceiverName = "diksha gupta";
            messageReceiverImage = "https://lh5.googleusercontent.com/-YZHqM0b2Po8/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3reQxSpodnBzUp3nxmzNpS_ZtLk8dg/s96-c/photo.jpg";

            messageReceiverID = "MWklcsqWViN1m2vIwQcyqcOWo893";
            //setDataToDB();

        } else if (messageSenderID.equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
            messageReceiverName = "shikha sharma";
            messageReceiverImage = "https://lh3.googleusercontent.com/-r8oLxAgnzik/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rcdd_1KyH6eJtnZitopSNyJ0lpuWg/s96-c/photo.jpg";
            messageReceiverID = "WIQFi2OFViWMeFE5pI4RKEaTrM82";
            //setDataToDB();

        } else if (messageReceiverID.equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
            messageReceiverName = "utkarsh kumar";
            messageReceiverImage = "https://lh5.googleusercontent.com/-wLbQRKzgGXY/AAAAAAAAAAI/AAAAAAAAqrI/PPHMMaQUN_o/s96-c/photo.jpg";
            messageReceiverID = "N5KmELyGB8QrlWdgjMOUiofK3m13";
            //setDataToDB();

        } else if (messageSenderID.equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
            messageReceiverName = "nirupama Lowanshi";
            messageReceiverID = "TwNBTwPMeaQgUf2LtaxX6B1Uw7n1";

            messageReceiverImage = "https://lh5.googleusercontent.com/-aSi5PSDcMfY/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rchliQkzgHC21qyIYqkK43JcJ5qQg/s96-c/photo.jpg";
            //setDataToDB();

        } else {

            messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
            messageReceiverName = "deeksha gupta";
            messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";

            // setDataToDB();
           /* messageReceiverID = "dKxjxLvOAXdgxOLiFgzUfMR733t1";
            messageReceiverName = "Sanjay";

            messageReceiverImage = "https://lh6.googleusercontent.com/-WwrwoX0QviU/AAAAAAAAAAI/AAAAAAAAF-M/oa_JXLE8wd0/s96-c/photo.jpg";
       */
          /*  messageReceiverID = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
            messageReceiverName = "deeps malvi";
            messageReceiverImage = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";
*/

            //setDataToDB();
        }


        // getMessageFromDatabase();

        text_content.setText("");
    }

    private void setDataToDB() {
        messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
        messageReceiverName = "deeksha gupta";
        messageReceiverImage = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
        messageSenderName = Constants.NAME;

        String msgContent = text_content.getText().toString();
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", msgContent);
        map.put("SenderName", messageSenderName);
        map.put("SenderID", messageSenderID);
        map.put("ReceiverID", messageReceiverID);
        map.put("ReceiverImage", messageReceiverImage);
        map.put("ReceiverName", messageReceiverName);
        map.put("timestamp", (Timestamp) Timestamp.now());
        reference1.push().setValue(map);
       /* Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("message", msgContent);
        map.put("SenderName", messageReceiverName);
        map.put("SenderID", messageSenderID);
        map.put("ReceiverID", messageReceiverID);
        map.put("ReceiverImage", messageReceiverImage);
        map.put("ReceiverName", messageSenderName);
        map.put("timestamp", (Timestamp) Timestamp.now());

        reference2.push().setValue(map1);
       */ reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                try {
                    String messageValue = null;
                    String userNameValue = null;
                    String SenderIDValue = null;
                    String ReceiverIDValue = null;
                    String ReceiverImageValue = null;
                    String ReceiverNameValue = null;
                    String timestampValue = null;


                    messageValue = map.get("message").toString();
                    stringList.add(messageValue);
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
                    String title2 = "<b> Masterji</b>";

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

   /* @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            getMassage();
        }
    }*/

    private void getMassage() {
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getMessageFromDatabase();
    }


    private void getMessageFromDatabase() {

        Log.e(TAG, "getMessageFromDatabase: ");
        messageSenderName = Constants.NAME;
        messageSenderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messageReceiverID = "BfX8tqKA2cRVvWozWMldbHdKcfj1";

        Log.e(TAG, "getChattingData: messageSenderName== " + messageSenderName);
        Log.e(TAG, "getChattingData: messageSenderID==" + messageSenderID);
        Log.e(TAG, "getChattingData: messageReceiverID===" + messageReceiverID);
        reference1 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageSenderID + "_" + messageReceiverID);
      //  reference2 = new Firebase("https://project-masterji.firebaseio.com/messages/" + messageReceiverID + "_" + messageSenderID);

        Log.e(TAG, "getChattingData: reference1" + reference1);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);


                try {
                    String messageValue = null;
                    String userNameValue = null;
                    String SenderIDValue = null;
                    String ReceiverIDValue = null;
                    String ReceiverImageValue = null;
                    String ReceiverNameValue = null;
                    String timestampValue = null;

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
                    String title2 = "<b> Masterji </b>";

                    if (userNameValue.equals(messageSenderName)) {

                        addMessageBox(Html.fromHtml(title) + " \n" + messageValue, 2);
                    } else {
                        addMessageBox(Html.fromHtml(title2) + " \n" + messageValue, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

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


    public void addMessageBox(String message, int type) {
        Log.e(TAG, "addMessageBox: ");
        Log.e(TAG, "addMessageBox:message " + message);
        TextView textView = new TextView(getContext());
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

    MessageList messageList = new MessageList();

    private void sendDataToFirebase(String messageText) {
        Log.e(TAG, "sendDataToFirebase: ");
        Log.e(TAG, "sendDataToFirebase: " + id);
        Map<String, String> map1 = new HashMap<String, String>();

        // messageList.setSender(messageSenderName);
        //  messageList.setReceiver(messageReceiverName);
        // messageList.setSenderId(messageSenderID);
        // messageList.setReceiverId(messageReceiverID);
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
    }

    String saveCurrentDate, saveCurrentTime;
    private FirebaseFirestore RootRef;
    Map messageTextBody;


    User user;
    List<User> productList = new ArrayList<>();
    List<String> list = new ArrayList<>();
    List<String> Imagesstrings = new ArrayList<>();
    List<String> id = new ArrayList<>();
    List<String> idList = new ArrayList<>();
    List<online.masterji.honchiSolution.chatting.Messages> arrayList = new ArrayList<>();


    Firebase reference;
    DatabaseReference mDatabase;
    List<String> fullnameList = new ArrayList<>();
    List<String> photoList = new ArrayList<>();
    List<String> idListList = new ArrayList<>();


    @Override
    public void onRefresh() {
        //getMassage();
    }


}
