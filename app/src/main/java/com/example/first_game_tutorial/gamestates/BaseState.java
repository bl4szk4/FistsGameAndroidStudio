package com.example.first_game_tutorial.gamestates;

import com.example.first_game_tutorial.main.Game;

public abstract class BaseState {
    protected Game game;
    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
