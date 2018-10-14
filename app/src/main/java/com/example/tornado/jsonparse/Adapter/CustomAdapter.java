package com.example.tornado.jsonparse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tornado.jsonparse.Model.Person;
import com.example.tornado.jsonparse.R;

import java.util.ArrayList;




public class CustomAdapter extends BaseAdapter
{


    private Context context;
    private ArrayList<Person> data = new ArrayList<>();
    private LayoutInflater inflater = null;
    Person tempValues = null;


    public CustomAdapter(Context ctx, ArrayList<Person> data)
    {


        this.context = ctx;
        this.data = data;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount()
    {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder
    {
        public TextView txtName;
        public TextView txtEmail;
        public TextView txtmobile;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null)
        {
            vi = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.txtName = (TextView) vi.findViewById(R.id.txtName);
            holder.txtEmail = (TextView) vi.findViewById(R.id.txtEmail);
            holder.txtmobile = (TextView) vi.findViewById(R.id.txtMobile);

            vi.setTag(holder);
        }
        else
            holder = (ViewHolder) vi.getTag();

        if (data.size() == 0)
        {
            holder.txtName.setText("No Data");

        }
        else
        {
            tempValues = data.get(position);
            holder.txtName.setText(tempValues.getName());
            holder.txtEmail.setText(tempValues.getEmail());
            holder.txtmobile.setText(tempValues.getPhone().getMobile());

        }
        return vi;
    }

}