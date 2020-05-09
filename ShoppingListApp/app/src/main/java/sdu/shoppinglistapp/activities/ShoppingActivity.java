package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.User;
import sdu.shoppinglistapp.persistence.DbHandler;

public class ShoppingActivity extends AppCompatActivity {

    private TextView welcome;
    private EditText createList;
    private Button btn_createList;
    private Button btn_viewAllLists;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        createList = findViewById(R.id.shopping_createList);
        btn_createList = findViewById(R.id.btn_shopping_createList);
        btn_viewAllLists = findViewById(R.id.btn_shopping_viewLists);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("users").child(userId);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Toast.makeText(ShoppingActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShoppingActivity.this, "Not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        welcome = findViewById(R.id.shopping_welcome);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String screenName = dataSnapshot.child("screen_name").getValue().toString();
                welcome.setText("Welcome " + screenName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("test", "onCancelled: id was: " + userId);
                Log.w("test", "Failed to read value.", error.toException());
            }
        });

        btn_createList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createList.getText().toString() != null) {
                    createNewList();
                } else {
                    Toast.makeText(ShoppingActivity.this, "Please enter a name for the Shopping list", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createNewList() {
        String listName = (mAuth.getCurrentUser().getUid() + "_" + createList.getText().toString());
        Map<String, Object> taskMap = new HashMap<>();

        myRef = mFirebaseDatabase.getReference("shoppingLists/" + listName);

        myRef.child("listName").setValue(createList.getText().toString());

        myRef = mFirebaseDatabase.getReference("shoppingLists/" + mAuth.getCurrentUser().getUid() + "_" + createList.getText().toString() + "/subscribers");
        myRef.push().setValue(mAuth.getCurrentUser().getUid());

        myRef = mFirebaseDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/subscribed_to");
        myRef.child(listName).setValue(listName);

        Toast.makeText(ShoppingActivity.this, "List created", Toast.LENGTH_SHORT).show();
        createList.setText("");
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
