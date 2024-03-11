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
    private final PointF joystickCenterPos = new PointF(250, 800);
    private final PointF attackBtnCenterPos = new PointF(1700, 800);
    private final float radius = 150;
    private final Paint circlePaint;
    private CustomButton btnMenu;

    // For multitouch
    private boolean touchDown;
    private int joystickPointerId = -1;
    private int attackBtnPointerId = -1;

    public PlayingUI(Playing playing) {
        this.playing = playing;
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        btnMenu = new CustomButton(5, 5, ButtonImages.PLAYING_MENU.getWidth(), ButtonImages.PLAYING_MENU.getHeight());
    }
    private void drawUI(Canvas c) {
        c.drawCircle(joystickCenterPos.x, joystickCenterPos.y, radius, circlePaint);
        c.drawCircle(attackBtnCenterPos.x, attackBtnCenterPos.y, radius, circlePaint);
        c.drawBitmap(
                ButtonImages.PLAYING_MENU.getBtnImg(btnMenu.isPushed(btnMenu.getPointerId())),
                btnMenu.getHitbox().left,
                btnMenu.getHitbox().top,
                null
        );

    }

    public void draw(Canvas c){
        drawUI(c);
    }

    private boolean checkInsideJoystick(PointF eventPos, int pointerId){

        if(isInsideRadius(eventPos, joystickCenterPos)){
            touchDown = true;
            joystickPointerId = pointerId;
            return true;
        }
        return false;
    }

    private boolean isInsideRadius(PointF eventPos, PointF circle){
        float a = Math.abs(eventPos.x - circle.x);
        float b = Math.abs(eventPos.y - circle.y);
        float c = (float)Math.hypot(a, b);

        return c <= radius;

    }

    private boolean checkInsideAttackBtn(PointF eventPos){
        return isInsideRadius(eventPos, attackBtnCenterPos);
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

                } else if (checkInsideAttackBtn(eventPos)) {
                    if (attackBtnPointerId < 0){
                        playing.setAttacking(true);
                        attackBtnPointerId = pointerId;
                    }
                } else{
                    if(isIn(eventPos, btnMenu))
                        btnMenu.setPushed(true, pointerId);

                }
            }
            case MotionEvent.ACTION_MOVE ->{
                if(touchDown)
                    for(int i = 0; i < event.getPointerCount(); i++){
                        if (event.getPointerId(i) == joystickPointerId){
                            float xDiff = event.getX(i) - joystickCenterPos.x;
                            float yDiff = event.getY(i) - joystickCenterPos.y;
                            playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                        }
                    }
            }

            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (pointerId == joystickPointerId){
                    resetJoystickButton();
                }
                else{
                    if(isIn(eventPos, btnMenu)) {
                        if (btnMenu.isPushed(pointerId)) {
                            resetJoystickButton();
                            playing.setGameStateToMenu();
                        }

                    }
                    btnMenu.unPush(pointerId);
                    if (pointerId == attackBtnPointerId) {
                        playing.setAttacking(false);
                        attackBtnPointerId = -1;
                    }
                }

            }

        }
    }
    private boolean isIn(PointF eventPos, CustomButton b){
        return b.getHitbox().contains(eventPos.x, eventPos.y);
    }
    private void resetJoystickButton(){
        touchDown = false;
        playing.setPlayerMoveFalse();
        joystickPointerId = -1;
    }
}
