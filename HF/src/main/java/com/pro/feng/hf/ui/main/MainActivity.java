package com.pro.feng.hf.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.pro.feng.hf.R;
import com.pro.feng.hf.contact.interfaces.main.MainContact;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseAppCompactActivity;
import com.pro.feng.hf.contact.presenter.main.MainPresenter;
import com.pro.feng.hf.ui.index.HomeFragment;
import com.pro.feng.hf.ui.mine.MineFragment;
import com.pro.feng.hf.ui.movie.MovieFragment;
import me.yokeyword.fragmentation.SupportFragment;

@CreatePresenter(MainPresenter.class)
public class MainActivity extends BaseAppCompactActivity<MainContact, MainPresenter> implements MainContact {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    private SupportFragment[] mFragments = new SupportFragment[3];

    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showHideFragment(mFragments[FIRST]);
                    return true;
                case R.id.navigation_dashboard:
                    showHideFragment(mFragments[SECOND]);
                    return true;
                case R.id.navigation_notifications:
                    showHideFragment(mFragments[THIRD]);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = MovieFragment.newInstance();
            mFragments[THIRD] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(MovieFragment.class);
            mFragments[THIRD] = findFragment(MineFragment.class);
        }
    }
}
