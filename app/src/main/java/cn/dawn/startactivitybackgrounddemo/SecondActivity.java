package cn.dawn.startactivitybackgrounddemo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * desc   :
 * version:
 * date   : 2018/4/8
 * author : DawnYu
 * GitHub : DawnYu9
 */
public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate - " + getIntent().getStringExtra("tag"));

        setContentView(R.layout.activity_second);

        TextView tv = findViewById(R.id.tv);

        if (getIntent().getBooleanExtra("moveTaskToBack", false)) {
            moveTaskToBack(true);
            Log.d(TAG, "moveTaskToBack");
        }

        tv.setText(getIntent().getStringExtra("tag"));
    }
}