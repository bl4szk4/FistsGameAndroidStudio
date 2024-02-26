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
    private CustomButton btnMenu;

    // For multitouch
    private boolean touchDown;
    private int joystickPointerId = -1;

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

    private boolean checkInsideJoystick(PointF eventPos, int pointerId){
        float a = Math.abs(eventPos.x - xCenter);
        float b = Math.abs(eventPos.y - yCenter);
        float c = (float)Math.hypot(a, b);

        if(c<= radius){
            touchDown = true;
            joystickPointerId = pointerId;
            return true;
        }
        return false;
    }

    public void touchEvents(MotionEvent event){
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);
        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));

        switch (action){
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN ->{

                if(checkInsideJoystick(eventPos, pointerId)){
                    touchDown = true;

                }
                else{
                    if(isIn(event, btnMenu))
                        btnMenu.setPushed(true);

                }
            }
            case MotionEvent.ACTION_MOVE ->{
                if(touchDown)
                    for(int i = 0; i < event.getPointerCount(); i++){
                        if (event.getPointerId(i) == joystickPointerId){
                            float xDiff = event.getX(i) - xCenter;
                            float yDiff = event.getY(i) - yCenter;
                            playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                        }
                    }
            }

            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (pointerId == joystickPointerId){
                    resetJoystickButton();
                }
                else{
                    if(isIn(event, btnMenu)) {
                        if (btnMenu.isPushed()) {
                            resetJoystickButton();
                            playing.setGameStateToMenu();
                        }
                    }
                    btnMenu.setPushed(false);

                }

            }

        }
    }
    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(), e.getY());
    }
    private void resetJoystickButton(){
        touchDown = false;
        playing.setPlayerMoveFalse();
        joystickPointerId = -1;
    }
}
