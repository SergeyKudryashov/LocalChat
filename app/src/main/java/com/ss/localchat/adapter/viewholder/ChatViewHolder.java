package com.ss.localchat.adapter.viewholder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ss.localchat.R;
import com.ss.localchat.adapter.ChatListAdapter;
import com.ss.localchat.db.entity.Message;
import com.ss.localchat.db.entity.User;
import com.ss.localchat.util.DateFormatUtil;


public class ChatViewHolder extends RecyclerView.ViewHolder {

    private ImageView profileImageView;

    private TextView nameTextView;

    private TextView lastMessageTextView;

    private TextView dateTextView;

    private AppCompatTextView unreadMessagesCountTextView;


    public ChatViewHolder(View itemView) {

        super(itemView);

        profileImageView = itemView.findViewById(R.id.profile_image_view);
        nameTextView = itemView.findViewById(R.id.name_text_view);
        lastMessageTextView = itemView.findViewById(R.id.last_message_text_view);
        dateTextView = itemView.findViewById(R.id.date_text_view);
        unreadMessagesCountTextView = itemView.findViewById(R.id.unread_messages_count_text_view);
    }

    public void bind(final User user, Message lastMessage, int unreadMessagesCount, final ChatListAdapter.OnItemClickListener listener) {
        nameTextView.setText(user.getName());

        if (lastMessage != null) {
            lastMessageTextView.setVisibility(View.VISIBLE);
            dateTextView.setVisibility(View.VISIBLE);

            lastMessageTextView.setText(lastMessage.getText());
            dateTextView.setText(DateFormatUtil.formatChatDate(lastMessage.getDate()));
        } else {
            lastMessageTextView.setVisibility(View.GONE);
            dateTextView.setVisibility(View.GONE);
        }

        if (unreadMessagesCount > 0) {
            unreadMessagesCountTextView.setVisibility(View.VISIBLE);
            unreadMessagesCountTextView.setText(String.valueOf(unreadMessagesCount));
        } else {
            unreadMessagesCountTextView.setVisibility(View.GONE);
        }

        Glide.with(itemView)
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
                .error(Glide.with(itemView).load(R.drawable.no_user_image).apply(RequestOptions.circleCropTransform()))
                .into(profileImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(user);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(user, v);
                }
                return true;
            }
        });
    }
}
