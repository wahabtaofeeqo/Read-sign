package com.taocoder.readsign;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private static Info info;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(Info i) {
        info = i;
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView desc  = (TextView) view.findViewById(R.id.desc);
        AppCompatImageView logo = (AppCompatImageView) view.findViewById(R.id.logo);

        if (info != null) {
            title.setText(info.getTitle());
            int thumb = getLogo(info.getLogo());
            Glide.with(getContext()).load(thumb).into(logo);
            desc.setText(info.getDesc());
        }

        return view;
    }

    private int getLogo(String title) {
        int logo = R.drawable.deer;

        switch (title) {
            case "deer":
                logo = R.drawable.deer;
                break;

            case "emergency":
                logo = R.drawable.emergency;
                break;

            case "keep":
                logo = R.drawable.keepright;
                break;

            case "men":
                logo = R.drawable.men;
                break;

            case "nobicycle":
                logo = R.drawable.nobicycle;
                break;

            case "uturn":
                logo = R.drawable.uturn;
                break;

            case "crossing":
                logo = R.drawable.crossing;
                break;

            case "speed":
                logo = R.drawable.speed;
                break;

            case "stop":
                logo = R.drawable.stop;
                break;

            case "yield":
                logo = R.drawable.yield;
                break;
                
            case "turnonred":
                logo = R.drawable.turnonred;
                break;

            case "nostop":
                logo = R.drawable.nostop;
                break;

            case "noentry":
                logo = R.drawable.noentry;
                break;

            case "ped":
                logo = R.drawable.ped;
                break;

            case "sharp":
                logo = R.drawable.sharp;
                break;

            case "nowaiting":
                logo = R.drawable.nowaiting;
                break;

            case "noover":
                logo = R.drawable.noover;
                break;

            case "mergeleft":
                logo = R.drawable.mergeleft;
                break;

            case "mergeright":
                logo = R.drawable.mergeright;
                break;

            case "bumps":
                logo = R.drawable.bumps;
                break;

            case "bridge":
                logo = R.drawable.bridge;
                break;
        }

        return logo;
    }
}
