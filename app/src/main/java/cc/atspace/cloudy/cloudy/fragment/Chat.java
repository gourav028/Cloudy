package cc.atspace.cloudy.cloudy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cc.atspace.cloudy.cloudy.R;

public class Chat extends Fragment {

    private View rootView;
    private Button profile;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        context = getActivity();
        profile = (Button) this.rootView.findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(context, Profile.class));

            }
        });
        return rootView;
    }
}
