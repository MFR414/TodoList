package id.ac.polinema.todoretrofit.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.Toast;

import java.util.List;

import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.models.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    private List<Todo> items;
    private OnTodoClickedListener listener;
    private OnTodoDelBtnListener deleteBtnListener;
    private SharedPreferences pref;
    private String size;

    public TodoAdapter(Context context, OnTodoClickedListener listener, OnTodoDelBtnListener deleteBtnListener) {
        this.context = context;
        this.listener = listener;
        this.deleteBtnListener = deleteBtnListener;
    }

    public void setItems(List<Todo> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_todo, viewGroup, false);
        size = pref.getString("text_size","16");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Todo todo = items.get(i);
        viewHolder.bind(todo, listener);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        Button deletebtn;
        AppCompatCheckBox status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.text_todo);
            deletebtn = itemView.findViewById(R.id.button_delete);
            todoText.setTextSize(Float.parseFloat(size));
            todoText.setTextColor(Color.BLACK);
        }

        public void bind(final Todo todo, final OnTodoClickedListener listener) {
            todoText.setText(todo.getTodo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(todo);
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBtnListener.onDeleteClicked(todo);
                }
            });
        }
    }

    public interface OnTodoClickedListener {
        void onClick(Todo todo);
    }

    public interface OnTodoDelBtnListener {
        void onDeleteClicked(Todo todo);
    }
    public void setShared(SharedPreferences pref){
        this.pref=pref;
    }
}
