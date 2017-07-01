package com.wjf.demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class PermissionsDispatcherActivity extends AppCompatActivity implements View.OnClickListener {
    private StringBuffer logStringBuffer = new StringBuffer();
    Button btn_check, btn_getSingle, btn_getMulti;
    TextView tv_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_getSingle = (Button) findViewById(R.id.btn_getSingle);
        btn_getMulti = (Button) findViewById(R.id.btn_getMulti);
        tv_log = (TextView) findViewById(R.id.tv_log);

        btn_check.setOnClickListener(this);
        btn_getSingle.setOnClickListener(this);
        btn_getMulti.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getSingle:
                //申请单个权限
                PermissionsDispatcherActivityPermissionsDispatcher.cam1WithCheck(this);

                break;
            case R.id.btn_getMulti:
                //申请多个权限
                PermissionsDispatcherActivityPermissionsDispatcher.mutiWithCheck(this);
                break;
        }

    }


    @NeedsPermission(Manifest.permission.CAMERA)
    void cam1() {
        Toast.makeText(this, "已获取单个权限，让我们干爱干的事吧！", Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void muti() {
        Toast.makeText(this, "已获取多个权限，让我们干爱干的事吧！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcherActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void cam1Show(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要CAMERA，下一步将继续请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        }).show();
    }


    @OnShowRationale({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void mutiShow(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限，下一步将继续请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})//一旦用户拒绝了
    public void multiDenied() {
        Toast.makeText(this, "已拒绝一个或以上权限", Toast.LENGTH_SHORT).show();
    }


    @OnPermissionDenied(Manifest.permission.CAMERA)//一旦用户拒绝了
    public void StorageDenied() {
        Toast.makeText(this, "已拒绝CAMERA权限", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})//用户选择的不再询问
    public void multiNeverAsk() {
        Toast.makeText(this, "已拒绝一个或以上权限，并不再询问", Toast.LENGTH_SHORT).show();
    }
    @OnNeverAskAgain(Manifest.permission.CAMERA)//用户选择的不再询问
    public void StorageNeverAsk() {
        Toast.makeText(this, "已拒绝CAMERA权限，并不再询问", Toast.LENGTH_SHORT).show();
    }

}
