package com.example.logoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logoquiz.Adapter.GridViewAnswerAdapter;
import com.example.logoquiz.Adapter.GridViewSuggestAdapter;
import com.example.logoquiz.Common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public Button btnSubmit;

    public GridView gridViewAnswer, gridViewSuggest;

    public ImageView imgViewQuestion;

    public long startTime;
    public long endTime;
    public long timeElapsed;

    public int totalPoints = 0;

    int[] image_list={

            R.drawable.adidas,
            R.drawable.airbnb,
            R.drawable.apple,
            R.drawable.blogger,
            R.drawable.dc,
            R.drawable.honda,
            R.drawable.huawei,
            R.drawable.instagram,
            R.drawable.lexus,
            R.drawable.linkedin,
            R.drawable.mcdonalds,
            R.drawable.mercedes,
            R.drawable.microsoft,
            R.drawable.nba,
            R.drawable.nike,
            R.drawable.pepsi,
            R.drawable.photoshop,
            R.drawable.pinterest,
            R.drawable.pringles,
            R.drawable.shell,
            R.drawable.skype,
            R.drawable.toyota,
            R.drawable.wordpress,
            R.drawable.yahoo
    };

    public char[] answer;

    String correct_answer;

    TextView pointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esialgne vaade
        initView();

    }
    private void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewSuggest);

        imgViewQuestion = (ImageView) findViewById(R.id.imgLogo);

        // Add SetupList here
        setupList();

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                for(int i=0;i< Common.user_submit_answer.length;i++)
                    result+=String.valueOf(Common.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(), "Finish ! This is "+result, Toast.LENGTH_SHORT).show();

                    endTime = System.nanoTime();
                    System.out.println("Hit Submit: " + endTime);

                    countPoints();


                    // Reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    // Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(), MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        //Random logo
        startTime = System.nanoTime();
        System.out.println("Start logo: " + startTime);

        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        // Add answer character to list
        suggestSource.clear();
        for (char item:answer)
        {
            // Add logo name to list
            suggestSource.add(String.valueOf(item));
        }

        // Random add some character to list
        for (int i=answer.length;i<answer.length*2;i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        // Sort random
        Collections.shuffle(suggestSource);

        // Set for GridView
        answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);

    }

    private void countPoints(){
        int quizPoints = 0;
        timeElapsed = (endTime - startTime) / 1000000000;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!TIME DIFFERENCE IS: " + timeElapsed);
        //ArrayList totalPoints = new ArrayList();
        if (timeElapsed <= 1) {
            quizPoints = 20;
        } else if (timeElapsed <= 3) {
            quizPoints = 18;
        } else if (timeElapsed <= 5) {
            quizPoints = 16;
        } else if (timeElapsed <= 7) {
            quizPoints = 14;
        } else if (timeElapsed <= 9) {
            quizPoints = 12;
        } else if (timeElapsed <= 12) {
            quizPoints = 10;
        } else if (timeElapsed <= 15) {
            quizPoints =  8;
        } else if (timeElapsed <= 18) {
            quizPoints = 6;
        } else if (timeElapsed <= 21) {
            quizPoints = 4;
        } else if (timeElapsed > 21) {
            quizPoints = 2;
        }
        System.out.println("Quizpoints: " + quizPoints);
        totalPoints = totalPoints + quizPoints;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!Your points are: " + totalPoints);
    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }
}
