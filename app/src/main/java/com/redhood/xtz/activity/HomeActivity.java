package com.redhood.xtz.activity;

import android.os.Bundle;
import android.transition.Transition;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.redhood.xtz.R;
import com.redhood.xtz.fragment.HomePageFragment;
import com.redhood.xtz.util.StatusUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView buttom_navigation_bar;
    private HomePageFragment homePageFragment;

    private List<Fragment> fragments = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

    }

    private void init() {
        StatusUtil.makeStatusBarTransparentAndFullContent(this);

        buttom_navigation_bar = findViewById(R.id.buttom_navigation_bar);

        showFragmentPage(homePageFragment,HomePageFragment.class);
        buttom_navigation_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_item_homepage:
                        showFragmentPage(homePageFragment,HomePageFragment.class);
                        break;
                }
                return true;
            }
        });
    }

    private void showFragmentPage(Fragment fragment, Class<? extends Fragment> fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transition = fragmentManager.beginTransaction();
        if (fragment == null) {
            try {
                homePageFragment = (HomePageFragment) fragmentClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            transition.add(R.id.flayout_4homechange, homePageFragment);
        }
        transition.show(homePageFragment);
        transition.commit();
    }


}
