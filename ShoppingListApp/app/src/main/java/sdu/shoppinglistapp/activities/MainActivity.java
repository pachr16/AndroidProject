package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.persistence.DbHandler;

public class MainActivity extends AppCompatActivity {
    private DbHandler dbh = DbHandler.getInstance();

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        // END OF TESTING PURPOSES

        Log.d("MyTag", "onCreate: Main found");
    }

    @Override
    protected void onResume() {
        super.onResume();

//        // checks if the user is logged in (i.e. not null), and sends them to login if no User object is found. Otherwise redirects to the shoppingActivity.
//        if (user == null) {
//            Log.d("***DEBUG", "onResume: User was NULL, going to login activity");
//            Intent logIntent = new Intent(this, LoginActivity.class);
//            startActivity(logIntent);
//        } else {
//            Log.d("***DEBUG", "onResume: User was: " + user);
//            Intent shopIntent = new Intent(this, ShoppingActivity.class);
//            shopIntent.putExtra("User", user);
//            startActivity(shopIntent);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

//    private void updateUI(FirebaseUser currentUser) {
//        if (currentUser == null) {
//            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }
}
