package com.rany.albeg.wein.fragmentbackstack;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener, AdapterBackStack.MyViewHolder.OnStackEntryClickedListener {

    private static final String TAG_FRAGMENT_A = "Tag_FragmentA";
    private static final String TAG_FRAGMENT_B = "Tag_FragmentB";
    private static final String TAG_FRAGMENT_C = "Tag_FragmentC";

    private static final int DURATION_STACK_ANIMATION = 400;

    private FragmentManager mFragmentManager;
    private AdapterBackStack mAdapterBackStack;

    private int mCurrentBackStackEntryCount = 0;
    private RecyclerView mRecyclerViewBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mFragmentManager = getFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        findViewById(R.id.bt_add_a).setOnClickListener(this);
        findViewById(R.id.bt_replace_with_a).setOnClickListener(this);
        findViewById(R.id.bt_remove_a).setOnClickListener(this);
        findViewById(R.id.bt_add_b).setOnClickListener(this);
        findViewById(R.id.bt_replace_with_b).setOnClickListener(this);
        findViewById(R.id.bt_remove_b).setOnClickListener(this);
        findViewById(R.id.bt_add_c).setOnClickListener(this);
        findViewById(R.id.bt_replace_with_c).setOnClickListener(this);
        findViewById(R.id.bt_remove_c).setOnClickListener(this);

        mRecyclerViewBackStack = (RecyclerView) findViewById(R.id.rv_back_stack);
        mRecyclerViewBackStack.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        LandingAnimator animator = new LandingAnimator();
        animator.setAddDuration(DURATION_STACK_ANIMATION);
        animator.setRemoveDuration(DURATION_STACK_ANIMATION);

        mRecyclerViewBackStack.setItemAnimator(animator);
        mAdapterBackStack = new AdapterBackStack(this, this);
        mRecyclerViewBackStack.setAdapter(mAdapterBackStack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_a:
                addA();
                break;
            case R.id.bt_replace_with_a:
                replaceWithA();
                break;
            case R.id.bt_remove_a:
                removeA();
                break;
            case R.id.bt_add_b:
                addB();
                break;
            case R.id.bt_replace_with_b:
                replaceWithB();
                break;
            case R.id.bt_remove_b:
                removeB();
                break;
            case R.id.bt_add_c:
                addC();
                break;
            case R.id.bt_replace_with_c:
                replaceWithC();
                break;
            case R.id.bt_remove_c:
                removeC();
                break;
        }
    }

    private void addC() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragments_container, new FragmentC(), TAG_FRAGMENT_C)
                .addToBackStack(getString(R.string.plus_c))
                .commit();
    }

    private void replaceWithC() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragments_container, new FragmentC(), TAG_FRAGMENT_C)
                .addToBackStack(getString(R.string.tilda_c))
                .commit();
    }

    private void removeC() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG_FRAGMENT_C);

        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.remove(fragment)
                    .addToBackStack(getString(R.string.minus_c))
                    .commit();
        }
    }

    private void addA() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragments_container, new FragmentA(), TAG_FRAGMENT_A)
                .addToBackStack(getString(R.string.plus_a))
                .commit();
    }

    private void replaceWithA() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragments_container, new FragmentA(), TAG_FRAGMENT_A)
                .addToBackStack(getString(R.string.tilda_a))
                .commit();
    }

    private void removeA() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG_FRAGMENT_A);

        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.remove(fragment)
                    .addToBackStack(getString(R.string.minus_a))
                    .commit();
        }
    }

    private void addB() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_fragments_container, new FragmentB(), TAG_FRAGMENT_B)
                .addToBackStack(getString(R.string.plus_b))
                .commit();
    }

    private void replaceWithB() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragments_container, new FragmentB(), TAG_FRAGMENT_B)
                .addToBackStack(getString(R.string.tilda_b))
                .commit();
    }

    private void removeB() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG_FRAGMENT_B);
        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.remove(fragment)
                    .addToBackStack(getString(R.string.minus_b))
                    .commit();
        }
    }

    @Override
    public void onBackStackChanged() {

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();

        int itemCount = mAdapterBackStack.getItemCount();

        // A BackStack entry was added.
        if (mCurrentBackStackEntryCount < backStackEntryCount) {
            FragmentManager.BackStackEntry lastEntry = mFragmentManager.getBackStackEntryAt(backStackEntryCount - 1);
            mAdapterBackStack.push(lastEntry.getName());
            mAdapterBackStack.notifyItemInserted(itemCount + 1);
            mRecyclerViewBackStack.smoothScrollToPosition(itemCount);
        } else {
            mAdapterBackStack.pop();
            mAdapterBackStack.notifyItemRemoved(itemCount - 1);
        }

        mCurrentBackStackEntryCount = backStackEntryCount;

    }

    @Override
    public void onStackEntryClick(int adapterPosition) {
        int itemCount = mAdapterBackStack.getItemCount();
        if (adapterPosition >= 0 && adapterPosition < itemCount) {
            for (int i = itemCount - 1; i >= adapterPosition; --i) {
                mFragmentManager.popBackStack();
            }
        }
    }
}
