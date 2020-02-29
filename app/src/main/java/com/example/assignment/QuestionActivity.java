package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    private TextView question, noIndictor;
    private LinearLayout optionsContainer;
    private Button cateBtn, nextBtn;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.Toolbar);

        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        noIndictor = findViewById(R.id.no_indicator);
        optionsContainer = findViewById(R.id.options_contrainer);
        cateBtn = findViewById(R.id.cate_btn);
        nextBtn = findViewById(R.id.next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                count = 0;
                playAnim(question, 0);
            }
        });

    }

    private void playAnim(final View view, final int value){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).
                setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4){
                    playAnim(optionsContainer.getChildAt(count),1);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0){
                    playAnim(view, 1);
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



}
