package com.taocoder.readsign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private String answer;
    private MaterialRadioButton radioButton;
    private Question current;
    int index = 0;

    private List<Question> questions;
    private TextView scoreText;

    private AppCompatImageView logo;
    private MaterialRadioButton a;
    private MaterialRadioButton b;
    private MaterialRadioButton c;
    private MaterialRadioButton d;
    private TextView question;

    private int score = 0;
    private int skipCount = 0;

    private SessionManager sessionManager;
    private OnFragmentChangeListener listener;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnFragmentChangeListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        scoreText = (TextView) view.findViewById(R.id.score);

        questions = new ArrayList<>();
        question = (TextView) view.findViewById(R.id.question);
        logo = (AppCompatImageView) view.findViewById(R.id.logo);
        a = (MaterialRadioButton) view.findViewById(R.id.a);
        b = (MaterialRadioButton) view.findViewById(R.id.b);
        c = (MaterialRadioButton) view.findViewById(R.id.c);
        d = (MaterialRadioButton) view.findViewById(R.id.d);

        final MaterialButton skip = (MaterialButton) view.findViewById(R.id.skip);
        final MaterialButton next = (MaterialButton) view.findViewById(R.id.next);

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.questionGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = view.findViewById(i);
                if (radioButton != null) {
                    answer = radioButton.getText().toString();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer != null) {

                    if (answer.equalsIgnoreCase(current.getAnswer())) {
                        score += 10;
                        setScore(score);
                    }

                    int temp = index + 1;
                    if (temp == questions.size()) {
                        responseDialog("Your Score For the Quiz is :" + score);
                        sessionManager.setHighest(score);
                        next.setEnabled(false);
                    }
                    else {
                        index++;
                        current = questions.get(index);
                        setQuestion(current);
                        radioGroup.clearCheck();
                    }
                }
                else {
                    Utils.showMessage(getContext(), "Select Your Answer");
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (skipCount < 3) {
                    skipCount++;
                    index++;
                    if (index <= questions.size() - 1) {
                        setQuestion(questions.get(index));
                    }
                }
                else {
                    Utils.showMessage(getContext(), "You have reached the highest skip level");
                }
            }
        });

        loadQuestions();

        return view;
    }

    private void setScore(int s) {
        scoreText.setText(getResources().getString(R.string.score, s));
    }

    private void setQuestion(Question q) {

        int t = getLogo(q.getLogo());
        logo.setImageResource(t);
        question.setText(q.getQuestion());
        a.setText(q.getOptionA());
        b.setText(q.getOptionB());
        c.setText(q.getOptionC());
        d.setText(q.getOptionD());
    }

    private void loadQuestions() {

        String data = loadJSONFromAsset();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray("questions");
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                Question q = new Question();
                q.setTitle("Road Quiz");
                q.setLogo(row.getString("logo"));
                q.setQuestion(row.getString("q"));
                q.setOptionA(row.getString("option1"));
                q.setOptionB(row.getString("option2"));
                q.setOptionC(row.getString("option3"));
                q.setOptionD(row.getString("option4"));
                q.setAnswer(row.getString("answer"));
                questions.add(q);
            }

            Log.i("RES", questions.toString());

            if (questions.size() > 0) {
                current = questions.get(index);
                setQuestion(current);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("questions.json");
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

    private int getLogo(String title) {
        int logo = R.drawable.deer;
        switch (title) {
            case "national":
                logo = R.drawable.national;
                break;

            case "pedestrian":
                logo = R.drawable.pedestrian;
                break;

            case "traffic":
                logo = R.drawable.traffic;
                break;

            case "slippery":
                logo = R.drawable.slippery;
                break;

            case "restriction":
                logo = R.drawable.restriction;
                break;

            case "turn":
                logo = R.drawable.turn;
                break;

            case "route":
                logo = R.drawable.route;
                break;

            case "vehicle":
                logo = R.drawable.vehicle;
                break;

            case "bicycle":
                logo = R.drawable.bicycle;
                break;

            case "two":
                logo = R.drawable.two;
                break;

            case "high":
                logo = R.drawable.high;
                break;
        }

        return logo;
    }

    private void responseDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("Game Over!");
        dialog.setMessage(message);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onFragmentChange(new HomeFragment());
            }
        });

        dialog.show();
    }
}
