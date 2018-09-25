package com.xufeng.mvpkotlin.app;
/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-9-21, xufeng, Create file
 */

import android.view.View;
import android.widget.ImageView;

/**
 * 用于java语法测试 ，方便和Kotlin进行对比
 */
public class text {

    ImageView mImageView;

    public void text1() {
        mImageView.setOnClickListener(v -> text2());
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void text2() {
    }
}
