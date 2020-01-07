package com.wood.commonlib.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.wood.commonlib.R;
import com.wood.commonlib.constants.ComConst;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private String[] mPermissions;
    private String test;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissions = getIntent().getStringArrayExtra(ComConst.KEY_PERMISSION);
        if (mPermissions == null || mPermissions.length == 0) {
            finish();
            return;
        }
        boolean granted = PermissionUtils.isGranted(mPermissions);
        if (granted) {
            finish();
            return;
        }

        setContentView(R.layout.activity_permission);

    }

    @SuppressLint("WrongConstant")
    public void requestPermission() {
        boolean granted = PermissionUtils.isGranted(mPermissions);
        if (granted) {
            finish();
            return;
        }
        PermissionUtils.permission(mPermissions)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.i("通过的权限：" + permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        LogUtils.i("拒绝的权限：" + permissionsDenied);
                        LogUtils.i("不在询问的权限：" + permissionsDeniedForever);
                    }
                }).request();
    }

    /**
     * 申请修改设置权限
     */
    public void requestWriteSettings() {
        PermissionUtils.requestWriteSettings(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        });
    }

    /**
     * 申请悬浮窗权限
     */
    public void requestDrawOverlays() {
        PermissionUtils.requestDrawOverlays(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        });
    }

}
