package m7edshin.popularmovieapp.CustomAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.ui.PlayerUIController;

import java.util.List;

import m7edshin.popularmovieapp.Models.MovieExtraDetails;
import m7edshin.popularmovieapp.R;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Mohamed Shahin on 02/03/2018.
 * Data holder for Trailer details
 */

public class TrailersRecyclerAdapter extends RecyclerView.Adapter<TrailersRecyclerAdapter.TrailersHolder> {

    private List<String> videosKeyList;

    public TrailersRecyclerAdapter(List<String> videosKeyList) {
        this.videosKeyList = videosKeyList;
    }

    @Override
    public TrailersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_recycler_item, parent,false);
        return new TrailersHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrailersHolder holder, final int position) {
        holder.youtube_player_view.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoKey = videosKeyList.get(position);
                        initializedYouTubePlayer.cueVideo(videoKey, 0);
                    }
                });
            }
        }, true);
    }

    @Override
    public int getItemCount() {
        return videosKeyList.size();
    }

    public class TrailersHolder extends RecyclerView.ViewHolder{

        private YouTubePlayerView youtube_player_view ;

        public TrailersHolder(View itemView) {
            super(itemView);
            youtube_player_view = itemView.findViewById(R.id.youtube_player_view);
        }
    }
}
