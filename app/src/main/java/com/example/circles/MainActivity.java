package com.example.circles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean Act_start = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Field(this));
    }

    public class Field extends View {

        Lvldot lvldot[][] = new Lvldot[5][5];
        float x = 100, y = 100, x2, y2, Xch, Ych;
        boolean open = true;
        Context ctx;
        Paint p = new Paint();
        float num=0;
        public Field(Context context){
            super(context);
            ctx = context;
        }

        @Override
        protected void onDraw(Canvas canvas){
            for (int i = 0; i < 2; i++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    p.setColor(Color.RED);
                    if (open) {
                        lvldot[i][i2] = new Lvldot(x + 10, y - 5, 25, p, num);
                        num++;
                    }
                    lvldot[i][i2].draw_circle(canvas);
                    x+=125;
                }
                x=100;
                y+=125;
            }
            if ((Math.abs(x2 - 1000) <= 50) && (Math.abs(y2 - 200) <= 50)) {
                Intent intent = new Intent(ctx, Main2Activity.class);
                int lvlnumber = 24;
                intent.putExtra("number_of_levels", lvlnumber);
                startActivity(intent);
            }
            open = false;
            for (int i=0;i<2;i++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    if (x2 != Xch && y2 != Ych) {
                        if ((Math.abs(x2 - lvldot[i][i2].x) <= lvldot[i][i2].radius) &&
                                (Math.abs(y2 - lvldot[i][i2].y) <= lvldot[i][i2].radius)) {
                            if (Act_start) {
                                Log.d("Main", "Level started");
                                // blocks multiple level opening
                                Act_start = false;
                                Intent intent = new Intent(ctx, Main2Activity.class);
                                int lvlnumber = (int) lvldot[i][i2].lvlnum;
                                intent.putExtra("number_of_levels", lvlnumber);
                                startActivity(intent);
                                Xch = x2;
                                Ych = y2;
                            }
                        }
                    }
                }
            }
            p.setColor(Color.RED);
            canvas.drawCircle(1000, 200, 50, p);
            p.setColor(Color.WHITE);
            p.setTextSize(15);
            canvas.drawText("Free lvl", 980, 200, p);
            p.setColor(Color.RED);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            x2 = event.getX();
            y2 = event.getY();


            invalidate();
            return true;
        }
    }

    @Override
    protected void onResume() {
        // restores flag so activities will open
        super.onResume();
        Act_start=true;
    }


}
