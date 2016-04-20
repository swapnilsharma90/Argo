package com.dd.argo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dd.argo.R;
import com.dd.argo.model.ChatItem;
import com.dd.argo.viewholder.ChatViewHolder;

import java.util.ArrayList;

/**
 * Created by jkarkhanis on 30/03/16.
 */
public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private ArrayList<ChatItem> mDataset;
    LinearLayout linearLayout;
    private static int selectedItem=-1;


    public ChatRecyclerViewAdapter(ArrayList<ChatItem> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chat_list_item, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
         linearLayout=(LinearLayout)view.findViewById(R.id.layout_chat_list_item_linearlayout);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.chatText.setText(mDataset.get(position).getChatText());
       // holder.chatText.setBackgroundResource(mDataset.get(position).isUsermessage() ? R.drawable.speech_bubble_green : R.drawable.speech_bubble_orange);
       linearLayout.setGravity(mDataset.get(position).isUsermessage() ? Gravity.RIGHT:Gravity.LEFT);
        if(selectedItem == position)
        holder.itemView.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(ChatItem chatItem) {
        mDataset.add(chatItem);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void setSelectedItem(int position)
    {
        selectedItem = position;
    }


}
