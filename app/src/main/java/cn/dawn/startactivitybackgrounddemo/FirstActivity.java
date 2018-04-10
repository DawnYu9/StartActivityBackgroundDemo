package cn.dawn.startactivitybackgrounddemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class FirstActivity extends BaseActivity {
    public static final int MILLIS_IN_FUTURE = 3 * 1000;
    public static final int COUNT_DOWN_INTERVAL = 1000;
    private CountDownTimer countDownTimer;
    private boolean ifStartSecondActivity;
    private Button btn_show_dialog, btn_no_handle, btn_handle_in_second_activity_on_pause,
            btn_handle_in_second_activity_on_stop, btn_handle_in_second_activity_app_foreground, btn_handle_in_first_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btn_show_dialog = findViewById(R.id.btn_show_dialog);
        btn_no_handle = findViewById(R.id.btn_no_handle);
        btn_handle_in_second_activity_on_pause = findViewById(R.id.btn_handle_in_second_activity_on_pause);
        btn_handle_in_second_activity_on_stop = findViewById(R.id.btn_handle_in_second_activity_on_stop);
        btn_handle_in_second_activity_app_foreground = findViewById(R.id.btn_handle_in_second_activity_app_foreground);
        btn_handle_in_first_activity = findViewById(R.id.btn_handle_in_first_activity);

        btn_show_dialog.setOnClickListener(onClick);
        btn_no_handle.setOnClickListener(onClick);
        btn_handle_in_second_activity_on_pause.setOnClickListener(onClick);
        btn_handle_in_second_activity_on_stop.setOnClickListener(onClick);
        btn_handle_in_second_activity_app_foreground.setOnClickListener(onClick);
        btn_handle_in_first_activity.setOnClickListener(onClick);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ifStartSecondActivity) {
            startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                    .putExtra("tag", "handleInFirstActivity - onResume"));
            ifStartSecondActivity = false;
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btn_show_dialog:
                    startActivity(new Intent(FirstActivity.this, DialogActivity.class));
                    break;
                default:
                    cancelCountdown();
                    countDownTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            String seconds = "：" + Math.round((double) millisUntilFinished / 1000) + "s";
                            switch (v.getId()) {
                                case R.id.btn_no_handle:
                                    btn_no_handle.setText(getResources().getString(R.string.no_handle) + seconds);
                                    break;
                                case R.id.btn_handle_in_second_activity_on_pause:
                                    btn_handle_in_second_activity_on_pause.setText(getResources().getString(R.string.handle_in_second_activity_on_pause) + seconds);
                                    break;
                                case R.id.btn_handle_in_second_activity_on_stop:
                                    btn_handle_in_second_activity_on_stop.setText(getResources().getString(R.string.handle_in_second_activity_on_stop) + seconds);
                                    break;
                                case R.id.btn_handle_in_second_activity_app_foreground:
                                    btn_handle_in_second_activity_app_foreground.setText(getResources().getString(R.string.handle_in_second_activity_app_foreground) + seconds);
                                    break;
                                case R.id.btn_handle_in_first_activity:
                                    btn_handle_in_first_activity.setText(getResources().getString(R.string.handle_in_first_activity_on_resume) + seconds);
                                    break;
                            }
                        }

                        @Override
                        public void onFinish() {
                            switch (v.getId()) {
                                case R.id.btn_no_handle:
                                    btn_no_handle.setText(getResources().getString(R.string.no_handle));

                                    startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                                            .putExtra("tag", "noHandle - isAppOnForeground - " + isAppOnForeground()));
                                    break;
                                case R.id.btn_handle_in_second_activity_on_pause:
                                    btn_handle_in_second_activity_on_pause.setText(getResources().getString(R.string.handle_in_second_activity_on_pause));

                                    startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                                            .putExtra("tag", "handleInSecondActivity - onPause - " + isOnPaused)
                                            .putExtra("moveTaskToBack", isOnPaused)
                                    );
                                    break;
                                case R.id.btn_handle_in_second_activity_on_stop:
                                    btn_handle_in_second_activity_on_stop.setText(getResources().getString(R.string.handle_in_second_activity_on_stop));

                                    startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                                            .putExtra("tag", "handleInSecondActivity - isVisible - " + isVisible)
                                            .putExtra("moveTaskToBack", !isVisible)
                                    );
                                    break;
                                case R.id.btn_handle_in_second_activity_app_foreground:
                                    btn_handle_in_second_activity_app_foreground.setText(getResources().getString(R.string.handle_in_second_activity_app_foreground));

                                    startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                                            .putExtra("moveTaskToBack", !isAppOnForeground())
                                            .putExtra("tag", "handleInSecondActivity - isAppOnForeground - " + isAppOnForeground()));
                                    break;
                                case R.id.btn_handle_in_first_activity:
                                    btn_handle_in_first_activity.setText(getResources().getString(R.string.handle_in_first_activity_on_resume));

                                    if (isAppOnForeground()) {
                                        startActivity(new Intent(FirstActivity.this, SecondActivity.class)
                                                .putExtra("tag", "handleInFirstActivity - isAppOnForeground - " + isAppOnForeground()));
                                    } else {
                                        ifStartSecondActivity = true;
                                    }
                                    break;
                            }
                        }
                    }.start();
                    break;
            }
        }
    };

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
    protected void onDestroy() {
        super.onDestroy();

        cancelCountdown();
    }
}
