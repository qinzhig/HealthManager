package sg.edu.nus.iss.medipal.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;

/**
 * Created by Divahar on 3/15/2017.
 * Description: This class handles the start up screens based on shared preference value
 */

public class HelpActivity extends AppCompatActivity {


    private LinearLayout helpDotsLayout;
    private TextView[] helpDots;
    private int[] helpLayoutPos;
    private Button btnSkip, btnNext;
    private PreferenceManager prefManager;
    private ViewPager strtUpViewPager;
    private HelpAdapter helpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PreferenceManager(getApplicationContext());

        if (null != prefManager.getSplashScreenPref()
                && prefManager.getSplashScreenPref().equals("false")) {
            launchHomeScreen();
        } else {
            setContentView(R.layout.activity_help);
            strtUpViewPager = (ViewPager) findViewById(R.id.helpView);
            helpDotsLayout = (LinearLayout) findViewById(R.id.helpLayoutDots);
            btnSkip = (Button) findViewById(R.id.skip);
            btnNext = (Button) findViewById(R.id.next);

            helpLayoutPos = new int[]{
                    R.layout.help_screen_1,
                    R.layout.help_screen_2,
                    R.layout.help_screen_3,
                    R.layout.help_screen_4};

            addDots(0);

            helpAdapter = new HelpAdapter();
            strtUpViewPager.setAdapter(helpAdapter);
            strtUpViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchHomeScreen();
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getItem(+1);
                    if (pos < helpLayoutPos.length) {
                        strtUpViewPager.setCurrentItem(pos);
                    } else {
                        launchHomeScreen();
                    }
                }
            });
        }
    }

    private void launchHomeScreen() {

        if (null != prefManager.getFirstTimeFlag()
                && prefManager.getFirstTimeFlag().equals("false")) {
            startActivity(new Intent(HelpActivity.this, MainActivity.class));
            finish();
        } else {
            Intent intent = new Intent(HelpActivity.this, AddEditPersonalBioActivity.class);
            intent.putExtra("firstTime", true);
            startActivity(intent);
            finish();
        }
    }

    private void addDots(int currentPage) {
        helpDots = new TextView[helpLayoutPos.length];

        int[] dotsActive = getResources().getIntArray(R.array.dots_active);
        int[] dotsInactive = getResources().getIntArray(R.array.dots_inactive);

        helpDotsLayout.removeAllViews();
        for (int i = 0; i < helpDots.length; i++) {
            helpDots[i] = new TextView(this);
            helpDots[i].setText(Html.fromHtml("&#8226;"));
            helpDots[i].setTextSize(35);
            helpDots[i].setTextColor(dotsInactive[currentPage]);
            helpDotsLayout.addView(helpDots[i]);
        }
        if (helpDots.length > 0)
            helpDots[currentPage].setTextColor(dotsActive[currentPage]);
    }

    private int getItem(int i) {
        return strtUpViewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if (position == helpLayoutPos.length - 1) {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class HelpAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public HelpAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(helpLayoutPos[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return helpLayoutPos.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
