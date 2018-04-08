package cn.dawn.startactivitybackgrounddemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * desc   :
 * version:
 * date   : 2018/4/8
 * author : DawnYu
 * GitHub : DawnYu9
 */
public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "test-FirstActivity";
    private CountDownTimer countDownTimer;
    private boolean ifStartSecondActivity;
    private Button btn_no_handle;
    private Button btn_handle_in_first_activity;
    private Button btn_handle_in_second_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btn_no_handle = findViewById(R.id.btn_no_handle);
        btn_handle_in_first_activity = findViewById(R.id.btn_handle_in_first_activity);
        btn_handle_in_second_activity = findViewById(R.id.btn_handle_in_second_activity);

        btn_no_handle.setOnClickListener(onClick);

        btn_handle_in_first_activity.setOnClickListener(onClick);

        btn_handle_in_second_activity.setOnClickListener(onClick);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        if (ifStartSecondActivity) {
            Log.d(TAG, "startActivity - handleInFirstActivity - onResume");

            startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                    .putExtra("tag", "handleInFirstActivity - onResume"));
            ifStartSecondActivity = false;
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            cancelCountdown();

            switch (v.getId()) {
                case R.id.btn_no_handle:
                    handleBtnNothing();
                    break;
                case R.id.btn_handle_in_first_activity:
                    handleBtnInFirstActivity();
                    break;
                case R.id.btn_handle_in_second_activity:
                    handleBtnInSecondActivity();
                    break;
            }
        }
    };

    private void handleBtnNothing() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_no_handle.setText("noHandle：" + Math.round((double) millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "startActivity - noHandle - isAppOnForeground - " + isAppOnForeground());

                startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                        .putExtra("tag", "noHandle - isAppOnForeground - " + isAppOnForeground()));
            }
        }.start();
    }

    private void handleBtnInFirstActivity() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_handle_in_first_activity.setText("handleInFirstActivity：" + Math.round((double) millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                handleInFirstActivity();
            }
        }.start();
    }

    private void handleInFirstActivity() {
        if (isAppOnForeground()) {
            Log.d(TAG, "startActivity - handleInFirstActivity - isAppOnForeground - " + isAppOnForeground());

            startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                    .putExtra("tag", "handleInFirstActivity - isAppOnForeground - " + isAppOnForeground()));
        } else {
            ifStartSecondActivity = true;
        }
    }

    private void handleBtnInSecondActivity() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_handle_in_second_activity.setText("handleInSecondActivity：" + Math.round((double) millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                handleInSecondActivity();
            }
        }.start();
    }

    private void handleInSecondActivity() {
        Log.d(TAG, "startActivity - handleInSecondActivity - isAppOnForeground - " + isAppOnForeground());

        startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                .putExtra("moveTaskToBack", !isAppOnForeground())
                .putExtra("tag", "handleInSecondActivity - isAppOnForeground - " + isAppOnForeground()));
    }

    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
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

        cancelCountdown();
    }
}
