package com.susyimes.free.view2view;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends Activity implements View.OnTouchListener {

    private final int MATCH_PARENT = -1;
    private ConstraintLayout layout_parent, layout_main;
    private ImageView img_main, img_domain;
    private int img_main_w = 328, img_main_h = 172, img_domain_w = 16, img_domain_h = 16;
    private float img_main_pre_x = 0, img_main_pre_y = 0, img_domain_pre_x = 0, img_domain_pre_y = 0;
    private TextView text_main, text_desc, text_action;
    private int text_main_w = 328, text_main_h = 42, text_desc_w = 178, text_desc_h = 15, text_action_w = 88, text_action_h = 32;
    private float text_main_pre_x = 0, text_main_pre_y = 0, text_desc_pre_x = 0, text_desc_pre_y = 0, text_action_pre_x = 0, text_action_pre_y = 0;
    private ConstraintLayout.LayoutParams img_main_params, img_domain_params, text_main_params, text_desc_params, text_action_params;
    private EditText redefine_x,redefine_y;
    private Button btn_edit;


    private ConstraintSet constraintSetParent,constraintSetGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        restoreLayout();
        setDragAction();
        initData();
        setEditAction();

    }



    private void findId() {
        layout_parent = findViewById(R.id.layout_parent);
        layout_main = findViewById(R.id.layout_main);

        text_main = findViewById(R.id.text_main);
        img_main = findViewById(R.id.img_main);
        img_domain = findViewById(R.id.img_domain);
        text_desc = findViewById(R.id.text_desc);
        text_action = findViewById(R.id.text_action);


        redefine_x=findViewById(R.id.redefine_x);
        redefine_y=findViewById(R.id.redefine_y);
        btn_edit=findViewById(R.id.btn_edit);
    }

    private void restoreLayout() {
        constraintSetParent = new ConstraintSet();
        constraintSetParent.clone(layout_parent);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        constraintSetParent.constrainWidth(R.id.layout_main, width);
        constraintSetParent.constrainHeight(R.id.layout_main, Dp2px.UseDp2Px(this, 288));
        constraintSetParent.applyTo(layout_parent);

        constraintSetGlobal = new ConstraintSet();
        constraintSetGlobal.clone(layout_main);
        constraintSetGlobal.constrainWidth(R.id.text_main, Dp2px.UseDp2Px(this, text_main_w));
        constraintSetGlobal.constrainHeight(R.id.text_main, Dp2px.UseDp2Px(this, text_main_h));
        constraintSetGlobal.constrainWidth(R.id.img_main, Dp2px.UseDp2Px(this, img_main_w));
        constraintSetGlobal.constrainHeight(R.id.img_main, Dp2px.UseDp2Px(this, img_main_h));
        constraintSetGlobal.constrainWidth(R.id.img_domain, Dp2px.UseDp2Px(this, img_domain_w));
        constraintSetGlobal.constrainHeight(R.id.img_domain, Dp2px.UseDp2Px(this, img_domain_h));
        constraintSetGlobal.constrainWidth(R.id.text_desc, Dp2px.UseDp2Px(this, text_desc_w));
        constraintSetGlobal.constrainHeight(R.id.text_desc, Dp2px.UseDp2Px(this, text_desc_h));
        constraintSetGlobal.constrainWidth(R.id.text_action, Dp2px.UseDp2Px(this, text_action_w));
        constraintSetGlobal.constrainHeight(R.id.text_action, Dp2px.UseDp2Px(this, text_action_h));
        constraintSetGlobal.applyTo(layout_main);

    }

    private void setEditAction() {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintSetParent.constrainHeight(R.id.layout_main, Dp2px.UseDp2Px(getBaseContext(), Float.parseFloat(redefine_y.getText().toString())));
                constraintSetParent.constrainWidth(R.id.layout_main, Dp2px.UseDp2Px(getBaseContext(), Float.parseFloat(redefine_x.getText().toString())));
                constraintSetParent.applyTo(layout_parent);

            }
        });

    }



    private void setDragAction() {
        text_main.setOnTouchListener(this);
        img_main.setOnTouchListener(this);
        img_domain.setOnTouchListener(this);
        text_desc.setOnTouchListener(this);
        text_action.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.text_main:
                text_main_params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                float x1 = event.getRawX();
                float y1 = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        text_main_pre_x = x1;
                        text_main_pre_y = y1;

                        break;


                    case MotionEvent.ACTION_MOVE:

                        float horizontalDisplacement = x1 - text_main_pre_x;
                        float verticalDisplacement = y1 - text_main_pre_y;
                        text_main_pre_x = x1;
                        text_main_pre_y = y1;

                        text_main_params.horizontalBias += horizontalDisplacement / layout_main.getWidth();
                        if (text_main_params.horizontalBias < 0) {
                            text_main_params.horizontalBias = 0;
                        }
                        text_main_params.verticalBias += verticalDisplacement / layout_main.getHeight();
                        if (text_main_params.verticalBias < 0) {
                            text_main_params.verticalBias = 0;
                        }
                        v.setLayoutParams(text_main_params);
                }

                break;
            case R.id.img_main:
                img_main_params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                float x2 = event.getRawX();
                float y2 = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        img_main_pre_x = x2;
                        img_main_pre_y = y2;

                        break;


                    case MotionEvent.ACTION_MOVE:

                        float horizontalDisplacement = x2 - img_main_pre_x;
                        float verticalDisplacement = y2 - img_main_pre_y;
                        img_main_pre_x = x2;
                        img_main_pre_y = y2;

                        img_main_params.horizontalBias += horizontalDisplacement / layout_main.getWidth();
                        if (img_main_params.horizontalBias < 0) {
                            img_main_params.horizontalBias = 0;
                        }
                        img_main_params.verticalBias += verticalDisplacement / layout_main.getHeight();
                        if (img_main_params.verticalBias < 0) {
                            img_main_params.verticalBias = 0;
                        }
                        v.setLayoutParams(img_main_params);
                }


                break;
            case R.id.img_domain:

                img_domain_params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                float x3 = event.getRawX();
                float y3 = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        img_domain_pre_x = x3;
                        img_domain_pre_y = y3;

                        break;


                    case MotionEvent.ACTION_MOVE:

                        float horizontalDisplacement = x3 - img_domain_pre_x;
                        float verticalDisplacement = y3 - img_domain_pre_y;
                        img_domain_pre_x = x3;
                        img_domain_pre_y = y3;

                        img_domain_params.horizontalBias += horizontalDisplacement / layout_main.getWidth();
                        if (img_domain_params.horizontalBias < 0) {
                            img_domain_params.horizontalBias = 0;
                        }
                        img_domain_params.verticalBias += verticalDisplacement / layout_main.getHeight();
                        if (img_domain_params.verticalBias < 0) {
                            img_domain_params.verticalBias = 0;
                        }
                        v.setLayoutParams(img_domain_params);
                }

                break;
            case R.id.text_desc:
                text_desc_params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                float x4 = event.getRawX();
                float y4 = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        text_desc_pre_x = x4;
                        text_desc_pre_y = y4;

                        break;


                    case MotionEvent.ACTION_MOVE:

                        float horizontalDisplacement = x4 - text_desc_pre_x;
                        float verticalDisplacement = y4 - text_desc_pre_y;
                        text_desc_pre_x = x4;
                        text_desc_pre_y = y4;

                        text_desc_params.horizontalBias += horizontalDisplacement / layout_main.getWidth();
                        if (text_desc_params.horizontalBias < 0) {
                            text_desc_params.horizontalBias = 0;
                        }
                        text_desc_params.verticalBias += verticalDisplacement / layout_main.getHeight();
                        if (text_desc_params.verticalBias < 0) {
                            text_desc_params.verticalBias = 0;
                        }
                        v.setLayoutParams(text_desc_params);
                }
                break;
            case R.id.text_action:
                text_action_params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
                float x5 = event.getRawX();
                float y5 = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        text_action_pre_x = x5;
                        text_action_pre_y = y5;

                        break;


                    case MotionEvent.ACTION_MOVE:

                        float horizontalDisplacement = x5 - text_action_pre_x;
                        float verticalDisplacement = y5 - text_action_pre_y;
                        text_action_pre_x = x5;
                        text_action_pre_y = y5;

                        text_action_params.horizontalBias += horizontalDisplacement / layout_main.getWidth();
                        if (text_action_params.horizontalBias < 0) {
                            text_action_params.horizontalBias = 0;
                        }
                        text_action_params.verticalBias += verticalDisplacement / layout_main.getHeight();
                        if (text_action_params.verticalBias < 0) {
                            text_action_params.verticalBias = 0;
                        }
                        v.setLayoutParams(text_action_params);
                }
                break;
        }

        return true;
    }


    private void initData() {

        Glide.with(this).load(R.mipmap.logonew).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(img_domain);
        Glide.with(this).load(R.mipmap.main).into(img_main);

    }

}
