package com.mongooze.ui.landing.intro;

import android.content.Intent;
import android.os.Bundle;

import com.jachdev.commonlibs.widget.CustomButton;
import com.mongooze.R;
import com.mongooze.base.SessionManager;
import com.mongooze.ui.main.MainActivity;
import com.mongooze.ui.meeting.MeetingActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

public class IntroActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;

    @BindView(R.id.pager)
    ViewPager2 viewPager;
    @BindView(R.id.btnPrevious)
    CustomButton btnPrevious;
    @BindView(R.id.btnNext)
    CustomButton btnNext;
    @BindView(R.id.indicator)
    CircleIndicator3 indicator;

    private ScreenSlidePagerAdapter pagerAdapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);

        pagerAdapter = new ScreenSlidePagerAdapter(this);
        sessionManager = new SessionManager(this);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewPager.registerOnPageChangeCallback(mPageChanger);
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewPager.unregisterOnPageChangeCallback(mPageChanger);
    }

    @OnClick(R.id.btnNext)
    public void OnNextClick(){
        if(pagerAdapter.getItemCount() > viewPager.getCurrentItem()+1){
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }else if(viewPager.getCurrentItem() == pagerAdapter.getItemCount()-1){
            startActivity();
        }
    }

    @OnClick(R.id.btnPrevious)
    public void OnPreviousClick(){
        if(0 < viewPager.getCurrentItem())
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    @OnClick(R.id.tvSkip)
    public void OnSkipClick(){
        startActivity();
    }

    private void startActivity() {
        IntroActivity.this.finish();
        sessionManager.setIsNewUser();

        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
    }

    private ViewPager2.OnPageChangeCallback mPageChanger = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            btnNext.setText(getString(R.string.next));

            if(position == 0){
                btnPrevious.setTextColor(ContextCompat.getColor(IntroActivity.this, R.color.whiteFourteen));
                btnPrevious.setEnabled(false);
            }else if(position == pagerAdapter.getItemCount()-1){
                btnNext.setText(getString(R.string.lets_start));
            }else{
                btnPrevious.setEnabled(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return IntroFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
