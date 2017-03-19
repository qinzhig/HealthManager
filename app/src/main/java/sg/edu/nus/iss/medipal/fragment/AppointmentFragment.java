package sg.edu.nus.iss.medipal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;

/**
 * Created by : Navi on 06-03-2017.
 * Description : This is the main view for Appointment
 * Modified by :
 * Reason for modification :
 */

public class AppointmentFragment extends Fragment {
    AppBarLayout mAppBarLayout;
    TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.appointment_tabview,container,false);

        tabLayout = (TabLayout) view.findViewById(R.id.appointment_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.appointment_viewpager);
        ((ViewGroup)tabLayout.getParent()).removeView(tabLayout);
        mAppBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appointment_appbar);
        mAppBarLayout.addView(tabLayout,
                new LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter  adapter = new ViewPagerAdapter (getChildFragmentManager());
        adapter.addFragment(new AppointmentsTabFragment(), "Upcoming");
        adapter.addFragment(new AppointmentsTabFragment(), "Past");
        viewPager.setAdapter(adapter);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putInt("position",position);
            Fragment fragment = mFragmentList.get(position);
            fragment.setArguments(fragmentBundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    @Override
    public void onDestroyView() {
        mAppBarLayout.removeView(tabLayout);
        super.onDestroyView();
    }
}
