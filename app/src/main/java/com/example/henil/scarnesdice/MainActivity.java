package com.example.henil.scarnesdice;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int userover=0,userturn=0,compover=0,compturn=0;
    public Button roll,hold,reset;
    public TextView uscore,cscore,utscore,ctscore,usvalue,utsvalue,csvalue,ctsvalue;
    public ImageView dice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll=(Button) findViewById(R.id.roll);
        hold=(Button) findViewById(R.id.hold);
        reset=(Button) findViewById(R.id.reset);
        uscore=(TextView) findViewById(R.id.uscore);
        cscore=(TextView) findViewById(R.id.cscore);
        utscore=(TextView) findViewById(R.id.utscore);
        ctscore=(TextView) findViewById(R.id.ctscore);
        usvalue=(TextView) findViewById(R.id.usvalue);
        csvalue=(TextView) findViewById(R.id.csvalue);
        utsvalue=(TextView) findViewById(R.id.utsvalue);
        ctsvalue=(TextView) findViewById(R.id.ctsvalue);
        dice=(ImageView) findViewById(R.id.dice);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRoll();
            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userHold();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }
    void setDice(int face) {
        switch(face) {
            case 1: ImageViewAnimatedChange(this,dice,R.drawable.dice1);
                break;
            case 2: ImageViewAnimatedChange(this,dice,R.drawable.dice2);
                break;
            case 3: ImageViewAnimatedChange(this,dice,R.drawable.dice3);
                break;
            case 4: ImageViewAnimatedChange(this,dice,R.drawable.dice4);
                break;
            case 5: ImageViewAnimatedChange(this,dice,R.drawable.dice5);
                break;
            case 6: ImageViewAnimatedChange(this,dice,R.drawable.dice6);
                break;
        }
    }
    void userRoll() {
        Random r=new Random();
        int i=r.nextInt(6)+1;
        setDice(i);
        if(i!=1) {
            userturn=userturn+i;
            String value1=Integer.toString(userturn);
            utsvalue.setText(value1);
        }
        else {
            userturn=0;
            utsvalue.setText("0");
            Context context = getApplicationContext();
            CharSequence text = "You Roll 1";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            comp();
        }
    }
    void userHold() {
        userover=userover+userturn;
        userturn=0;
        String value2=Integer.toString(userover);
        usvalue.setText(value2);
        utsvalue.setText("0");
        if(checkScore(userover,1,0)) {}
        else {
            Context context = getApplicationContext();
            CharSequence text = "You Hold";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            comp();
        }

    }
    void comp() {
        hold.setEnabled(false);
        roll.setEnabled(false);
        hold.setClickable(false);
        roll.setClickable(false);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int j=rand.nextInt(6)+1;
                setDice(j);
                if(j!=1) {
                    compturn=compturn+j;
                    String value3=Integer.toString(compturn);
                    ctsvalue.setText(value3);
                }
                else {
                    compturn=0;
                    ctsvalue.setText("0");
                    Context context = getApplicationContext();
                    CharSequence text = "Computer Rolls 1";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                if (compturn>10) {
                    Context context = getApplicationContext();
                    CharSequence text = "Computer Holds";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                if(compturn<=10 && j!=1) {
                    handler.postDelayed(this,2000);
                }
                else {
                    compover=compover+compturn;
                    compturn=0;
                    String value4=Integer.toString(compover);
                    csvalue.setText(value4);
                    ctsvalue.setText("0");
                    checkScore(compover,0,1);
                }
            }
        };
        handler.post(runnable);
        hold.setEnabled(true);
        roll.setEnabled(true);
        hold.setClickable(true);
        roll.setClickable(true);
    }
    boolean checkScore(int score,int user,int comp) {
        if(score>=100 && user==1) {
            reset();
            Context context = getApplicationContext();
            CharSequence text = "You Win !!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return true;
        }
        else if (score>=100 && comp==1) {
            reset();
            Context context = getApplicationContext();
            CharSequence text = "Computer Wins !!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return true;
        }
        else {
            return false;
        }
    }
    void reset() {
        userturn=0;
        userover=0;
        compover=0;
        compturn=0;
        usvalue.setText("0");
        utsvalue.setText("0");
        csvalue.setText("0");
        ctsvalue.setText("0");
        Context context = getApplicationContext();
        CharSequence text = "New Game Started !!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        ImageViewAnimatedChange(this,dice,R.drawable.dice1);
    }
    public static void ImageViewAnimatedChange(Context c, final ImageView v, final int new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.slide_out_right);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        anim_in.scaleCurrentDuration((float) 2);
        anim_out.scaleCurrentDuration((float) 2);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageResource(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
    @Override
    public void onClick(View v) {

    }
}