package mobil.ev.socarpetroleum;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapterWorkers extends ArrayAdapter < MyDataModelWorker > {

    List < MyDataModelWorker > modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapterWorkers(Context context, List < MyDataModelWorker > objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModelWorker getItem(int position) {
        return modelList.get(position);
    }

     public void getget(int position){

         MyDataModelWorker item = getItem(position);
     }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view_workers, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModelWorker item = getItem(position);

        vh.textId.setText(item.getId_worker());
        vh.textName.setText(item.getName_worker());

        return vh.rootView;
    }



    private static class ViewHolder {
        public final RelativeLayout rootView;


        public final TextView textId;
        public final TextView textName;


        private ViewHolder(RelativeLayout rootView,TextView textId, TextView textName) {
            this.rootView = rootView;
            this.textName = textName;
            this.textId = textId;

        }

        public static ViewHolder create(RelativeLayout rootView) {

            TextView textId = (TextView) rootView.findViewById(R.id.tv_worker_id);
            TextView textName = (TextView) rootView.findViewById(R.id.tv_worker_name);



            return new ViewHolder(rootView, textId, textName);
        }
    }

}
