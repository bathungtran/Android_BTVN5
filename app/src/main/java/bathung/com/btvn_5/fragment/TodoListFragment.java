package bathung.com.btvn_5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bathung.com.btvn_5.R;
import bathung.com.btvn_5.Task;
import bathung.com.btvn_5.adapter.ToDoListAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HH on 4/5/2018.
 */

public class TodoListFragment extends Fragment implements TaskDialogFragment.DataProcess
{
    private View view;
    private RecyclerView rvTodoList;
    private Button btnAddTodo;
    private List<Task> taskList;
    private ToDoListAdapter toDoListAdapter;
    public OnTaskDone onTaskDone;
    public  TodoListFragment(){

    };

    public interface OnTaskDone {
        void UpdateDoneList(Task task);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.todo_list_fragment, container, false);
        rvTodoList = view.findViewById(R.id.rlvToDo);
        btnAddTodo = view.findViewById(R.id.btnToDo);
        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialogFragment taskDialogFragment = new TaskDialogFragment();
                taskDialogFragment.setTargetFragment(TodoListFragment.this,1);
                taskDialogFragment.show(getFragmentManager(),"create_dialog");
            }
        });

        taskList= new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> results = realm.where(Task.class).equalTo("isDone",false).findAll();
        for(Task task:results){
            taskList.add(task);
        }

        toDoListAdapter = new ToDoListAdapter(taskList,getContext(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTodoList.getContext(),
                layoutManager.getOrientation());
        rvTodoList.setLayoutManager(layoutManager);
        rvTodoList.addItemDecoration(dividerItemDecoration);
        rvTodoList.setAdapter(toDoListAdapter);
        return  view;
    }

    @Override
    public void AddData(Task task) {
        taskList.add(task);
        toDoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void UpdateData(Task task, int pos) {
        taskList.set(pos,task);
        toDoListAdapter.notifyDataSetChanged();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onTaskDone = (OnTaskDone) context;
        } catch (ClassCastException e) {
            Log.e("Error", e.toString());
        }
    }
}

