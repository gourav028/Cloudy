package cc.atspace.cloudy.cloudy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.fragment.Chat;
import cc.atspace.cloudy.cloudy.fragment.Contact;
import cc.atspace.cloudy.cloudy.fragment.Story;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class MainActivity extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ImageView search, profileIcon, settingIcon;
    public Context context;

    //firebase

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        //transparent status and navigation bar
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_vp);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.mipmap.story_icon);
                        tabLayout.getTabAt(0).setText(null);
                        tabLayout.getTabAt(1).setIcon(null);
                        tabLayout.getTabAt(1).setText("Chat");
                        tabLayout.getTabAt(2).setIcon(null);
                        tabLayout.getTabAt(2).setText("Contacts");
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(null);
                        tabLayout.getTabAt(0).setText("Story");
                        tabLayout.getTabAt(1).setIcon(R.mipmap.chat_icon);
                        tabLayout.getTabAt(1).setText(null);
                        tabLayout.getTabAt(2).setIcon(null);
                        tabLayout.getTabAt(2).setText("Contacts");

                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(null);
                        tabLayout.getTabAt(0).setText("Story");
                        tabLayout.getTabAt(1).setIcon(null);
                        tabLayout.getTabAt(1).setText("Chat");
                        tabLayout.getTabAt(2).setIcon(R.mipmap.contacts_icon);
                        tabLayout.getTabAt(2).setText(null);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        search = (ImageView) findViewById(R.id.search_icon);
        profileIcon = (ImageView) findViewById(R.id.profile_icon);
        settingIcon = (ImageView) findViewById(R.id.setting_icon);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.cloudy_head_logo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QueryActivity.class));

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Search Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "profle Button Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Profile.class));
            }
        });
        settingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "setting Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        //Firebase

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        AppPreference.getInstance(context).setUserId(currentUser.getUid().toString());
        Log.d("curr", currentUser.toString());
        if (currentUser != null) {
            AppPreference.getInstance(context).setUserId(currentUser.getUid().toString());

            //    Intent startIntent = startActivity(MainActivity.this,StartActivity.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    Story story = new Story(context);
                    return story;
                case 1:
                    Chat chat = new Chat(context);
                    return chat;
                case 2:
                    Contact contact = new Contact(context);
                    return contact;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
