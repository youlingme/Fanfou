package youlingme.com.fanfou.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import youlingme.com.fanfou.R;
import youlingme.com.fanfou.adapter.PagerAdapter;
import youlingme.com.fanfou.fragment.FragmentDaily;
import youlingme.com.fanfou.fragment.FragmentWeekly;
import youlingme.com.fanfou.model.Date;

public class UIHOME extends AppCompatActivity {

    public static ViewPager viewPager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    FragmentDaily fragmentDaily = new FragmentDaily();
    FragmentWeekly fragmentWeekly = new FragmentWeekly();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentDaily, getString(R.string.daily));
        adapter.addFragment(fragmentWeekly, getString(R.string.weekly));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        floatingActionButton.show();
                        break;
                    default:
                        floatingActionButton.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, UIPreference.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment datePicker = (DialogFragment) getSupportFragmentManager().findFragmentByTag("datePicker");
        if (datePicker == null) {
            datePicker = new FragmentDatePicker();
        }
        if (!datePicker.isAdded()) {
            datePicker.show(getSupportFragmentManager(), "datePicker");
        }
//        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    public static class FragmentDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            KLog.i("onDateSet", year, month, dayOfMonth);
            Calendar pickDate = Calendar.getInstance();
            pickDate.set(year, month, dayOfMonth);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fdate = format.format(pickDate.getTime());
            Date date = new Date(fdate);
            KLog.i("onDateSet", date.toString());
            EventBus.getDefault().post(date);
        }
    }


}
