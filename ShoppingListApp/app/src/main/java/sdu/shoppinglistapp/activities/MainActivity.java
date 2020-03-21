package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;

public class MainActivity extends AppCompatActivity {
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR TESTING PURPOSES:
        ArrayList<ShopList> slist = new ArrayList<>();
        ArrayList<ShopItem> ilist = new ArrayList<>();
        ilist.add(new ShopItem("thisisanitem", false, "ThisisaScreenName", 0));
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "ThisisaScreenName");
        slist.add(new ShopList(0, "thisisalist", 109850923, ilist, map));
        user = new User("Patrick", "email", 0, slist);

        // END OF TESTING PURPOSES

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
            shopIntent.putExtra("User", user);
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
