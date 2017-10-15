package com.example.huangming.aboutapi23permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

/**
 * 关于Android 6.0 的权限申请示例
 *
 * @author Huangming 2017/10/15
 */
public class MainActivity extends AppCompatActivity {

    Intent callIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // 根据返回的申请结果进行相应处理

        // 参数说明：
        // permissions是申请的各个权限
        // grantResults是各个权限的申请结果，与permisssions一一对应
        // 如果申请了多个权限，要注意区分每个权限的申请结果
        // 这里只简单处理了一下只申请一个权限的情况
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            } else {
                // 此处一般要提醒用户去打开相应的权限
            }
        }
    }

    private void initView() {
        callIntent =  new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:123"));
        // 打电话权限
        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(callIntent);
                } else {
                    String[] ps = new String[]{Manifest.permission.CALL_PHONE};
                    requestPermissions(ps);
                }
            }
        });

        // 相机权限
        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 有趣的是，调用相机是不需要申请权限的，无法哪个版本都是可以正常调用的
                // 但如果在AndroidManifest.xml中声明了相机权限，就必须做权限判断，
                // 否则在6.0以上的机型运行就会crash
                String state = Environment.getExternalStorageState();
                // 判断sd卡是否可用
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent2, 3);
                }
            }
        });
    }

    /**
     * 判断是否有某个权限
     *
     * @param permission
     * @return
     */
    private boolean hasPermission(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return true;
        }
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限
     *
     * @param permissions
     */
    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, 1);
    }
}
