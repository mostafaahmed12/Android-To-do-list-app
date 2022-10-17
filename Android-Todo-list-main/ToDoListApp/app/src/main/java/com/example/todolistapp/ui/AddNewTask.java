package com.example.todolistapp.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todolistapp.R;
import com.example.todolistapp.database.TodoDatabaseHandler;
import com.example.todolistapp.database.UserDatabaseHandler;
import com.example.todolistapp.models.TodoItem;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private EditText newDescription;
    private Button newTaskSaveButton;

    private TodoDatabaseHandler db;
    private UserDatabaseHandler user;


    int itemId = -1;
    TodoItem Item;

    public AddNewTask(int id1) {
        this.itemId = id1;
    }

    public static AddNewTask newInstance(int id){
        return new AddNewTask(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.TextTask);
        newDescription=getView().findViewById(R.id.DescText);
        newTaskSaveButton = getView().findViewById(R.id.TaskButton);

        boolean isUpdate = false;

        db = new TodoDatabaseHandler(getActivity());

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newTaskText.getText().toString();
                String desc = newDescription.getText().toString();
                if(finalIsUpdate){
                    db.updateItem(itemId , title ,desc , 0 );
                }
                else {
                    TodoItem task = new TodoItem();
                   task.setTitle(title);
                   task.setDesc(desc);
                    task.setIs_done(0);
                    db.addItem(title,desc, itemId,0);
                }
                dismiss();

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        this.getActivity().recreate();
    }
}