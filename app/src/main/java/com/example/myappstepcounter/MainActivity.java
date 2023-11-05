package com.example.myappstepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textViewStepCounter,textViewStepDerector;
    private SensorManager sensorManager;
    private Sensor mStepCounter,mStepDerector;
    private boolean isCounterSensorPresent,isDerectorSensorPresent;

    int stepCount = 0, stepDerector = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        textViewStepCounter = findViewById(R.id.textViewStepCounter);
        textViewStepDerector = findViewById(R.id.textViewStepDerector);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }else{
            textViewStepCounter.setText("Counter Sensor is  not Present");
            isCounterSensorPresent = false;
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null){
            mStepDerector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDerectorSensorPresent = true;
        }else{
            textViewStepDerector.setText("Derector Sensor  is not Present");
            isDerectorSensorPresent= false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == mStepCounter){
            stepCount =(int) sensorEvent.values[0];
            textViewStepCounter.setText(String.valueOf(stepCount));
        } else if (sensorEvent.sensor ==mStepDerector) {
            stepDerector = (int) (stepDerector+sensorEvent.values[0]);
            textViewStepDerector.setText(String.valueOf(stepDerector));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!= null){
            sensorManager.registerListener(this,mStepDerector,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.unregisterListener(this,mStepCounter);
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null){
            sensorManager.unregisterListener(this,mStepDerector);
        }
    }
}