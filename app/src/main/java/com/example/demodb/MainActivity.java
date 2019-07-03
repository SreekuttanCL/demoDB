package com.example.demodb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView input, output;
    Button rock, paper, scissors, btnScore, add;
    int[] images = new int[]{
            R.mipmap.rock,
            R.mipmap.paper,
            R.mipmap.scissors
    };
    int userinput = 0;
    int humanScore, compScore;
    ListView listViewScore;
    //EditText scoreview;

    DatabaseReference dbScore;
    List<TotalScore> scrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.iv_input);
        output = findViewById(R.id.iv_output);
        rock = findViewById(R.id.btn_rock);
        paper = findViewById(R.id.btn_paper);
        scissors = findViewById(R.id.btn_scissors);
        //btnScore = findViewById(R.id.btnScore);
        listViewScore = findViewById(R.id.listViewScore);
       // scoreview = findViewById(R.id.scoreview);
        add = findViewById(R.id.add);

        rock.setOnClickListener( this);
        paper.setOnClickListener(this);
        scissors.setOnClickListener(this);

        dbScore = FirebaseDatabase.getInstance().getReference("Scores");
        scrs = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                scrs.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    TotalScore scr = postSnapshot.getValue(TotalScore.class);
                    scrs.add(scr);
                }

                ScoreList scoreAdapter = new ScoreList(MainActivity.this, scrs);
                listViewScore.setAdapter(scoreAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void onClick(View v){

        int id = v.getId();
        switch (id) {
            case R.id.btn_rock:
                userinput = 1;
                input.setBackgroundResource(R.mipmap.rock);
                setOutput();
                break;

            case R.id.btn_paper:
                userinput = 2;
                input.setBackgroundResource(R.mipmap.paper);
                setOutput();
                break;

            case R.id.btn_scissors:
                userinput = 3;
                input.setBackgroundResource(R.mipmap.scissors);
                setOutput();
                break;

        }
    }

    private void setOutput() {

        int imageId = (int) (Math.random() * images.length);
        output.setBackgroundResource(images[imageId]);
        checkresult(imageId);
    }

    private void checkresult(int imageId) {
        if (userinput == 1 && imageId == 0) {
            showresult(2);
        }
        else if (userinput == 1 && imageId == 1) {
            showresult(0);
        }
        else if (userinput == 1 && imageId == 2) {
            showresult(1);
        }
        else if (userinput == 2 && imageId == 0) {
            showresult(1);
        }
        else if (userinput == 2 && imageId == 1) {
            showresult(2);
        }
        else if (userinput == 2 && imageId == 2) {
            showresult(0);
        }
        else if (userinput == 3 && imageId == 0) {
            showresult(0);
        }
        else if (userinput == 3 && imageId == 1) {
            showresult(1);
        }
        else if (userinput == 3 && imageId == 2) {
            showresult(2);
        }
    }

    private void showresult(int result) {

        if (result == 0) {
            humanScore += 0;
            compScore += 2;
            String msg = "Score Human" + (humanScore) + "Computer" + (compScore);
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
           // scoreview.setText(msg);
            //scoreCalc(0);
            scoreMsg(msg);
        } else if (result == 1) {
            humanScore += 2;
            compScore += 0;
            String msg = "Score Human" + (humanScore) + "Computer" + (compScore);
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            //scoreview.setText(msg);
            scoreMsg(msg);
        } else {
            humanScore += 1;
            compScore += 1;
            String msg = "Score Human" + (humanScore) + "Computer" + (compScore);
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            //scoreview.setText(msg);
            scoreMsg(msg);
        }
    }

    public void scoreMsg(String msg) {
        String message = msg;//scoreview.getText().toString().trim();
        String id = dbScore.push().getKey();
        TotalScore ts = new TotalScore(id, message);
        dbScore.child(id).setValue(ts);
    }

}
