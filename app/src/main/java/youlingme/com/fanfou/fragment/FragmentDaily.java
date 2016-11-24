package youlingme.com.fanfou.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import youlingme.com.fanfou.MyApplication;
import youlingme.com.fanfou.R;
import youlingme.com.fanfou.adapter.FanfouAdapter;
import youlingme.com.fanfou.callbacks.RecyclerItemClickListener;
import youlingme.com.fanfou.extras.FanfouUtils;
import youlingme.com.fanfou.model.Date;
import youlingme.com.fanfou.model.Fanfou;
import youlingme.com.fanfou.ui.UIStatus;
import youlingme.com.fanfou.utils.DateUtils;
import youlingme.com.fanfou.utils.HttpUtils;
import youlingme.com.fanfou.volley.VolleySingleton;

public class FragmentDaily extends Fragment {

    private static final String DAILY_FANFOU = "daily_fanfou";

    @Bind(R.id.swipeFanfous)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyvlerview)
    RecyclerView mRecyclerView;

    private String temp = DateUtils.getCurrentDate();

//    private ArrayList<Fanfou> listFanfous = new ArrayList<>();
    private ArrayList<Fanfou> listFanfous;
    private FanfouAdapter mFanfouAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        listFanfous = new ArrayList<>();

        setupSwipeRefreshLayout();
        setupRecyclerView();

        EventBus.getDefault().register(this);

        if (savedInstanceState != null) {
            listFanfous = savedInstanceState.getParcelableArrayList(DAILY_FANFOU);
        } else {
            if (listFanfous.isEmpty()) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (HttpUtils.isNetworkConnected(getContext())) {
//                            mSwipeRefreshLayout.setRefreshing(true);
                            Snackbar.make(view, getString(R.string.action_loading) + temp, Snackbar.LENGTH_LONG).show();
                            fetchData(temp);
                        } else {
                            Snackbar.make(view, getString(R.string.network_notworking), Snackbar.LENGTH_LONG).show();
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });
            }
        }

        return view;
    }

    private void fetchData(String date) {
        KLog.i(date);
//        mSwipeRefreshLayout.setRefreshing(true);

//        String url = UriUtils.getReuqestUrlHead() + date + UriUtils.getRequestUrlDailyEnd();
//        new TaskLoadFanfouDaily(date).execute(url);

        new TaskLoadFanfouDaily(date).execute();
        mFanfouAdapter.setFanfous(listFanfous);
    }

    private void setupRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(position);
            }
        }));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mFanfouAdapter = new FanfouAdapter(getContext());
        mRecyclerView.setAdapter(mFanfouAdapter);
    }

    private void startActivity(int position) {
        Intent intent = new Intent(getActivity(), UIStatus.class);
        Fanfou feed = listFanfous.get(position);
        intent.putExtra("feed", feed);
        startActivity(intent);
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HttpUtils.isNetworkConnected(getContext())) {
                            Snackbar.make(mRecyclerView, getString(R.string.action_loading) + temp, Snackbar.LENGTH_LONG).show();
                            fetchData(temp);
                        } else {
                            Snackbar.make(getView(), getString(R.string.network_notworking), Snackbar.LENGTH_LONG).show();
                            if (mSwipeRefreshLayout.isRefreshing()) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                }, 2500);
            }
        });
    }


    public class TaskLoadFanfouDaily extends AsyncTask<String, Void, ArrayList<Fanfou>> {

        private final String date;
        private VolleySingleton mVolleySingleton;
        private RequestQueue mRequestQueue;

        public TaskLoadFanfouDaily(String date) {
            this.date = date;
//            mVolleySingleton = VolleySingleton.getInstance();
//            mRequestQueue = mVolleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
            listFanfous.clear();
        }

        @Override
        protected ArrayList<Fanfou> doInBackground(String... params) {
//            listFanfous = FanfouUtils.loadFanfouDailyFeeds(mRequestQueue, date);
//            return 1;

            listFanfous = FanfouUtils.loadFanfouDailyFeeds(mRequestQueue, date);

            return FanfouUtils.loadFanfouDailyFeeds(mRequestQueue, date);

//            return FanfouUtils.loadFanfouFeed(params[0], mRequestQueue);
        }

        @Override
        protected void onPostExecute(ArrayList<Fanfou> result) {
//            if (result == 1) {
//                mFanfouAdapter.setFanfous(listFanfous);
//                if (mSwipeRefreshLayout.isRefreshing()) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }

            mFanfouAdapter.setFanfous(result);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (listFanfous.isEmpty()) {
//            fetchData(temp);
//        } else {
//            KLog.i(listFanfous);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Date date) {
        MyApplication.setDate(date);
//        Snackbar.make(mRecyclerView, getString(R.string.action_loading) + MyApplication.getDate().getDate(), Snackbar.LENGTH_LONG).show();
        Snackbar.make(mRecyclerView, getString(R.string.action_loading) + date.getDate(), Snackbar.LENGTH_LONG).show();
        fetchData(MyApplication.getDate().getDate());
    }

}
