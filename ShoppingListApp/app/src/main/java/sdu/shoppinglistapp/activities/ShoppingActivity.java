package sdu.shoppinglistapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;
import sdu.shoppinglistapp.businessFragments.ListOverviewFragment;

public class ShoppingActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        user = (User)(getIntent().getSerializableExtra("User"));

        Fragment lof = new ListOverviewFragment(user);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.bitch_please, lof);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(ShoppingActivity.this, "Text?", Toast.LENGTH_LONG).show();
    }
}
