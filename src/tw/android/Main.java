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
    // ��l����
    private float currentDegree = 0f;
    // �P�����޲z
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

        // ���U�P����ť��
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // ����P����ť��
        mSensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {

        //��oz�b������
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // currentDegree-��l����,-degree�f�ɰw���൲������
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,   //x�y��
                Animation.RELATIVE_TO_SELF, 0.5f);  //y�y��

        // ��ʮɶ�
        ra.setDuration(210);

        // �w�]���A�����᪺�ʧ@�]�w
        ra.setFillAfter(true);

        // �N�ʧ@��J�Ϥ�
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    /*���ܸg�T��*/
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}