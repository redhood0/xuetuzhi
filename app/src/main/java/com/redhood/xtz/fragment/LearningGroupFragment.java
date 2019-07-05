package com.redhood.xtz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redhood.xtz.R;
import com.redhood.xtz.adapter.LearningGroupAdapter;
import com.redhood.xtz.bean.LearningGroupBean;
import com.redhood.xtz.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearningGroupFragment extends Fragment {
    private TextView tv_home_title;
    private ImageView iv_home_add_icon;
    private RecyclerView recycleview_learning_group;
    List<LearningGroupBean> data = new ArrayList<>(32);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_group, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        tv_home_title = getActivity().findViewById(R.id.tv_home_title);
        iv_home_add_icon = getActivity().findViewById(R.id.iv_home_add_icon);
        recycleview_learning_group = view.findViewById(R.id.recycleview_learning_group);

        initRecycleView();
        changeTitleBar();
    }

    private void initRecycleView() {
        getData();

        LearningGroupAdapter learningGroupAdapter = new LearningGroupAdapter(getContext(), data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recycleview_learning_group.setLayoutManager(manager);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_learning_group));
        recycleview_learning_group.addItemDecoration(divider);

        recycleview_learning_group.setAdapter(learningGroupAdapter);
        recycleview_learning_group.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                learningGroupAdapter.setLoadState(learningGroupAdapter.LOADING);

                if (data.size() < 10) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getData();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    learningGroupAdapter.setLoadState(learningGroupAdapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    }.start();
                } else {
                    // 显示加载到底的提示
                    learningGroupAdapter.setLoadState(learningGroupAdapter.LOADING_END);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
        tv_home_title.setText("学习交流圈");
        iv_home_add_icon.setVisibility(View.VISIBLE);
    }

    private void getData() {
        for (int i = 0; i < 4; i++) {
            List<String> urls = new ArrayList<>();
            for (int l = 0; l < i % 4; l++) {
                urls.add(" ");
            }
            data.add(new LearningGroupBean("", "王老吉" + i
                    , "感觉自己每天都在进步，进了企业学到好多东西，这是在学校完全学不到的！", urls, "09/12 18:24"));
        }
        return;
    }
}
