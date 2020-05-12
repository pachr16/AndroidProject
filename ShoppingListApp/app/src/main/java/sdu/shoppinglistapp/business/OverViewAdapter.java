package sdu.shoppinglistapp.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import sdu.shoppinglistapp.R;

public class OverViewAdapter extends ArrayAdapter {

    ArrayList<ShoppingList> lists = new ArrayList<>();
    public OverViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ShoppingList> objects) {
        super(context, resource, objects);

        lists = objects;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(lists.get(position).getName());

        return view;
    }
}
