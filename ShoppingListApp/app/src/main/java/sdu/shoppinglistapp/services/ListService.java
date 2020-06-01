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
    private static String activeList;
    FirebaseDatabase db;

    public ListService() {
        super("ListService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseReference actListRef = db.getReference("shoppingLists/" + getActiveList());
        actListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast toast = Toast.makeText(ListService.this, "List has changed", LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }
        });

    }

    public String getActiveList() {
        return activeList;
    }

    public static void setActiveList(String activeList) {
        ListService.activeList = activeList;
    }
}