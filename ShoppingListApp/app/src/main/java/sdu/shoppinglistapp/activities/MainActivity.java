package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.User;

public class MainActivity extends AppCompatActivity {
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyTag", "onCreate: Main found");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // checks if the user is logged in (i.e. not null), and sends them to login if no User object is found. Otherwise redirects to the shoppingActivity.
        if (user.equals(null)) {
            Intent logIntent = new Intent(this, LoginActivity.class);
            startActivity(logIntent);
        } else {
            Intent shopIntent = new Intent(this, ShoppingActivity.class);
            startActivity(shopIntent);
        }
    }

    // use to set the user after login
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
