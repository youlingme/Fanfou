package youlingme.com.fanfou.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import youlingme.com.fanfou.R;
import youlingme.com.fanfou.adapter.FanfouAdapter;
import youlingme.com.fanfou.callbacks.RecyclerItemClickListener;
import youlingme.com.fanfou.extras.FanfouUtils;
import youlingme.com.fanfou.model.Fanfou;
import youlingme.com.fanfou.ui.UIStatus;
import youlingme.com.fanfou.utils.DateUtils;
import youlingme.com.fanfou.volley.VolleySingleton;

/**
 */
public class FragmentWeekly extends Fragment {

    private static final String WEEKLY_FANFOU = "weekly_fanfou";

    @Bind(R.id.swipeFanfous)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyvlerview)
    RecyclerView recyclerView;

    private String temp = DateUtils.getCurrentMonday();
    private ArrayList<Fanfou> listFanfous = new ArrayList<>();
    private FanfouAdapter fanfouAdapter;

    public FragmentWeekly() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        fanfouAdapter = new FanfouAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(fanfouAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.red, R.color.green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new TaskLoadFanfouWeekly().execute();
                    }
                }, 2500);
            }
        });

        if (savedInstanceState != null) {
            listFanfous = savedInstanceState.getParcelable(WEEKLY_FANFOU);
        } else {
            if (listFanfous.isEmpty()) {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        new TaskLoadFanfouWeekly().execute();
                    }
                });
                fanfouAdapter.setFanfous(listFanfous);
            }
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(position);
            }
        }));

        return view;
    }

    private void startActivity(int position) {
        Intent intent = new Intent(getActivity(), UIStatus.class);
        Fanfou feed = listFanfous.get(position);
        intent.putExtra("feed", feed);
        startActivity(intent);
    }

    public class TaskLoadFanfouWeekly extends AsyncTask<Void, Void, List<Fanfou>> {

        private VolleySingleton volleySingleton;
        private RequestQueue requestQueue;

        public TaskLoadFanfouWeekly(){
            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getRequestQueue();
        }

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
            listFanfous.clear();
        }

        @Override
        protected List<Fanfou> doInBackground(Void... params) {
            ArrayList<Fanfou> fanfous = FanfouUtils.loadFanfouWeeklyFeeds(requestQueue, temp);
            listFanfous = fanfous;
            return fanfous;
        }

        @Override
        protected void onPostExecute(List<Fanfou> fanfous) {
            fanfouAdapter.setFanfous(fanfous);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(WEEKLY_FANFOU, listFanfous);
    }
}
