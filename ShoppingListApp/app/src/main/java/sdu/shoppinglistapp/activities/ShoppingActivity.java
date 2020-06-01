package sdu.shoppinglistapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.SectionsStatePagerAdapter;

//All used fragments
import sdu.shoppinglistapp.businessFragments.FragmentShoppingListOverview;
import sdu.shoppinglistapp.businessFragments.FragmentShoppingMain;
import sdu.shoppinglistapp.services.ListService;


public class ShoppingActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference listRef;
    private DatabaseReference subRef;
    private DatabaseReference subToRef;

    private SectionsStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Don't mind the red line.
        //It's a warning against locking screen orientation which is needed here as we do not want the user to go to landscape-mode
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_shopping);


        mAuth = FirebaseAuth.getInstance();

        mSectionStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);

        setupViewPager(mViewPager);


        //Start ListService, to notify user when one of their subscribed lists change
        Intent intent = new Intent(this, ListService.class);

        startService(intent);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentShoppingMain(), "Main shopping fragment");
        adapter.addFragment(new FragmentShoppingListOverview(), "List overview");
        //adapter.addFragment(new FragmentShoppingListContent(), "List Content");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }



    public void createNewList(String listName) {

        String listId = mAuth.getCurrentUser().getUid() + "_" + listName;

        listRef = mFirebaseDatabase.getReference("shoppingLists/" + listId);

        listRef.child("listName").setValue(listName);

        subRef = mFirebaseDatabase.getReference("shoppingLists/" + listId + "/subscribers");
        subRef.push().setValue(mAuth.getCurrentUser().getUid());

        subToRef = mFirebaseDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/subscribed_to");
        subToRef.child(listId).setValue(listId);

        Toast.makeText(ShoppingActivity.this, "List created", Toast.LENGTH_SHORT).show();
    }


}
