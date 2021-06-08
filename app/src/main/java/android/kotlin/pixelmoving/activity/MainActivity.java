package android.kotlin.pixelmoving.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.kotlin.pixelmoving.R;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {


    private final List<TextView> checkerYellow = new ArrayList<>();

    RelativeLayout relative_layout_square;
    RelativeLayout relative_layout_main;
    TextView text_view_square;
    TextView text_view_coordinate;

    private  int xDelta = 0;
    private int yDelta = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relative_layout_square = findViewById(R.id.relative_layout_square);
        relative_layout_main = findViewById(R.id.relative_layout_main);
        text_view_square = findViewById(R.id.text_view_square);
        text_view_coordinate = findViewById(R.id.text_view_coordinate);
        moveTextview();


    }


    @SuppressLint("ClickableViewAccessibility")
    private void moveTextview(){

            relative_layout_square.setOnTouchListener(onTouchListener());

    }

    private View.OnTouchListener onTouchListener() {

        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        Animation animation = new RotateAnimation(0.0f,180.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(200);
                        animation.setFillEnabled(true);
                        animation.setFillAfter(true);
                        text_view_square.startAnimation(animation);
                        break;

                    case MotionEvent.ACTION_UP:
                        String xText = Integer.toString(x);
                        String yText = Integer.toString( y);
                        Date date = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd/MM:mm:ss");
                        String dateText = simpleFormatter.format(date);
                        text_view_coordinate.setText(String.format("(%s:%s) |%s", xText, yText, dateText));
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;

                        view.setLayoutParams(layoutParams);
                        break;
                }

                relative_layout_main.invalidate();
                return true;
            }
        };
    }
}

