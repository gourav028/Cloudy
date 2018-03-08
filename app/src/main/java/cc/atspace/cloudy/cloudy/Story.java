package cc.atspace.cloudy.cloudy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Story extends Fragment {
    private Button prachi, pranjal;
    private View rootView;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_story, container, false);
        init();
        return rootView;
    }

    private void init()
    {
        context=getActivity();
/*

        prachi = (Button) this.rootView.findViewById(R.id.prachi);
        pranjal = (Button) this.rootView.findViewById(R.id.pranjal);

        prachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getContext(),Profile.class));
            }
        });

        pranjal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Profile.class));

            }
        });
*/


    }
}
