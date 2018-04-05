package bathung.com.btvn_5.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bathung.com.btvn_5.adapter.DoneListAdapter;
import bathung.com.btvn_5.R;
import bathung.com.btvn_5.Task;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HH on 4/5/2018.
 */

public class DoneListFragment extends Fragment{
    private RecyclerView rvDoneList;
    private List<Task> taskList;
    private DoneListAdapter doneListAdapter;
    public DoneListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.done_list_fragment, container, false);
        rvDoneList = view.findViewById(R.id.rlvDone);
        taskList= new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> results = realm.where(Task.class).equalTo("isDone",true).findAll();
        for(Task task:results){
            taskList.add(task);
        }
        doneListAdapter = new DoneListAdapter(taskList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvDoneList.getContext(),
                layoutManager.getOrientation());
        rvDoneList.setLayoutManager(layoutManager);
        rvDoneList.addItemDecoration(dividerItemDecoration);
        rvDoneList.setAdapter(doneListAdapter);
       return  view;
    }

    public void UpdateDoneList(Task task) {
        taskList.add(task);
        doneListAdapter.notifyDataSetChanged();
    }
}
