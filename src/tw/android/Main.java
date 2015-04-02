package tw.android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity implements SensorEventListener {

    private ImageView image;
    // 初始角度
    private float currentDegree = 0f;
    // 感應器管理
    private SensorManager mSensorManager;

    private TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        image = (ImageView) findViewById(R.id.imageViewCompass);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 註冊感應監聽器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 停止感應監聽器
        mSensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {

        //獲得z軸的角度
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // currentDegree-初始角度,-degree逆時針旋轉結束角度
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,   //x座標
                Animation.RELATIVE_TO_SELF, 0.5f);  //y座標

        // 轉動時間
        ra.setDuration(210);

        // 預設狀態結束後的動作設定
        ra.setFillAfter(true);

        // 將動作放入圖片
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    /*改變經確度*/
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}