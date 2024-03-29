package com.example.first_game_tutorial.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.first_game_tutorial.helpers.interfaces.GameStateInterface;
import com.example.first_game_tutorial.main.Game;
import com.example.first_game_tutorial.main.MainActivity;
import com.example.first_game_tutorial.ui.ButtonImages;
import com.example.first_game_tutorial.ui.CustomButton;
import com.example.first_game_tutorial.ui.GameImages;

public class Menu extends BaseState implements GameStateInterface {

    private CustomButton btnStart;
    private int menuX = MainActivity.GAME_WIDTH/6;
    private int menuY = 200;

    private int btnStartX = menuX + GameImages.MAINMENU_MENUBG.getImage().getWidth()/2 - ButtonImages.MENU_START.getWidth()/2;
    private int btnStartY = menuY + 100;
    public Menu(Game game) {
        super(game);
        btnStart = new CustomButton(btnStartX, btnStartY, ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_START.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(
                GameImages.MAINMENU_MENUBG.getImage(),
                menuX,
                menuY,
                null
        );

        c.drawBitmap(
                ButtonImages.MENU_START.getBtnImg(btnStart.isPushed()),
                btnStart.getHitbox().left,
                btnStart.getHitbox().top,
                null
                );
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, btnStart))
                btnStart.setPushed(true);
        }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isIn(event, btnStart))
                    if(btnStart.isPushed())
                        game.setCurrentGameState(Game.GameState.PLAYING);
                btnStart.setPushed(false);
            }


    }
    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(), e.getY());
    }
}
