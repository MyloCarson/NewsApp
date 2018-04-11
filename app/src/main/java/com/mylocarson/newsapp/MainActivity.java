package com.mylocarson.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mylocarson.newsapp.activities.SavedNewsActivity;
import com.mylocarson.newsapp.fragment.EntertainmentFragment;
import com.mylocarson.newsapp.fragment.HeadlineFragment;
import com.mylocarson.newsapp.fragment.SearchFragment;
import com.mylocarson.newsapp.fragment.SportsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    Context context;

    Toolbar toolbar;
    ViewPager viewpager;
    TabLayout tabLayout;

    private int [] tabIcons = {R.mipmap.tent,R.mipmap.news,R.mipmap.fitbit,R.mipmap.search};
    public static final String FRAGMENT_TAG ="ArticleFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewpager);
        viewpager.setCurrentItem(1);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager);
        setTabIcons();

        context = this;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.savedNews:
                Intent intent = new Intent(MainActivity.this, SavedNewsActivity.class);
                startActivity(intent);
                break;
        }
        return  true;
    }

    private void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setUpViewPager(ViewPager viewpager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragement(new EntertainmentFragment(),"Entertainment");
        viewPagerAdapter.addFragement(new HeadlineFragment(),"Headline");
        viewPagerAdapter.addFragement(new SportsFragment(), "Sports");
        viewPagerAdapter.addFragement(new SearchFragment(),"Search");
        viewpager.setAdapter(viewPagerAdapter);
    }

        class ViewPagerAdapter extends FragmentPagerAdapter{
            private final ArrayList<Fragment> fragments = new ArrayList<>();
            private final ArrayList<String> fragmentsTitle = new ArrayList<>();
            public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
                super(fm);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentsTitle.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            public void addFragement(Fragment fragment , String title){
                fragments.add(fragment);
                fragmentsTitle.add(title);
            }




        }

}
