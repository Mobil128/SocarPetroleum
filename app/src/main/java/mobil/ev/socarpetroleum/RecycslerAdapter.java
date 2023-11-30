package mobil.ev.socarpetroleum;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecycslerAdapter extends RecyclerView.Adapter<RecycslerAdapter.ViewHolder> {

    private List<String> dataList; // Замените на ваш список данных

    // Конструктор для передачи данных в адаптер
    public RecycslerAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    // Создание нового ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycitem, parent, false);
        return new ViewHolder(view);
    }

    // Связывание данных с ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.bind(data);
    }

    // Возвращает общее количество элементов в списке
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Вложенный класс для представления каждого элемента
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView); // Замените на ваш TextView из макета
        }

        // Привязка данных к элементу списка
        public void bind(String data) {
            textView.setText(data);
            // Добавьте код для обработки нажатий или других событий
        }
    }
}
