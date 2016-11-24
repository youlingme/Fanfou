package youlingme.com.fanfou.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import youlingme.com.fanfou.R;
import youlingme.com.fanfou.model.Fanfou;

/**
 * Created by wanghan on 16/11/23.
 */
public class FanfouAdapter extends RecyclerView.Adapter<FanfouAdapter.FanfouViewHolder>{

    private final LayoutInflater mInflater;
    private List<Fanfou> mFanfouList = new ArrayList<>();

    public FanfouAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setFanfous(List<Fanfou> listFanfous) {
        this.mFanfouList = listFanfous;
        notifyDataSetChanged();
    }

    @Override
    public FanfouViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card_list_item, parent, false);
        FanfouViewHolder holder = new FanfouViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(FanfouAdapter.FanfouViewHolder fanfouViewHolder, int position) {
        Fanfou current = mFanfouList.get(position);

        Uri avatar_uri = Uri.parse(current.getAvatarUrl());
        Context context = fanfouViewHolder.vAvatar.getContext();
        Picasso.with(context).load(avatar_uri).into(fanfouViewHolder.vAvatar);

        fanfouViewHolder.vScreenName.setText(current.getScreenName());
        fanfouViewHolder.vStatus.setText(current.getStatus());
        fanfouViewHolder.mFanfou = current;
        fanfouViewHolder.vImageflag.setImageResource(R.drawable.ic_photo_24dp);

        if (current.getImageUrl().equals("")) {
            fanfouViewHolder.vImageflag.setImageDrawable(null);
        }

    }

    @Override
    public int getItemCount() {
        return mFanfouList.size();
    }

    class FanfouViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar)
        CircleImageView vAvatar;
        @Bind(R.id.name)
        TextView vScreenName;
        @Bind(R.id.timestamp)
        TextView vStatus;
        @Bind(R.id.imageflag)
        ImageView vImageflag;

        Fanfou mFanfou;
        View feed;

        public FanfouViewHolder(View itemView) {
            super(itemView);
            feed = itemView;
            ButterKnife.bind(this, itemView);
        }
    }


}
