package com.wjf.demo;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    private static StringBuffer logStringBuffer = new StringBuffer();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //使用EasyPermissions查看权限是否已申请
    public String easyCheckPermissions(Context context, String... permissions) {
        logStringBuffer.delete(0, logStringBuffer.length());
        for (String permission : permissions) {
            logStringBuffer.append(permission);
            logStringBuffer.append(" is applied? \n     ");
            logStringBuffer.append(EasyPermissions.hasPermissions(context, permission));
            logStringBuffer.append("\n\n");
        }
        return logStringBuffer.toString();
    }

    @AfterPermissionGranted(0)
    private void afterGet() {
        Toast.makeText(this, "已获取单个权限，让我们干爱干的事吧！", Toast.LENGTH_SHORT).show();
    }

    @AfterPermissionGranted(1)
    private void afterGet1() {
        Toast.makeText(this, "已获取多个权限，让我们干爱干的事吧！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                String str = easyCheckPermissions(this,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                tv_log.setText(str);
                break;
            case R.id.btn_getSingle:
                EasyPermissions.requestPermissions(this,
                        "接下来需要获取CAMERA权限",
                        R.string.yes,
                        R.string.no,
                        0,
                        Manifest.permission.CAMERA);
                break;
            case R.id.btn_getMulti:
                EasyPermissions.requestPermissions(this,
                        "接下来需要获取WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限",
                        R.string.yes,
                        R.string.no,
                        1,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO);
                break;

        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("已获取");
        for (String s : perms) {
            stringBuffer.append(s).append(",");
        }
        stringBuffer.append("权限");

        switch (requestCode) {
            case 0:
                Toast.makeText(this, stringBuffer, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, stringBuffer, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //处理权限名字字符串
        StringBuffer sb = new StringBuffer();
        for (String str : perms) {
            sb.append(str);
            sb.append("\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "");

        switch (requestCode) {
            case 0:
                Toast.makeText(this, "已拒绝权限" + sb, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "已拒绝" + sb + "权限", Toast.LENGTH_SHORT).show();
                break;
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show();
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show();
        }
    }
}
