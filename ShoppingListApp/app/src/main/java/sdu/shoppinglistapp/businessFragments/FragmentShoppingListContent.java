package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.ShoppingActivity;
import sdu.shoppinglistapp.business.ContentAdapter;
import sdu.shoppinglistapp.business.ShopItem;

public class FragmentShoppingListContent extends Fragment {

    ListView content;
    EditText amount;
    EditText product;
    Button btn_addToList;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_overview, container, false);

        final ArrayList<ShopItem> contentList = new ArrayList<>();

        content = view.findViewById(R.id.content_listView);
        amount = view.findViewById(R.id.content_amount);
        product = view.findViewById(R.id.content_product);
        btn_addToList = view.findViewById(R.id.btn_addToList);

        final ContentAdapter contentAdapter = new ContentAdapter((ShoppingActivity)getActivity(), R.layout.content_view_layout, contentList);



        return view;
    }
}
