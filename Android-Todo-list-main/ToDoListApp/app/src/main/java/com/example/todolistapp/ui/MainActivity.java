package com.example.todolistapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todolistapp.R;
import com.example.todolistapp.adapters.TasksAdapter;
import com.example.todolistapp.database.TodoDatabaseHandler;
import com.example.todolistapp.database.UserDatabaseHandler;
import com.example.todolistapp.helper.HandleClicks;
import com.example.todolistapp.models.TodoItem;
import com.google.android.material.floatingactionbutton.*;

import java.util.*;

public class MainActivity extends AppCompatActivity implements HandleClicks {

    static ArrayList<TodoItem> todoItems;
    TasksAdapter tasksAdapter;
    public static int userId = -1;

    TodoDatabaseHandler todoHandler = new TodoDatabaseHandler(this);
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("jjjjjjjjj" , "jjjjjjjjjjj");

        RecyclerView rv = findViewById(R.id.TasksRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("id");
        }


        tasksAdapter = new TasksAdapter(todoHandler , this);
        tasksAdapter.setClickListener(this);
        rv.setAdapter(tasksAdapter);
        fab = findViewById(R.id.Fab);

        todoItems = new ArrayList<TodoItem>();
        todoItems = todoHandler.getAllItemsOfUser(userId);
        Log.d("jjjjjjjjj" , "jjjjjjjjjjj");
        Log.d("hhhhhhhhh" , todoItems.toString());
        Collections.reverse(todoItems);
        tasksAdapter.setTodoItemArrayList(todoItems);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance(userId).show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }


    @Override
    public void check(int pos, View view) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+pos);
        todoItems.get(pos).setIs_done(todoItems.get(pos).getIs_done() != 0 ? 0 : 1);
        tasksAdapter.notifyItemChanged(pos);
    }

    @Override
    public void delete(int pos, View view) {
        TodoItem item = todoItems.get(pos);
        todoHandler.deleteItem(item.getId());
        todoItems.remove(pos);
        tasksAdapter.notifyItemRemoved(pos);
        tasksAdapter.notifyItemRangeChanged(pos, todoItems.size());
    }


}