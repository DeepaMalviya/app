package online.masterji.honchiSolution.chatting;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;

public class MessageAdapterNew extends RecyclerView.Adapter<MessageAdapterNew.MessageViewHolder> {
    private static final String TAG = "MessageAdapter";
    Context context;
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    //   private DatabaseReference usersRef;
    String name, image, id;
    List<Messages> arrayList;
    List<String> list;
    List<String> imagesstrings;
    List<String> idList;

    public MessageAdapterNew(ChatActivity chatActivity, List<Messages> userMessagesList) {
        this.userMessagesList = userMessagesList;
        this.context = chatActivity;
        Log.e(TAG, " 1 name, image, id " +name + image +id);

    }

    public MessageAdapterNew(ChatActivity chatActivity, List<Messages> arrayList, String name, String image, String id) {
        this.context = chatActivity;
        this.userMessagesList = arrayList;
        this.image = image;
        this.name = name;
        this.id = id;
        Log.e(TAG, " 2 name, image, id =====>" +name + image +id);

    }

   /* public MessageAdapter(Context context, List<Messages> arrayList, List<String> list, List<String> imagesstrings, List<String> id) {
        this.context = context;
        this.userMessagesList = arrayList;
        this.imagesstrings = imagesstrings;
        this.list = list;
        this.idList = id;
        Log.e(TAG, " 3 name, image, id =====>" +name + image +id);

    }*/

    public MessageAdapterNew(Context context, List<Messages> arrayList, String messageReceiverName, String messageReceiverImage, String messageReceiverID) {

        this.context = context;
        this.userMessagesList = arrayList;
        this.image = messageReceiverImage;
        this.name = messageReceiverName;
        this.id = messageReceiverID;
        Log.e(TAG, " 4 name, image, id=====> " +name + image +id);

    }

    public MessageAdapterNew(Context context, List<Messages> msgDtoList) {
        this.userMessagesList = msgDtoList;
        this.context = context;
        Log.e(TAG, " 5 name, image, id =====>" +name + image +id);

    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_messsage_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_messages_layout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i) {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(i);

        String fromUserID = messages.getFrom();
        String toUserID = messages.getTo();
        String fromMessageType = messages.getType();
        Log.e(TAG, "onBindViewHolder: image" + image);
        Log.e(TAG, "============ messages============" + messages.getMessage());
        Log.e(TAG, "onBindViewHolder: fromUserID" + fromUserID);
        Log.e(TAG, "onBindViewHolder: toUserID" + toUserID);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("MJ_Users").whereEqualTo("userId", Constants.Uid).get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => \n" + document.getData().get("photo"));
                                String Image = String.valueOf(document.getData().get("photo"));
                                Picasso.get().load(Image).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);


                            }


                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        if (!TextUtils.isEmpty(image)&&image!=null) {
            Picasso.get().load(image).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

        }
        messageViewHolder.receiverMessageText.setVisibility(View.GONE);
        messageViewHolder.receiverProfileImage.setVisibility(View.GONE);
        messageViewHolder.senderMessageText.setVisibility(View.GONE);
        messageViewHolder.messageSenderPicture.setVisibility(View.GONE);
        messageViewHolder.messageReceiverPicture.setVisibility(View.GONE);
        /*if (imagesstrings.get(i) != null) {
            Picasso.get().load(imagesstrings.get(i)).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

        } else {
            if (!TextUtils.isEmpty(image)) {
                Picasso.get().load(image).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

            }
        }*/

        try {
            if (fromMessageType.equals("text")) {
                Log.e(TAG, "onBindViewHolder:fromMessageType ");
                if (fromUserID.equals(messageSenderId)) {
                    Log.e(TAG, "onBindViewHolder: fromUserID.equals(messageSenderId) ");
                    messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);

                    messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.my_message_bubble);
                    messageViewHolder.senderMessageText.setTextColor(Color.WHITE);
                    messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() /*+ " - " + messages.getDate()*/);
                    if (!TextUtils.isEmpty(imagesstrings.get(i))) {
                        Picasso.get().load(imagesstrings.get(i)).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

                    } else {
                        if (!TextUtils.isEmpty(image)) {
                            Picasso.get().load(image).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

                        }
                    }
                    Log.e(TAG, "onBindViewHolder: fromMessageTypemessages===========" + messages.getMessage());
                    if (!TextUtils.isEmpty(messages.getMessage())) {
                        messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n " + messages.getTime() /*+ " - " + messages.getDate()*/);

                    }
                } else {
                    Log.e(TAG, "onBindViewHolder:========else fromUserID.equals(messageSenderId) ");
                    Log.e(TAG, "onBindViewHolder: fromMessageType else messages==========" + messages.getMessage());
                    messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.other_message_bubble);
                    messageViewHolder.receiverMessageText.setTextColor(Color.WHITE);
                    messageViewHolder.receiverMessageText.setText(messages.getMessage() + "\n \n " + messages.getTime() /*+ " - " + messages.getDate()*/);

                    messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                    messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);
                    /*if (!TextUtils.isEmpty(imagesstrings.get(i))) {
                        Picasso.get().load(imagesstrings.get(i)).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

                    } else {
                        if (!TextUtils.isEmpty(image)) {
                            Picasso.get().load(image).placeholder(R.drawable.ic_image_placeholder).into(messageViewHolder.receiverProfileImage);

                        }
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

}
