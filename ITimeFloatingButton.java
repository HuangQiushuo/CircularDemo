package com.getbase.floatingactionbutton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiushuo Huang on 2017/3/30.
 */

public class ITimeFloatingButton extends AddFloatingActionButton{

    private ITimeFloatingMenu menu;
    private List<Item> items = new ArrayList<>();
    private List<View> itemViews = new ArrayList<>();
    private FloatingPopup dialog;
    private Display display;
    private View parentView;

    public ITimeFloatingButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ITimeFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ITimeFloatingButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        items = new ArrayList<>();
        menu = new ITimeFloatingMenu(context, attrs, defStyle);

        initMenu();
        initDialog();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.showAtLocation(parentView, Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    public void setParent(View view){
        parentView = view;
    }

    public void setItems(List<Item> list){
        if(items!=list){
            for(View item:itemViews){
                menu.removeItem(item);
            }
            items = list;
            itemViews.clear();
            for(Item item: items){
                generateView(item);
            }
        }
    }

    public void initMenu(){
        menu.initAddButton(getIconDrawable(), getPlusColor(), getColorNormal(), getColorPressed(),getSize(), false);
        menu.setBackgroundColor(getResources().getColor(R.color.transparent));
        menu.setOnFloatingActionsMenuUpdateListener(new ITimeFloatingMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {

            }

            @Override
            public void onMenuCollapsed() {
                dialog.dismiss();
            }
        });
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        menu.setLayoutParams(params);
    }


    public void initDialog(){
        dialog = new FloatingPopup(getContext());
        dialog.setMenu(menu);

    }

    public ITimeFloatingButton addItem(Item item){
        items.add(item);
        generateView(item);
        return this;
    }

    private void generateView(final Item item){
        TextView textView = new TextView(getContext());
        textView.setText(item.getText());
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLACK);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.listener!=null) {
                    item.getListener().onClick(view);
                }
                menu.collapseImmediately();
                ITimeFloatingButton.this.performClick();
            }
        });
        itemViews.add(textView);
        menu.addItem(textView);
    }

    public static class Item{
        private Drawable icon;
        private String text;
        private OnClickListener listener;

        public Item(Drawable icon, String text, OnClickListener listener) {
            this.icon = icon;
            this.text = text;
            this.listener = listener;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public OnClickListener getListener() {
            return listener;
        }

        public void setListener(OnClickListener listener) {
            this.listener = listener;
        }
    }

    private int dp2px(double dipValue){
        float scale=getContext().getResources().getDisplayMetrics().densityDpi;
        return (int)(dipValue*(scale/160)+0.5f);
    }

    public int px2dp( double pxValue){
        float scale = getContext().getResources().getDisplayMetrics().densityDpi;
        return (int)((pxValue*160)/scale+0.5f);
    }
}
