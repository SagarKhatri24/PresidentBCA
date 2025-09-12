package president.bca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    String[] nameArray;
    String[] imageArray;

    public CustomListAdapter(Context context, String[] nameArray, String[] imageArray) {
        this.context = context;
        this.nameArray = nameArray;
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return nameArray.length;
    }

    @Override
    public Object getItem(int i) {
        return nameArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_listview,null);

        ImageView imageView = view.findViewById(R.id.custom_listview_image);
        TextView name = view.findViewById(R.id.custom_listview_name);

        name.setText(nameArray[i]);
        //imageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context).load(imageArray[i]).placeholder(R.mipmap.ic_launcher).into(imageView);

        return view;
    }
}
