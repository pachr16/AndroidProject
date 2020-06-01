package sdu.shoppinglistapp.businessFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.LoginActivity;
import sdu.shoppinglistapp.activities.ShoppingActivity;
import sdu.shoppinglistapp.activities.ShoppingListContent;

public class FragmentShoppingMain extends Fragment {

    TextView welcome;
    EditText listName;
    Button btnCreateList;
    Button btnViewLists;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_main, container, false);

        welcome = view.findViewById(R.id.shopping_welcome);
        listName = view.findViewById(R.id.shopping_createList);
        btnCreateList = view.findViewById(R.id.btn_shopping_createList);
        btnViewLists = view.findViewById(R.id.btn_shopping_viewLists);
        logout = view.findViewById(R.id.logout);

        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShoppingActivity)getActivity()).createNewList(listName.getText().toString());
                listName.setText("");
                Toast.makeText(getActivity(), "List created", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShoppingActivity)getActivity()).setViewPager(1);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent((ShoppingActivity)getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
