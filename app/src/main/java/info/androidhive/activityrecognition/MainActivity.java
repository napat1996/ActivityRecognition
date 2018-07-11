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
    String label="";
    int count1 = 0,count2 = 0,count3 = 0,count4 = 0,count5 = 0,count6 = 0,count7 = 0,count = 0;

    private void handleUserActivity(int type, int confidence) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        mRootref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimeactivity-3a959.firebaseio.com/User");

        String strTime = currentTimestamp.toString();
        startTime = strTime;

        int icon = R.drawable.ic_still;

            switch (type) {
                case DetectedActivity.IN_VEHICLE: {
                    newLabel = getString(R.string.activity_in_vehicle);
                    icon = R.drawable.ic_driving;
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    newLabel = getString(R.string.activity_on_bicycle);
                    icon = R.drawable.ic_on_bicycle;
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
                    newLabel = getString(R.string.activity_running);
                    icon = R.drawable.ic_running;
                    break;
                }
                case DetectedActivity.STILL: {
                    newLabel = getString(R.string.activity_still);
                    icon = R.drawable.ic_still;
                    break;
                }
                case DetectedActivity.TILTING: {
                    newLabel  = getString(R.string.activity_tilting);
                    icon = R.drawable.ic_tilting;
                    break;
                }
                case DetectedActivity.WALKING: {
                    newLabel = getString(R.string.activity_walking);
                    icon = R.drawable.ic_walking;
                    break;
                }
//                case DetectedActivity.UNKNOWN: {
//                    label = getString(R.string.activity_unknown);
//                    break;
//                }
            }

            //String strTime = currentTimestamp.toString();


            Log.d(TAG, "handleUserActivity: " + "Current Timestamp: " + currentTimestamp + " User activity: " + label + ", Confidence: " + confidence + " Count: " + count);
            if (confidence > Constants.CONFIDENCE) {
                txtActivity.setText(label);
                txtConfidence.setText("Confidence: " + confidence);
                txtTime.setText("Time: " + currentTimestamp);
                imgActivity.setImageResource(icon);

//                Log.d(TAG, "START : "+startTime+" STOP : "+endTime);
               if (label != newLabel) {
                   DatabaseReference oldRef = mRootref.child(label);
                   Log.e(TAG, "****************************************** old: "+label+" new: "+newLabel );
                   if(label == getString(R.string.activity_in_vehicle)){
                       oldRef.child(count1 + "stopTime ").setValue(currentTimestamp.toString()); //add start Time to DB
                   }else if(label == getString(R.string.activity_on_bicycle)){
                       oldRef.child(count2 + "stopTime ").setValue(currentTimestamp.toString());
                   }else if(label == getString(R.string.activity_running)){
                       oldRef.child(count4 + "stopTime ").setValue(currentTimestamp.toString());
                   }else if(label == getString(R.string.activity_still)){
                       oldRef.child(count5 + "stopTime ").setValue(currentTimestamp.toString());
                   }else if(label == getString(R.string.activity_tilting)){
                       oldRef.child(count6 + "stopTime ").setValue(currentTimestamp.toString());
                   }else if(label == getString(R.string.activity_walking)){
                       oldRef.child(count7 + "stopTime ").setValue(currentTimestamp.toString());
                   }
                    label = newLabel;
                   DatabaseReference childRef = mRootref.child(newLabel);
                   endTime = currentTimestamp.toString();
                   switch (newLabel) {
                       case "In Vehicle": {
                           label = getString(R.string.activity_in_vehicle);
                      //     DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                         //  newChildRef.child(count1 + "stopTime ").setValue(startTime); //add start Time to DB
                           count1++;
                           childRef.child(count1 + "startTime ").setValue(endTime);
                            newLabel = label;
                           break;
                       }
                       case "On Bicycle": {
                           label = getString(R.string.activity_on_bicycle);
                        //   DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                         //  newChildRef.child(count2 + "stopTime ").setValue(startTime); //add start Time to DB
                           count2++;
                           childRef.child(count2 + "startTime ").setValue(endTime);
                           newLabel = label;
                           break;
                       }
                       case "Running": {
                           label = getString(R.string.activity_running);
                       //    DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                         //  newChildRef.child(count4 + "stopTime ").setValue(startTime); //add start Time to DB
                           count4++;
                           childRef.child(count4 + "startTime ").setValue(endTime);
                           newLabel = label;
                           break;
                       }
                       case "Still": {
                           label = getString(R.string.activity_still);
                    //       DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                         //  newChildRef.child(count5 + "stopTime ").setValue(startTime); //add start Time to DB
                           count5++;
                           childRef.child(count5 + "startTime ").setValue(endTime);
                           newLabel = label;
                           break;
                       }
                       case "Tilting": {
                           label = getString(R.string.activity_tilting);
                       //    DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                        //   newChildRef.child(count6 + "stopTime ").setValue(startTime); //add start Time to DB
                           count6++;
                           childRef.child(count6 + "startTime ").setValue(endTime);
                           newLabel = label;
                           break;
                       }
                       case "walking": {
                           label = getString(R.string.activity_walking);
                       //    DatabaseReference newChildRef = mRootref.child(newLabel);
                           Log.e(TAG, "newlabel: " + newLabel);
                        //   newChildRef.child(count7 + "stopTime ").setValue(startTime); //add start Time to DB
                           count7++;
                           childRef.child(count7 + "startTime ").setValue(endTime);
                           newLabel = label;
                           break;
                       }
                   }

                   //add stop Time to DB
                   //mRootref.child("0Time").push().setValue(strTime + " " + confidence);
                   //mRootref.child("0Type").push().setValue(label + " " + confidence);

//                    Log.e(TAG, "START Timestamp: " + startTime + " User activity: " + label + ", Confidence: " + confidence + " Count:  " + count);
               }


                    Log.e(TAG, "STOP Timestamp: " + endTime
                            + " User activity: " + label + ", Confidence: " + confidence +" Count: "+count);

                 //   count++;
//                    startTime = endTime;
//                    Log.d(TAG, "start time: " + strTime +" newLabel"+ newLabel);
            }
                //endTime = strTime;
                Log.d(TAG, "start: "+ startTime+" stop: "+endTime);

            }


        //Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);




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