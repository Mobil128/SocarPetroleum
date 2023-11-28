package mobil.ev.socarpetroleum;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapterDirek extends ArrayAdapter < MyDataModelDirek > {

    List < MyDataModelDirek > modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapterDirek(Context context, List < MyDataModelDirek > objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModelDirek getItem(int position) {
        return modelList.get(position);
    }

     public void getget(int position){

         MyDataModelDirek item = getItem(position);
     }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_viewdi, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModelDirek item = getItem(position);

        vh.textViewYanacaqNovu.setText(item.getYanacaq_novu());
        vh.textViewMetrostok.setText(item.getMetrostok());

        vh.textViewCemi.setText(item.getCemi());

        return vh.rootView;
    }



    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewYanacaqNovu;
        public final EditText textViewMetrostok;

        public final TextView textViewCemi;


        private ViewHolder(RelativeLayout rootView, TextView textViewYanacaqNovu, EditText textViewMetrostok, TextView textViewCemi) {
            this.rootView = rootView;
            this.textViewYanacaqNovu = textViewYanacaqNovu;
            this.textViewMetrostok = textViewMetrostok;
            this.textViewCemi = textViewCemi;


        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewYanacaqNovu = (TextView) rootView.findViewById(R.id.tv_yan_nov);
            EditText textViewMetrostok = (EditText) rootView.findViewById(R.id.et_metrostok);

            TextView textViewCemi = (TextView) rootView.findViewById(R.id.tv_cemi);



            return new ViewHolder(rootView, textViewYanacaqNovu, textViewMetrostok, textViewCemi);
        }
    }

}
