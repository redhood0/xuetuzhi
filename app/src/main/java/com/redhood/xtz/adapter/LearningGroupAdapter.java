package com.redhood.xtz.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.redhood.xtz.R;
import com.redhood.xtz.bean.LearningGroupBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LearningGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 普通布局
    private final int TYPE_ITEM = 0;
    // 脚布局
    private final int TYPE_FOOTER = 1;

    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    private PopupWindow popupWindow;
    private boolean mIsShowing = false;

    Context context;
    List<LearningGroupBean> datas;

    public LearningGroupAdapter(Context context, List<LearningGroupBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_learning_group, parent, false);
            return new MyHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            LearningGroupBean bean = datas.get(position);
            loadingShareImg(myHolder, bean.getPic_url());
            myHolder.item_learning_group_name.setText(bean.getName());
            myHolder.item_learning_group_date.setText(bean.getDate());
            myHolder.item_learning_group_msg.setText(bean.getMsg());

            myHolder.iv_learning_group_button_give_like.setOnClickListener(v -> {
                giveAlike(myHolder);
            });

            myHolder.tv_learning_group_button_forwarding.setOnClickListener(v -> {
                popupShareWindow();
            });

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    footViewHolder.ll_loading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.ll_loading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.ll_loading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView item_learning_group_name;
        TextView item_learning_group_date;
        TextView item_learning_group_msg;
        TextView tv_learning_group_button_forwarding;
        ImageView iv_learning_group_button_give_like;
        ImageView iv_learning_group_pic_one;
        ImageView iv_learning_group_pic_two1;
        ImageView iv_learning_group_pic_two2;
        ImageView iv_learning_group_pic_three1;
        ImageView iv_learning_group_pic_three2;
        ImageView iv_learning_group_pic_three3;
        boolean isAlike = false;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            item_learning_group_name = itemView.findViewById(R.id.item_learning_group_name);
            item_learning_group_date = itemView.findViewById(R.id.item_learning_group_date);
            item_learning_group_msg = itemView.findViewById(R.id.item_learning_group_msg);
            iv_learning_group_button_give_like = itemView.findViewById(R.id.iv_learning_group_button_give_like);
            iv_learning_group_pic_one = itemView.findViewById(R.id.iv_learning_group_pic_one);
            iv_learning_group_pic_two1 = itemView.findViewById(R.id.iv_learning_group_pic_two1);
            iv_learning_group_pic_two2 = itemView.findViewById(R.id.iv_learning_group_pic_two2);
            iv_learning_group_pic_three1 = itemView.findViewById(R.id.iv_learning_group_pic_three1);
            iv_learning_group_pic_three2 = itemView.findViewById(R.id.iv_learning_group_pic_three2);
            iv_learning_group_pic_three3 = itemView.findViewById(R.id.iv_learning_group_pic_three3);
            tv_learning_group_button_forwarding = itemView.findViewById(R.id.tv_learning_group_button_forwarding);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llEnd;
        LinearLayout ll_loading;

        FootViewHolder(View itemView) {
            super(itemView);
            ll_loading = itemView.findViewById(R.id.ll_loading);
            llEnd = itemView.findViewById(R.id.ll_end);
        }
    }

    public void update(List<LearningGroupBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    //下拉更新状态
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据x个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    //点赞功能
    private void giveAlike(MyHolder myHolder) {
        if (!myHolder.isAlike) {
            myHolder.iv_learning_group_button_give_like.setImageResource(R.mipmap.give_like_selected);
            myHolder.isAlike = true;
        } else {
            myHolder.iv_learning_group_button_give_like.setImageResource(R.mipmap.give_like_unselected);
            myHolder.isAlike = false;
        }
    }

    //分享弹窗弹出
    private void popupShareWindow() {
        if (popupWindow == null) {
            initPopup();
        }
        if (!popupWindow.isShowing()) {
            Activity activity = (Activity) context;
            popupWindow.showAtLocation(activity.findViewById(R.id.buttom_navigation_bar), Gravity.BOTTOM, 0, 0);
            showBackgroundAnimator();
            mIsShowing = true;
            popupWindow.setOnDismissListener(() -> {
                setWindowBackgroundAlpha(1.0f);
            });
        }
    }

    private void initPopup() {
        View pop = View.inflate(context, R.layout.popup_share, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        popupWindow.setAnimationStyle(R.style.anim_popup_window);
        mIsShowing = false;

        pop.findViewById(R.id.btn_popup_share_close).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
    }

    private void setWindowBackgroundAlpha(float alpha) {
        if (context == null) return;
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.alpha = alpha;
            window.setAttributes(layoutParams);
        }
    }

    private void showBackgroundAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.6f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(400);
        animator.start();
    }

    //加载分享图片
    private void loadingShareImg(MyHolder myHolder, List<String> urls) {
        switch (urls.size()) {
            case 0:
                myHolder.iv_learning_group_pic_one.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three3.setVisibility(View.GONE);
                break;
            case 1:
                myHolder.iv_learning_group_pic_one.setVisibility(View.VISIBLE);
                myHolder.iv_learning_group_pic_two1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three3.setVisibility(View.GONE);
                break;
            case 2:
                myHolder.iv_learning_group_pic_one.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two1.setVisibility(View.VISIBLE);
                myHolder.iv_learning_group_pic_two2.setVisibility(View.VISIBLE);
                myHolder.iv_learning_group_pic_three1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three3.setVisibility(View.GONE);
                break;
            case 3:
                myHolder.iv_learning_group_pic_one.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two1.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_two2.setVisibility(View.GONE);
                myHolder.iv_learning_group_pic_three1.setVisibility(View.VISIBLE);
                myHolder.iv_learning_group_pic_three2.setVisibility(View.VISIBLE);
                myHolder.iv_learning_group_pic_three3.setVisibility(View.VISIBLE);

                break;
        }
    }
}
