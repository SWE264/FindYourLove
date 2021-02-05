package com.example.findyourlove.zhangzhipeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourlove.R;

import java.util.ArrayList;

public class ChatWindows extends Activity {
static ArrayList<Message> messages;
    RecyclerView showChat;
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_chat_windows);

        //DEMO DATA GEN
        messages=new ArrayList<>();
        messages.add(new Message("one message",0));
        messages.add(new Message("two message",1));
        //DELETE!!!

        Bundle bundle=this.getIntent().getExtras();
        String userName=bundle.getString("userName");
TextView showUser=findViewById(R.id.textView);
showUser.setText(userName);
        showChat  =findViewById(R.id.recyclerView2);
        showChat.setLayoutManager(new LinearLayoutManager(this));
      chatAdapter=new ChatAdapter(this);

        showChat.setAdapter(chatAdapter);

    }

    public void send(View view){
        EditText message=findViewById(R.id.editText);

        Message sendMessage=new Message(message.getText().toString(),0);
        messages.add(sendMessage);
        chatAdapter.notifyItemInserted(messages.size()-1);
        showChat.scrollToPosition(messages.size()-1);
        System.out.println("send");
        //THIS IS DISPLAY PART. SENDING HAS NOT BEEN DONE

        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemViewHolder> {
    public Context mContext;
    public ChatAdapter(Context mainActivity) {
        mContext=mainActivity;

    }
    @NonNull
    @Override
    public ChatAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatAdapter.ItemViewHolder holder;
        holder = new ChatAdapter.ItemViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.zchat_card, parent,    //Change here！！！！！！！！
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ItemViewHolder holder, int position) {
        int type=ChatWindows.messages.get(position).getType();

if(type==0){
    holder.rightMessage.setText(ChatWindows.messages.get(position).getContent());
    holder.rightMessage.setVisibility(View.VISIBLE);
}
else {
    holder.leftMessage.setText(ChatWindows.messages.get(position).getContent());
    holder.leftMessage.setVisibility(View.VISIBLE);
}
    }

    @Override
    public int getItemCount() {
        return ChatWindows.messages.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView leftMessage;
        TextView rightMessage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            leftMessage=itemView.findViewById(R.id.message);
            rightMessage=itemView.findViewById(R.id.message2);

        }
    }


}
