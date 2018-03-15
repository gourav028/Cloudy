package cc.atspace.cloudy.cloudy.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.adapter.VerticalStoriesOuterAdapter;

public class Story extends Fragment {
    private Button prachi, pranjal;
    private View rootView;
    private Context context;
private RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    private  VerticalStoriesOuterAdapter verticalStoriesOuterAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_story, container, false);

        context=getActivity();

        rv = (RecyclerView) rootView.findViewById(R.id.vertical_rv_outer);

        verticalStoriesOuterAdapter= new VerticalStoriesOuterAdapter(context);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(verticalStoriesOuterAdapter);
        verticalStoriesOuterAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void init()
    {
    }
}
