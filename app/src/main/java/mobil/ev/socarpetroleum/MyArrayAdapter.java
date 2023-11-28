package mobil.ev.socarpetroleum;


import android.content.Context;
import android.content.Entity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter < MyDataModel > {

    List < MyDataModel > modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, List < MyDataModel > objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

     public void getget(int position){

         MyDataModel item = getItem(position);
     }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.textViewYanacaqNovu.setText(item.getYanacaq_novu());
        vh.textViewMetrostok.setText(item.getMetrostok());
        vh.textViewHecm.setText(item.getHecmi());
        vh.textViewCemi.setText(item.getCemi());
      if(item.getOn_off().equals("true")){
        vh.checkBox.setChecked(true);}
        return vh.rootView;
    }



    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewYanacaqNovu;
        public final EditText textViewMetrostok;
        public final TextView textViewHecm;
        public final TextView textViewCemi;
        private final CheckBox checkBox;

        private ViewHolder(RelativeLayout rootView, TextView textViewYanacaqNovu, EditText textViewMetrostok, TextView textViewHecm, TextView textViewCemi, CheckBox checkBox) {
            this.rootView = rootView;
            this.textViewYanacaqNovu = textViewYanacaqNovu;
            this.textViewMetrostok = textViewMetrostok;
            this.textViewCemi = textViewCemi;
            this.textViewHecm = textViewHecm;
            this.checkBox = checkBox;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewYanacaqNovu = (TextView) rootView.findViewById(R.id.tv_yan_nov);
            EditText textViewMetrostok = (EditText) rootView.findViewById(R.id.et_metrostok);
            TextView textViewHecm = (TextView) rootView.findViewById(R.id.tv_hecm);
            TextView textViewCemi = (TextView) rootView.findViewById(R.id.tv_cemi);
            CheckBox checkBox= (CheckBox) rootView.findViewById(R.id.checkBox);


            return new ViewHolder(rootView, textViewYanacaqNovu, textViewMetrostok, textViewHecm,textViewCemi,checkBox);
        }
    }

}
