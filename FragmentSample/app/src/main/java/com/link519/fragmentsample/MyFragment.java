package com.link519.fragmentsample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yusuke on 12/3/15.
 */
public class MyFragment extends Fragment implements FragmentListener {
    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return layout = inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onDataChanged(String from) {
        ((TextView)layout.findViewById(R.id.textview)).setText("hello from " + from);
    }
}
