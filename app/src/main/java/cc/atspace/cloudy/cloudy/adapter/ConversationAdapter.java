package cc.atspace.cloudy.cloudy.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.activity.Conversation;
import cc.atspace.cloudy.cloudy.bean.chat;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

/**
 * Created by garuav on 03-04-2018.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<chat> mMessageList;
    private Context context;
    private DatabaseReference mUserDatabase;
    private boolean once = true;
    private String last = "";


    public ConversationAdapter(List<chat> mMessageList, Context context) {

        this.mMessageList = mMessageList;
        this.context = context;

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
        if (once) {
            last = c.getFrom();
            once = false;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.messageText.getLayoutParams();

        if (c.getFrom().toString().equals(AppPreference.getInstance(context).getUserId())) {
            Log.d("if: " + c.getFrom().toString(), AppPreference.getInstance(context).getUserId().toString());
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.messageText.setBackground(ContextCompat.getDrawable(context, R.drawable.right_chat_bubble));
        } else {
            Log.d("else: " + c.getFrom().toString(), AppPreference.getInstance(context).getUserId());
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            viewHolder.messageText.setBackground(ContextCompat.getDrawable(context, R.drawable.left_chat_bubble));

        }
        float dpRatio = context.getResources().getDisplayMetrics().density;
        int leftRightMargin = (int) (10.0 * dpRatio);
        if (last.equals(c.getFrom())) {
            params.setMargins(leftRightMargin, (int) (1.0 * dpRatio), leftRightMargin, (int) (1.0 * dpRatio));
        } else {
            last = c.getFrom();
            params.setMargins(leftRightMargin, (int) (5.0 * dpRatio), leftRightMargin, (int) (1.0 * dpRatio));
        }

        viewHolder.messageText.setLayoutParams(params);
        viewHolder.messageText.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
