package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;
import sdu.shoppinglistapp.persistence.DbHandler;

public class MainActivity extends AppCompatActivity {
    private User user = null;
    private DbHandler dbh = DbHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR TESTING PURPOSES:
        ArrayList<ShopList> slist = new ArrayList<>();

        ArrayList<ShopItem> ilist = new ArrayList<>();
        ilist.add(new ShopItem("thisisanitem", false, "ThisisaScreenName", ""));

        //slist.add(new ShopList(0, "thisisalist", 109850923, ilist, map));
        //user = new User("Patrick", "email", "testuserid", slist);

        /*
        HashMap<String, String> map = new HashMap<>();
        map.put("XuiWQTZXer531rw2Ay0A", "Patrick");
        new ShopList("8LULHyPzkyNmRGguELTU", "Indkøbsliste", 0, ilist, map);

         */


        // END OF TESTING PURPOSES

        Log.d("MyTag", "onCreate: Main found");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // checks if the user is logged in (i.e. not null), and sends them to login if no User object is found. Otherwise redirects to the shoppingActivity.
        if (user == null) {
            Log.d("***DEBUG", "onResume: User was NULL, going to login activity");
            Intent logIntent = new Intent(this, LoginActivity.class);
            startActivity(logIntent);
        } else {
            Log.d("***DEBUG", "onResume: User was: " + user);
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
