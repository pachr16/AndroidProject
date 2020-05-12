package sdu.shoppinglistapp.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;

public class ContentAdapter extends ArrayAdapter {

    ArrayList<ShopItem> items = new ArrayList<>();

    public ContentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ShopItem> objects) {
        super(context, resource, objects);

        items = objects;
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
        view = inflater.inflate(R.layout.content_view_layout, null);
        TextView amount = view.findViewById(R.id.contentList_amount);
        amount.setText(items.get(position).getAmount());
        TextView product = view.findViewById(R.id.contentList_product);
        product.setText(items.get(position).getProduct());

        return view;
    }
}
