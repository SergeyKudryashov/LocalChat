package com.ss.localchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.localchat.R;
import com.ss.localchat.adapter.viewholder.ChatViewHolder;
import com.ss.localchat.db.entity.Chat;
import com.ss.localchat.db.entity.User;

import java.util.ArrayList;
import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Chat> mFilteredList = new ArrayList<>();
    private OnItemClickListener mListener;

    private List<Chat> mChats = new ArrayList<>();

    private Context mContext;


    public ChatListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_view, parent, false);
        return new ChatViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat user = mFilteredList.get(position);
        holder.bind(user.user, user.message, user.count, mContext);
    }

    @Override
    public int getItemCount() {
        return mFilteredList == null ? 0 : mFilteredList.size();
    }

    public void setUsers(List<Chat> chats) {
        mChats = chats;

        // TODO: 8/27/2018 change users set logic, should pass users list instead of user
        mFilteredList.clear();
        mFilteredList = chats;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private ArrayList<Chat> filter(List<Chat> models, String query) {

        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<Chat> filteredModelList = new ArrayList<>();
        for (Chat model : models) {
            final String name = model.user.getName().toLowerCase();
            final String message = model.message.getText().toLowerCase();
            if (name.contains(lowerCaseQuery) | message.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void getFilter(String str) {

        mFilteredList = filter(mChats, str);
        notifyDataSetChanged();

    }

    public interface OnItemClickListener {
        void onClick(User user);

        void onLongClick(User user, View view);
    }
}
