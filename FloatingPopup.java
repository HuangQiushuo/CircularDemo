package com.getbase.floatingactionbutton;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by Qiushuo Huang on 2017/3/30.
 */

public class FloatingPopup extends PopupWindow {
    RelativeLayout contentView;
    ITimeFloatingMenu menu;
    Context context;
    public FloatingPopup(Context context){
        super(context);
        this.context = context;
        contentView = new RelativeLayout(context);
        contentView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setDrawingCacheBackgroundColor(context.getResources().getColor(R.color.transparent));

        this.setContentView(contentView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void setMenu(final ITimeFloatingMenu menu){
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.addView(menu);
        this.menu = menu;
        contentView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int top = v.getTop();
                int bottom = v.getRight();
                int left = v.getLeft();
                int right = v.getRight();
                int y=(int) event.getY();
                int x=(int) event.getX();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(x<left || x>right || y<bottom|| y>top){
                        menu.collapse();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y){
        super.showAtLocation(parent,gravity,x,y);
        while(!this.isShowing()){

        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) menu.getLayoutParams();
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        parent.getLocationOnScreen(location1);

        int i = context.getResources().getDisplayMetrics().heightPixels;
        params.setMargins(0,0,0,i-location1[1]-parent.getHeight());
        menu.setLayoutParams(params);
        menu.expand();
    }
}
