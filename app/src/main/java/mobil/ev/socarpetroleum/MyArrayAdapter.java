package mobil.ev.socarpetroleum;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter < MyDataModel > {

    List < MyDataModel > modelList;
    Context context;
    private LayoutInflater mInflater;

    // Интерфейс для вызова метода в активности
    public interface OnItemChangeListener {
        void onItemChanged(int position, String newValue);
    }

    private OnItemChangeListener listener;

    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects, OnItemChangeListener listener) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
        this.listener = listener; // Передаем слушатель
    }
    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects) {
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



        // Remove any previous TextWatcher
        if (vh.textViewMetrostok.getTag() instanceof TextWatcher) {
            vh.textViewMetrostok.removeTextChangedListener((TextWatcher) vh.textViewMetrostok.getTag());
        }

        // Add a TextWatcher to update the TextView "textViewCemi" when "textViewMetrostok" changes
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Update the "Cemi" TextView based on the new value in "Metrostok"
                int currentPosition = 8;
                String newText = s.toString();

                // Вызов метода активности через интерфейс
                if (listener != null) {
                    listener.onItemChanged(currentPosition, newText);
                    }
               /* String newText = s.toString();
                if(newText.equals("")){newText="0";}
                Kalibrovka kalibrovka= new Kalibrovka();

                String newText2 = kalibrovka.getValue(UserInfo.getUser_ydm(),1, newText,2,Integer.parseInt(UserInfo.getCenlerin_sayi()));
                vh.textViewHecm.setText(newText2); // Or perform any specific logic you need*/

            }
        };

        // Set the TextWatcher and save it in the tag to avoid duplications
        vh.textViewMetrostok.addTextChangedListener(textWatcher);
        vh.textViewMetrostok.setTag(textWatcher);




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
