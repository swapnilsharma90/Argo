package com.dd.argo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dd.argo.R;

/**
 * Created by jkarkhanis on 30/03/16.
 */
public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView chatText;
    public ChatViewHolder(View itemView) {
        super(itemView);
        chatText=(TextView) itemView.findViewById(R.id.layout_chat_list_item_textview);

    }
}
