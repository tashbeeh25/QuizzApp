package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question, noIndictor;
    private LinearLayout optionsContainer;
    private Button sharebtn, nextBtn;
    private int count = 0;
    private List<QuestionMode> list, listTemp;
    private int position = 0;
    private static int score = 0;
    private String category;
    private  int setNo;
    private Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.Toolbar);

        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        noIndictor = findViewById(R.id.no_indicator);
        optionsContainer = findViewById(R.id.options_contrainer);
        sharebtn = findViewById(R.id.share_btn);
        nextBtn = findViewById(R.id.next_btn);

        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo", 1);

        loading = new Dialog(this);
        loading.setContentView(R.layout.loading);
        loading.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loading.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loading.setCancelable(false);

        list = new ArrayList<>();
        listTemp = new ArrayList<>();

        loading.show();

        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(QuestionMode.class));
                }
                if (list.size() > 0){
                    for (int i = 0; i < 4; i++){
                        optionsContainer.getChildAt(i).setOnClickListener((v) ->{
                                checkAnswer((Button)v);
                        });
                    }
                    playAnim(question, 0, list.get(position).getQuestion());
                    nextBtn.setOnClickListener((v) ->{
//                        questionCount += 1;
                        nextBtn.setEnabled(false);
                        nextBtn.setAlpha(0.7f);
//                        enableOption(true);
                        position++;
                        if (position == list.size()){
//
                            Intent questionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                            questionIntent.putExtra("category", category);
                            questionIntent.putExtra("setNo", ++setNo);
                            if(setNo == 6) {
                                Intent scoreInt = new Intent(QuestionActivity.this, ScoreActivity.class );
                                scoreInt.putExtra("Score", score);
                                scoreInt.putExtra("Total", list.size());
                                QuestionActivity.this.startActivity(scoreInt);
                                score = 0;   //resetting score for other categories.
                                questionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                            } else {
                                finish();
                                QuestionActivity.this.startActivity(questionIntent);
                                //startActivity(questionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

                            }
                            return;
                        }
                        count = 0;
                        playAnim(question, 0, list.get(position).getQuestion());

                    });
                }else {
                    finish();
                    Toast.makeText(QuestionActivity.this, "No question", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();

                sharebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String body = list.get(position).getQuestion() +
                                list.get(position).getOptionA() +
                                list.get(position).getOptionB() +
                                list.get(position).getOptionC() +
                                list.get(position).getOptionD();
                        Intent shareInt = new Intent(Intent.ACTION_SEND);
                        shareInt.setType("plain/text");
                        shareInt.putExtra(Intent.EXTRA_SUBJECT, "Quizzer challenge");
                        shareInt.putExtra(Intent.EXTRA_TEXT, body);
                        startActivity(Intent.createChooser(shareInt,"share via"));
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
                finish();
            }
        });
    }

    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4){
                    String option = "";
                    if (count == 0){
                        option = list.get(position).getOptionA();
                    }else if (count == 1){
                        option = list.get(position).getOptionB();
                    }else if (count == 2){
                        option = list.get(position).getOptionC();
                    }else if (count == 3){
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionsContainer.getChildAt(count),0, option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0){
                    try{
                        ((TextView) view).setText(data);
                        noIndictor.setText(position+1+"/"+list.size());
                    }catch(ClassCastException e){
                        ((Button) view).setText(data);

                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button selectOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        Log.d("-----> "+selectOption.getText().toString()," -----> "+list.get(position).getCorrectANS());
        if (selectOption.getText().toString().equals((list.get(position).getCorrectANS()))){
            //correct
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#178f01")));

        }else{
            //incorrect
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#db0f0f")));
            Button correctOption = (Button) optionsContainer.findViewWithTag(list.get(position).getCorrectANS());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#178f01")));
        }
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setEnabled(enable);
            if (enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A0750")));
            }
        }
    }
}
