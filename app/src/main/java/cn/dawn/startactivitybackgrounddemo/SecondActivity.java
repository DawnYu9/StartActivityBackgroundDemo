package cn.dawn.startactivitybackgrounddemo;

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
public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "test-SecondActivity";

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}