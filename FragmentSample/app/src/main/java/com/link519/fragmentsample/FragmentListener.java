package com.link519.fragmentsample;

/**
 * Created by yusuke on 12/3/15.
 *
 * データが変更されたことをFragmentに伝えるためのListener
 */
public interface FragmentListener {
    void onDataChanged(String from);
}
