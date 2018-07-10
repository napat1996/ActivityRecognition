package info.androidhive.activityrecognition;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.DetectedActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import info.androidhive.activityrecognition.BackgroundDetectedActivitiesService;
import info.androidhive.activityrecognition.Constants;
import info.androidhive.activityrecognition.R;

public class MainActivity extends AppCompatActivity{

    private String TAG = MainActivity.class.getSimpleName();
    BroadcastReceiver broadcastReceiver;

    private TextView txtActivity, txtConfidence, txtTime;
    private ImageView imgActivity;
    private Button btnStartTrcking, btnStopTracking, btnSendData;
    private DatabaseReference mDatabase, mRootref;
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp start = new java.sql.Timestamp(now.getTime());
    java.sql.Timestamp end = new java.sql.Timestamp(now.getTime());
    private String startTime = start.toString() ;
    private String endTime = startTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mRootref = FirebaseDatabase.getInstance().getReference();
        mRootref.child("User").removeValue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtActivity = findViewById(R.id.txt_activity);
        txtConfidence = findViewById(R.id.txt_confidence);
        txtTime = findViewById(R.id.txt_Time);
        imgActivity = findViewById(R.id.img_activity);

//        btnSendData = (Button) findViewById(R.id.btn_send_data);
         btnStartTrcking = findViewById(R.id.btn_start_tracking);
         btnStopTracking = findViewById(R.id.btn_stop_tracking);

//        btnSendData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                java.util.Date now = calendar.getTime();
//                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
//
//                String strTime = currentTimestamp.toString();
//
//           mDatabase.child(strTime).setValue(label);
//                DatabaseReference childRef = mRootref.child("Time");
//                childRef.push().setValue(strTime);
//            }
//        });
//
        btnStartTrcking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
                Log.e(TAG, "onClick: .....START TRACKING.....");
            }
        });

        btnStopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking();
                Log.e(TAG, "onClick: .....STOP TRACKING.....");
            }
        });

        startTracking();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };

        startTracking();
    }

    String newLabel = "";
    int count1 = 0,count2 = 0,count3 = 0,count4 = 0,count5 = 0,count6 = 0,count7 = 0,count8 = 0,count = 0;

    private void handleUserActivity(int type, int confidence) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        mRootref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimeactivity-3a959.firebaseio.com/User");

        String strTime = currentTimestamp.toString();
        startTime = strTime;
        String label = getString(R.string.activity_unknown);

        DatabaseReference testDB = mRootref.child("FUCKKKK");
        testDB.child("FFFFFFFF").setValue("UUUU");

        int icon = R.drawable.ic_still;

            switch (type) {
                case DetectedActivity.IN_VEHICLE: {
                    label = getString(R.string.activity_in_vehicle);
                    icon = R.drawable.ic_driving;
                    count1 = count;
                    count1++;
                    count = count1;
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    label = getString(R.string.activity_on_bicycle);
                    icon = R.drawable.ic_on_bicycle;
                    count2 = count;
                    count2++;
                    count = count2;
                    break;
                }
//            case DetectedActivity.ON_FOOT: {
//                label = getString(R.string.activity_on_foot);
//                icon = R.drawable.ic_walking;
//                count3++;
//                count = count3;
//                break;
//            }
                case DetectedActivity.RUNNING: {
                    label = getString(R.string.activity_running);
                    icon = R.drawable.ic_running;
                    count4 = count;
                    count4++;
                    count = count4;
                    break;
                }
                case DetectedActivity.STILL: {
                    label = getString(R.string.activity_still);
                    icon = R.drawable.ic_still;
                    count5 = count;
                    count5++;
                    count = count5;
                    break;
                }
                case DetectedActivity.TILTING: {
                    label = getString(R.string.activity_tilting);
                    icon = R.drawable.ic_tilting;
                    count6 = count;
                    count6++;
                    count = count6;
                    break;
                }
                case DetectedActivity.WALKING: {
                    label = getString(R.string.activity_walking);
                    icon = R.drawable.ic_walking;
                    count7 = count;
                    count7++;
                    count = count7;
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    label = getString(R.string.activity_unknown);
                    count8 = count;
                    count8++;
                    count = count8;
                    break;
                }
            }

            //String strTime = currentTimestamp.toString();

        if(label == "Unknown"){

        }

            Log.d(TAG, "handleUserActivity: " + "Current Timestamp: " + currentTimestamp + " User activity: " + label + ", Confidence: " + confidence + " Count: " + count);
            if (confidence > Constants.CONFIDENCE) {
                txtActivity.setText(label);
                txtConfidence.setText("Confidence: " + confidence);
                txtTime.setText("Time: " + currentTimestamp);
                imgActivity.setImageResource(icon);

                do {
//                Log.d(TAG, "START : "+startTime+" STOP : "+endTime);
//                if (startTime.equals(endTime) ){
                    DatabaseReference childRef = mRootref.child(label);
                    mRootref.child("0Time").push().setValue(strTime + " " + confidence);
                    mRootref.child("0Type").push().setValue(label + " " + confidence);
//                   childRef.child(count + "startTime ").setValue(startTime); //add start Time to DB
//                    Log.e(TAG, "START Timestamp: " + startTime + " User activity: " + label + ", Confidence: " + confidence + " Count:  " + count);
//                }
//                else {
                    endTime = currentTimestamp.toString();
                    childRef.child(count + "stopTime ").setValue(endTime); //add stop Time to DB
//
                    Log.e(TAG, "STOP Timestamp: " + endTime
                            + " User activity: " + label + ", Confidence: " + confidence +" Count: "+count);
                    newLabel = label;
                    count++;
//                    startTime = endTime;
//                    Log.d(TAG, "start time: " + strTime +" newLabel"+ newLabel);
//                }

                }

                while(label  != newLabel);


                //endTime = strTime;
                count--;

                endTime = currentTimestamp.toString();
                Log.d(TAG, "start: "+ startTime+" stop: "+endTime);

            }


        //Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);



    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startTracking() {
        Intent intent = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent);
    }

    private void stopTracking() {
        Intent intent = new Intent(MainActivity.this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
    }


}