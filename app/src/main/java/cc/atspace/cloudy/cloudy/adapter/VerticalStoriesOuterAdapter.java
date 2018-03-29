package cc.atspace.cloudy.cloudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.atspace.cloudy.cloudy.R;


public class VerticalStoriesOuterAdapter extends RecyclerView.Adapter<VerticalStoriesOuterAdapter.VerticalStoriesOuterAdapterViewHolder> {

    private Context context;

    public VerticalStoriesOuterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VerticalStoriesOuterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_vertical_rv_outer, parent, false);
        return new VerticalStoriesOuterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalStoriesOuterAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class VerticalStoriesOuterAdapterViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout VerticalOuterLayout;
        public TextView dummy;

        public VerticalStoriesOuterAdapterViewHolder(View itemView) {
            super(itemView);
            VerticalOuterLayout = (RelativeLayout) itemView.findViewById(R.id.layout_vertical_rv_outer);
//            dummy = itemView.findViewById(R.id.dummy_text);


        }
    }
}
