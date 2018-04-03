package cc.atspace.cloudy.cloudy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.bean.chat;

/**
 * Created by garuav on 03-04-2018.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<chat> mMessageList;
    private DatabaseReference mUserDatabase;

    public ConversationAdapter(List<chat> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_message_conversation, parent, false);

        return new ConversationViewHolder(v);

    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        public ConversationViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);


        }
    }

    @Override
    public void onBindViewHolder(final ConversationViewHolder viewHolder, int i) {

        chat c = mMessageList.get(i);
   viewHolder.messageText.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
