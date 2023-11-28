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

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapterTab2 extends ArrayAdapter< MyDataModelTab2 > {


    List< MyDataModelTab2> modelList;
    Context context;
    private LayoutInflater mInflater;

    public MyArrayAdapterTab2(Context context, List < MyDataModelTab2 > objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }
    @Override
    public MyDataModelTab2 getItem(int position) {
        return modelList.get(position);
    }

    public void getget(int position){

        MyDataModelTab2 item = getItem(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyArrayAdapterTab2.ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view_tab2, parent, false);
            vh = MyArrayAdapterTab2.ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (MyArrayAdapterTab2.ViewHolder) convertView.getTag();
        }

        MyDataModelTab2 item = getItem(position);

        vh.user.setText(item.getUser());
        vh.tarix.setText(item.getTarix());
        vh.ai92.setText(item.getAi92());
        vh.premium.setText(item.getPrem());
        vh.sup.setText(item.getSup());
        vh.dizel.setText(item.getDiz());
        vh.lpg.setText(item.getLpg());

        return vh.rootView;
    }


    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView user;
        public final TextView tarix;
        public final TextView ai92;
        public final TextView premium;
        public final TextView sup;
        public final TextView dizel;
        public final TextView lpg;



       // private final CheckBox checkBox;

        private ViewHolder(RelativeLayout rootView, TextView user, TextView tarix, TextView ai92, TextView premium, TextView sup,TextView dizel,TextView lpg) {
            this.rootView = rootView;
            this.user = user;
            this.tarix = tarix;
            this.ai92 = ai92;
            this.premium = premium;
            this.dizel= dizel;
            this.sup= sup;
            this.lpg = lpg;


        }

        public static MyArrayAdapterTab2.ViewHolder create(RelativeLayout rootView) {
            TextView user = (TextView) rootView.findViewById(R.id.tv_tab2_user);
            TextView tarix = (TextView) rootView.findViewById(R.id.tv_tab2_tarix);
            TextView ai92= (TextView) rootView.findViewById(R.id.tv_tab2_ai92);
            TextView premium = (TextView) rootView.findViewById(R.id.tv_tab2_premium);
           TextView sup= (TextView) rootView.findViewById(R.id.tv_tab2_super);
            TextView dizel= (TextView) rootView.findViewById(R.id.tv_tab2_dizel);
            TextView lpg= (TextView) rootView.findViewById(R.id.tv_tab2_lpg);


            return new MyArrayAdapterTab2.ViewHolder(rootView, user, tarix, ai92,premium,sup,dizel,lpg);
        }
    }
}
