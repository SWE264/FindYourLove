package com.example.findyourlove;

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

import java.util.ArrayList;

class ChatWindows extends Activity {
static ArrayList<zMessage> zMessages;
    RecyclerView showChat;
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_chat_windows);

        //DEMO DATA GEN
        zMessages =new ArrayList<>();
        zMessages.add(new zMessage("one message",0));
        zMessages.add(new zMessage("two message",1));
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

        zMessage sendZMessage =new zMessage(message.getText().toString(),0);
        zMessages.add(sendZMessage);
        chatAdapter.notifyItemInserted(zMessages.size()-1);
        showChat.scrollToPosition(zMessages.size()-1);
        System.out.println("send");
        //THIS IS DISPLAY PART. SENDING HAS NOT BEEN DONE

        Intent intent=new Intent(this, SignUp.class);
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
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHolder holder;
        holder = new ItemViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.zchat_card, parent,    //Change here！！！！！！！！
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int type=ChatWindows.zMessages.get(position).getType();

if(type==0){
    holder.rightMessage.setText(ChatWindows.zMessages.get(position).getContent());
    holder.rightMessage.setVisibility(View.VISIBLE);
}
else {
    holder.leftMessage.setText(ChatWindows.zMessages.get(position).getContent());
    holder.leftMessage.setVisibility(View.VISIBLE);
}
    }

    @Override
    public int getItemCount() {
        return ChatWindows.zMessages.size();
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
