package president.bca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    /*String[] nameArray;
    String[] imageArray;

    public CustomListAdapter(Context context, String[] nameArray, String[] imageArray) {
        this.context = context;
        this.nameArray = nameArray;
        this.imageArray = imageArray;
    }*/

    ArrayList<CustomList> arrayList;

    public CustomListAdapter(Context context, ArrayList<CustomList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        //return nameArray.length;
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        //return nameArray[i];
        return  arrayList.get(i);
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

        name.setText(arrayList.get(i).getName());
        //imageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context).load(arrayList.get(i).getImage()).placeholder(R.mipmap.ic_launcher).into(imageView);

        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Image Clicked : "+nameArray[i], Toast.LENGTH_SHORT).show();
            }
        });*/

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view,nameArray[i],Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CategoryDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("NAME",arrayList.get(i).getName());
                bundle.putString("IMAGE",arrayList.get(i).getImage());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

        return view;
    }
}
