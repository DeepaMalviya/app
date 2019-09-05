package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.ChatActivityMain;
import online.masterji.honchiSolution.chatting.Messages;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.UserChatting;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private static final String TAG = "UserAdapter";
    private Context mContext;
    List<String> list;
    private boolean ischat;
    List<String> imagesstrings;
    List<String> id;
    String theLastMessage;
    List<Messages> arrayList;
    List<UserChatting> arrayList1;
    /*public UserAdapter(Context context, List<String> list, List<String> imagesstrings, List<String> id) {
        this.list = list;
        this.mContext = context;
        this.imagesstrings = imagesstrings;
        this.id = id;
    }*/

    public UserAdapter(Context context, List<UserChatting> arrayList) {
        this.arrayList1 = arrayList;
        this.mContext = context;


    }

    /*public UserAdapter(Context context, List<Messages> arrayList, List<String> list, List<String> imagesstrings, List<String> id) {
        this.list = list;
        this.mContext = context;
        this.imagesstrings = imagesstrings;
        this.id = id;
        this.arrayList = arrayList;
    }*/



    /*public UserAdapter(Context mContext, List<String> mUsers, List<String> ischat){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // final User user = list.get(position);
        holder.username.setText(arrayList1.get(position).getFullname());

        Glide.with(mContext).load(arrayList1.get(position).getPhoto()).into(holder.profile_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivityMain.class);
              //  intent.putExtra("list", list.get(position));
                intent.putExtra("user_id", arrayList1.get(position).getUid());
                intent.putExtra("user_name", arrayList1.get(position).getFullname());
                intent.putExtra("imagesstrings", arrayList1.get(position).getPhoto());
               // intent.putExtra("id", id.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList1.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }


}
