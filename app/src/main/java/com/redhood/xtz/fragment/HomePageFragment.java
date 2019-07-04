package com.redhood.xtz.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.redhood.xtz.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomePageFragment extends Fragment {
    private TextView tv_home_title;
    private ImageView iv_home_add_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        init(view);
        return view;
    }

    private void init(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        tv_home_title = getActivity().findViewById(R.id.tv_home_title);
        iv_home_add_icon = getActivity().findViewById(R.id.iv_home_add_icon);
        changeTitleBar();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeTitleBar();
        } else {
        }
    }

    private void changeTitleBar() {
        tv_home_title.setText("首页");
        iv_home_add_icon.setVisibility(View.INVISIBLE);
    }
}
