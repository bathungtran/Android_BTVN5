package bathung.com.btvn_5;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bathung.com.btvn_5.adapter.MainPaperAdapter;
import bathung.com.btvn_5.fragment.DoneListFragment;
import bathung.com.btvn_5.fragment.TodoListFragment;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements TodoListFragment.OnTaskDone  {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    DoneListFragment doneListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(getApplicationContext());
        viewPager = findViewById(R.id.main_viewpaper);
        tabLayout = findViewById(R.id.main_tab);
        MainPaperAdapter mainPaperAdapter = new MainPaperAdapter(getSupportFragmentManager());
        doneListFragment = new DoneListFragment();
        mainPaperAdapter.AddFragment(new TodoListFragment(),"To Do");
        mainPaperAdapter.AddFragment(doneListFragment,"Done");
        viewPager.setAdapter(mainPaperAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void UpdateDoneList(Task task) {
        doneListFragment.UpdateDoneList(task);
    }
}
