package cn.dawn.startactivitybackgrounddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * desc   :
 * version:
 * date   : 2018/4/9
 * author : DawnYu
 * GitHub : DawnYu9
 */
public class BaseActivity extends AppCompatActivity {
    protected final String TAG = "test-" + getClass().getSimpleName();
    protected boolean isOnPaused, isVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        isVisible = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        isOnPaused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

        isOnPaused = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        isVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
