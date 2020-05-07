package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.User;
import sdu.shoppinglistapp.persistence.DbHandler;

public class ShoppingActivity extends AppCompatActivity {

    private User user;
    TextView welcome;
    DbHandler db = DbHandler.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        welcome = findViewById(R.id.shopping_welcome);

    }

    @Override
    public void onStart() {
        super.onStart();

        String screenName = getScreenName(FirebaseAuth.getInstance().getCurrentUser().getUid());

        welcome.setText("Welcome " + screenName);

        Toast.makeText(ShoppingActivity.this, "Text?", Toast.LENGTH_LONG).show();
    }

    private String getScreenName(String userId) {
        String[] result = db.getUser(userId);
        Log.d("getUser", "getScreenName: " + result.toString());
        return result[0];
    }
}
