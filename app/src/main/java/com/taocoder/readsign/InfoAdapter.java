package com.taocoder.readsign;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private Context context;
    private List<Info> infos;
    private Info info;
    private OnFragmentChangeListener listener;

    public InfoAdapter(Context context, List<Info> infos, OnFragmentChangeListener listener) {
        this.context = context;
        this.infos = infos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info = infos.get(position);
        holder.title.setText(info.getTitle());
        int logo = getLogo(info.getLogo());
        Glide.with(context).load(logo).into(holder.logo);
        info.setThumb(logo);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFragmentChange(DetailsFragment.getInstance(info));
                //Utils.showMessage(context, info.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    private int getLogo(String title) {

        int logo = R.drawable.deer;

        switch (title) {
            case "deer":
                logo = R.drawable.deer;
                info.setThumb(R.drawable.deer);
                break;

            case "emergency":
                logo = R.drawable.emergency;
                info.setThumb(R.drawable.emergency);
                break;

            case "keep":
                logo = R.drawable.keepright;
                info.setThumb(R.drawable.keepright);
                break;

            case "men":
                logo = R.drawable.men;
                info.setThumb(R.drawable.men);
                break;

            case "nobicycle":
                logo = R.drawable.nobicycle;
                info.setThumb(R.drawable.nobicycle);
                break;

            case "uturn":
                logo = R.drawable.uturn;
                info.setThumb(R.drawable.uturn);
                break;

            case "crossing":
                logo = R.drawable.crossing;
                info.setThumb(R.drawable.crossing);
                break;

            case "speed":
                logo = R.drawable.speed;
                info.setThumb(R.drawable.speed);
                break;

            case "stop":
                logo = R.drawable.stop;
                info.setThumb(R.drawable.stop);
                break;

            case "yield":
                logo = R.drawable.yield;
                info.setThumb(R.drawable.yield);
                break;
        }

        return logo;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        private TextView title;
        private AppCompatImageView logo;

        public ViewHolder(View view) {
            super(view);
            v = view;
            title = (TextView) view.findViewById(R.id.title);
            logo  = (AppCompatImageView) view.findViewById(R.id.logo);
        }
    }
}
