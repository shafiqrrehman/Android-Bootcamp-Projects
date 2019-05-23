package com.example.android.criminalintent.view.abstraction_implementation;

import android.support.v4.app.Fragment;

import com.example.android.criminalintent.controller.CrimeListFragment;
import com.example.android.criminalintent.view.abstraction.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
