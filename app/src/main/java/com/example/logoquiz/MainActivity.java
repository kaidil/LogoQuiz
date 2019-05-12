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

    public int totalAnswers = 0;

    TextView pointsDisplay;

    int[] imageListLevelOne={

            R.drawable.acer,
            R.drawable.adobe,
            R.drawable.adidas,
            R.drawable.airbnb,
            R.drawable.alibaba,
            R.drawable.apple,
            R.drawable.audi,
            R.drawable.baskinrobbins,
            R.drawable.batman,
            R.drawable.beats,
            R.drawable.blogger,
            R.drawable.bmw,
            R.drawable.bp,
            R.drawable.dc,
            R.drawable.chanel,
            R.drawable.cisco,
            R.drawable.cocacola,
            R.drawable.dish,
            R.drawable.dropbox,
            R.drawable.fedex,
            R.drawable.fila,
            R.drawable.firefox,
            R.drawable.goodwill,
            R.drawable.honda,
            R.drawable.hotelsgroup,
            R.drawable.huawei,
            R.drawable.ikea,
            R.drawable.instagram,
            R.drawable.intel,
            R.drawable.lexus,
            R.drawable.lg,
            R.drawable.linkedin,
            R.drawable.mcdonalds,
            R.drawable.mercedes,
            R.drawable.microsoft,
            R.drawable.mitsubishi,
            R.drawable.nasa,
            R.drawable.nba,
            R.drawable.netflix,
            R.drawable.nike,
            R.drawable.pepsi,
            R.drawable.philadelphiaeagles,
            R.drawable.photoshop,
            R.drawable.pinterest,
            R.drawable.playstation,
            R.drawable.pringles,
            R.drawable.roxy,
            R.drawable.scotiabank,
            R.drawable.sevenup,
            R.drawable.shell,
            R.drawable.singaporeairlines,
            R.drawable.skype,
            R.drawable.snapchat,
            R.drawable.starbucks,
            R.drawable.target,
            R.drawable.telekom,
            R.drawable.total,
            R.drawable.toyota,
            R.drawable.twitter,
            R.drawable.visa,
            R.drawable.volkswagen,
            R.drawable.warnerbrothers,
            R.drawable.wechat,
            R.drawable.whatsapp,
            R.drawable.wikipedia,
            R.drawable.wordpress,
            R.drawable.xbox,
            R.drawable.yahoo
    };

    int[] imageListLevelTwo={
            R.drawable.bipolar,
            R.drawable.londonsymphonyorchestra,
            R.drawable.proctorgamble,
            R.drawable.aglow,
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Toast.makeText(getApplicationContext(), "Correct ! This is "+result, Toast.LENGTH_SHORT).show();

                    endTime = System.nanoTime();
                    countPoints();
                    pointsDisplay = (TextView)findViewById(R.id.pointsDisplay);
                    pointsDisplay.setText("Your points: " + totalPoints);


                    // Reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    // Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(),
                            MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    if (totalPoints > 0) {
                        Toast.makeText(MainActivity.this, "Incorrect!\n You lost 1 point! \n Try again.", Toast.
                                LENGTH_SHORT).show();
                        totalPoints = totalPoints - 1;
                        pointsDisplay.setText("Your points: " + totalPoints);
                    } else {
                        Toast.makeText(MainActivity.this, "Please write your answer!", Toast.
                                LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setupList() {
        //Random logo
        startTime = System.nanoTime();
        System.out.println("Start logo: " + startTime);

        pointsDisplay = (TextView)findViewById(R.id.pointsDisplay);
        pointsDisplay.setText("Your points: " + totalPoints);

        Random random = new Random();

        if(totalAnswers < 2 ){
            int imageSelected = imageListLevelOne[random.nextInt(imageListLevelOne.length)];
            imgViewQuestion.setImageResource(imageSelected);
            correct_answer = getResources().getResourceName(imageSelected);
            correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);
            totalAnswers++;
        } else {
            int imageSelected = imageListLevelTwo[random.nextInt(imageListLevelTwo.length)];
            imgViewQuestion.setImageResource(imageSelected);
            correct_answer = getResources().getResourceName(imageSelected);
            correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);
        }


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
        totalPoints = totalPoints + quizPoints;
    }



    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }
}
