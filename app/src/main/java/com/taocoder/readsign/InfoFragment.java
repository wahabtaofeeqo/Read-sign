package com.taocoder.readsign;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Info> infos;
    private InfoAdapter adapter;

    private ProgressBar progressBar;

    private OnFragmentChangeListener listener;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnFragmentChangeListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        infos = new ArrayList<>();
        adapter = new InfoAdapter(getContext(), infos, listener);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        loadInfo();
        return view;
    }

    private void loadInfo() {

        String data = loadJSONFromAsset();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray("informs");
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                Info info = new Info();
                info.setTitle(row.getString("title"));
                info.setDesc(row.getString("description"));
                info.setLogo(row.getString("logo"));

                infos.add(info);
            }

            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("infos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
