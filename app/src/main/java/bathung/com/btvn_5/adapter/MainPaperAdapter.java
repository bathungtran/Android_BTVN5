package bathung.com.btvn_5.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import bathung.com.btvn_5.fragment.DoneListFragment;
import bathung.com.btvn_5.fragment.TodoListFragment;

/**
 * Created by HH on 4/5/2018.
 */

public class MainPaperAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList= new ArrayList<>();
    private List<String>   titleList = new ArrayList<>();
    public MainPaperAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
       return fragmentList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        return  titleList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void AddFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
