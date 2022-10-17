package com.example.todolistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;
import com.example.todolistapp.database.*;
import com.example.todolistapp.helper.HandleClicks;
import com.example.todolistapp.models.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<TodoItem> todoItemArrayList;
    private final Context context;
    private TodoDatabaseHandler db;
    private HandleClicks mClickListener;

    public void setTodoItemArrayList(ArrayList<TodoItem> todoItemArrayList) {
        this.todoItemArrayList = todoItemArrayList;
        notifyDataSetChanged();
    }

    public TasksAdapter(TodoDatabaseHandler db, Context context){
        this.db = db;
        this.context = context;
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_layout , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(todoItemArrayList.get(position).getTitle());
        holder.desc.setText(todoItemArrayList.get(position).getDesc());
        holder.done.setChecked(todoItemArrayList.get(position).getIs_done() != 0);
        holder.done.setOnClickListener(view -> {
            if (mClickListener != null){
                mClickListener.check(position , view);
            }
        });
        holder.del.setOnClickListener(view -> {
            if (mClickListener != null){
                mClickListener.delete(position , view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.todoItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView desc;
        public CheckBox done;
        public ImageButton del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            done = itemView.findViewById(R.id.done);
            del = itemView.findViewById(R.id.del);
        }
    }
    public void setClickListener(HandleClicks itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
