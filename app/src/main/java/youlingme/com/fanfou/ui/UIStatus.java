package youlingme.com.fanfou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import youlingme.com.fanfou.R;
import youlingme.com.fanfou.model.Fanfou;

/**
 * Created by wanghan on 16/11/24.
 */
public class UIStatus extends AppCompatActivity {

    @Bind(R.id.statustoolbar)
    Toolbar mToolbar;
    @Bind(R.id.avatar1)
    CircleImageView avatar1;
    @Bind(R.id.name1)
    TextView name1;
    @Bind(R.id.timestamp1) TextView timestamp1;
    @Bind(R.id.status1) TextView status1;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.imageprogress)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        Fanfou feed = (Fanfou)intent.getParcelableExtra("feed");

        name1.setText(feed.getScreenName());
        timestamp1.setText(feed.getTimeStamp());
        status1.setText(feed.getStatus());

        Picasso.with(this).load(feed.getAvatarUrl()).into(avatar1);

        if (!feed.getImageUrl().equals("")) {
            Picasso.with(this).load(feed.getImageUrl()).into(image1, new ImageLoadedCallback(mProgressBar) {
                @Override
                public void onSuccess() {
                    super.onSuccess();
                    if (this.mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
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

    private class ImageLoadedCallback implements Callback {

        ProgressBar mProgressBar;

        public ImageLoadedCallback(ProgressBar progressBar) {
            mProgressBar = progressBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }


}
