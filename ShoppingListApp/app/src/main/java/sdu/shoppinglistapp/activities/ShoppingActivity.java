package sdu.shoppinglistapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.SectionsStatePagerAdapter;

//All used fragments
import sdu.shoppinglistapp.businessFragments.FragmentShoppingListContent;
import sdu.shoppinglistapp.businessFragments.FragmentShoppingListOverview;
import sdu.shoppinglistapp.businessFragments.FragmentShoppingMain;


public class ShoppingActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private SectionsStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        mAuth = FirebaseAuth.getInstance();

        mSectionStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentShoppingMain(), "Main shopping fragment");
        adapter.addFragment(new FragmentShoppingListOverview(), "List overview");
        adapter.addFragment(new FragmentShoppingListContent(), "List Content");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public void createNewList(String listName) {
        Log.d("login", "createNewList: userid = " + mAuth.getCurrentUser().getUid());

        String listId = mAuth.getCurrentUser().getUid() + "_" + listName;

        myRef = mFirebaseDatabase.getReference("shoppingLists/" + listId);

        myRef.child("listName").setValue(listName);

        myRef = mFirebaseDatabase.getReference("shoppingLists/" + listId + "/subscribers");
        myRef.push().setValue(mAuth.getCurrentUser().getUid());

        myRef = mFirebaseDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/subscribed_to");
        myRef.child(listId).setValue(listId);

        Toast.makeText(ShoppingActivity.this, "List created", Toast.LENGTH_SHORT).show();
    }
}
