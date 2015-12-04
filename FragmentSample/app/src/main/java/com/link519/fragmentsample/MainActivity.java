package com.link519.fragmentsample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by yusuke on 12/3/15.
 */
public class MainActivity extends Activity {
    private ActionBar actionBar;
    private FragmentListener frgListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();

        if(actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            //MainActivity側でFragmentのインスタンスを作って保持しておく
            Fragment fragment = Fragment.instantiate(this, MyFragment.class.getName());
            frgListener = (FragmentListener)fragment;
            actionBar.addTab(actionBar.newTab().setText("Tab1").setTabListener(
                    new MyTabListener<MyFragment>(fragment, "Tab1")));

            //こちらは従来のやり方
            actionBar.addTab(actionBar.newTab().setText("Tab2").setTabListener(
                    new MyTabListener<MyFragment>("Tab2", MyFragment.class)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem actionItem = menu.add(Menu.NONE, 0, 0, "Send");
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                frgListener.onDataChanged("MainActivity"); //データの変更(など)をFragmentに伝える
                break;
            default:
                break;
        }
        return true;
    }

    class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private String tag;
        private Class<T> cls;

        /**
         * リスナー側でフラグメントをインスタンス化する場合
         * @param tag
         * @param cls 表示するFragment(名前を取得するのに必要)
         */
        MyTabListener(String tag, Class cls) {
            this.tag = tag;
            this.cls = cls;
        }

        /**
         * MainActivityでFragmentのインスタンスを既に生成してある場合
         * @param fragment 表示するFragment
         * @param tag
         */
        MyTabListener(Fragment fragment, String tag) {
            this.mFragment = fragment;
            this.tag = tag;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            if(mFragment == null) {
                //従来はここでFragmentのインスタンスを生成
                mFragment = Fragment.instantiate(MainActivity.this, cls.getName());
                FragmentManager fm = MainActivity.this.getFragmentManager();
                fm.beginTransaction().add(android.R.id.content, mFragment, tag).commit();
            } else {
                if (mFragment.isDetached()) {
                    FragmentManager fm = MainActivity.this.getFragmentManager();
                    fm.beginTransaction().attach(mFragment).commit();
                } else {
                    FragmentManager fm = MainActivity.this.getFragmentManager();
                    fm.beginTransaction().add(android.R.id.content, mFragment, tag).commit();
                }
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (mFragment != null) {
                    FragmentManager fm = MainActivity.this.getFragmentManager();
                    fm.beginTransaction().detach(mFragment).commit();
                }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }

}
