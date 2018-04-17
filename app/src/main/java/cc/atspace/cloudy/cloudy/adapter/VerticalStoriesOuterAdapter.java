package cc.atspace.cloudy.cloudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.bean.story;


public class VerticalStoriesOuterAdapter extends RecyclerView.Adapter<VerticalStoriesOuterAdapter.VerticalStoriesOuterAdapterViewHolder> {

    private Context context;
    private List<story> mStoryList;

    public VerticalStoriesOuterAdapter(List<story> mStoryList, Context context) {
        this.context = context;
        this.mStoryList = mStoryList;
    }

    @Override
    public VerticalStoriesOuterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_vertical_rv_outer, parent, false);
        return new VerticalStoriesOuterAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VerticalStoriesOuterAdapterViewHolder holder, int position) {
        story s = mStoryList.get(position);
        Picasso.with(context).load(s.getStory_link()).into(holder.storyIV);
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public class VerticalStoriesOuterAdapterViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout VerticalOuterLayout;
        public ImageView storyIV;
        public TextView dummy;

        public VerticalStoriesOuterAdapterViewHolder(View itemView) {
            super(itemView);
            VerticalOuterLayout = (RelativeLayout) itemView.findViewById(R.id.layout_vertical_rv_outer);
            storyIV = (ImageView) itemView.findViewById(R.id.story_IV);
//            dummy = itemView.findViewById(R.id.dummy_text);


        }
    }
}
