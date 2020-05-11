package sdu.shoppinglistapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_LONG;

public class ListService extends IntentService {
    FirebaseDatabase db;
    DatabaseReference myRef;

    public ListService() {
        super("ListService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("List", "ListService has started.");
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("lists/");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("OnDataChange", "Entered");
                Toast toast = Toast.makeText(ListService.this, "List has changed", LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }
}