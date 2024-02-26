package com.example.first_game_tutorial.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.first_game_tutorial.gamestates.Playing;
import com.example.first_game_tutorial.main.Game;

public class PlayingUI {
    private final Playing playing;
    //For UI
    private float xCenter = 250, yCenter = 700, radius = 150;
    private Paint circlePaint;
    private float xTouch, yTouch;
    private boolean touchDown;
    private CustomButton btnMenu;
    public PlayingUI(Playing playing) {
        this.playing = playing;
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        btnMenu = new CustomButton(5, 5, ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
    }
    private void drawUI(Canvas c) {
        c.drawCircle(xCenter, yCenter, radius, circlePaint);
        c.drawBitmap(
                ButtonImages.PLAYING_MENU.getBtnImg(btnMenu.isPushed()),
                btnMenu.getHitbox().left,
                btnMenu.getHitbox().top,
                null
        );

    }

    public void draw(Canvas c){
        drawUI(c);
    }

    public void touchEvents(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCenter);
                float b = Math.abs(y - yCenter);
                float c = (float)Math.hypot(a, b);

                if(c<= radius){
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                }
                else{
                    if(isIn(event, btnMenu))
                        btnMenu.setPushed(true);

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchDown){
                    xTouch = event.getX();
                    yTouch = event.getY();

                    float xDiff = xTouch - xCenter;
                    float yDiff = yTouch - yCenter;

                    playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                }
                break;
            case MotionEvent.ACTION_UP:
                touchDown = false;
                playing.setPlayerMoveFalse();
                if(isIn(event, btnMenu))
                    if (btnMenu.isPushed())
                        playing.setGameStateToMenu();
                btnMenu.setPushed(false);
                break;
        }
    }
    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(), e.getY());
    }
}
