package info.androidhive.activityrecognition;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Retrieve extends AppCompatActivity {

    ListView listViewStill, listViewWalk, listViewRun;
    FirebaseDatabase database;
    DatabaseReference refStill, refWalk, refRun , mRootref;
    ArrayList<String> listStill, listWalk, listRun;
    ArrayAdapter<String> adapterStill, adapterWalk, adapterRun;
    User user;
    Label label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        user = new User();
        listViewStill = (ListView)findViewById(R.id.listview_still);
        mRootref = FirebaseDatabase.getInstance().getReference();
        mRootref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimeactivity-3a959.firebaseio.com/User");
        database = FirebaseDatabase.getInstance();
        refStill = FirebaseDatabase.getInstance().getReferenceFromUrl("https://realtimeactivity-3a959.firebaseio.com/User/Still");
        listStill = new ArrayList<>();
        adapterStill = new ArrayAdapter<String>(this,R.layout.activity_still,R.id.listview_still,listStill);
        refStill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    label = ds.getValue(Label.class);
                    listStill.add("Start Time: " + label.getStartTime().toString() + "\nStop Time:  " + label.getStopTime());
//                    for(DataSnapshot time : dataSnapshot.getChildren()){
//                        label = time.getValue(Label.class);
//                        list.add(label.getStartTime().toString() + " " + label.getStopTime());
//
//                    }
                }
                listViewStill.setAdapter(adapterStill);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
