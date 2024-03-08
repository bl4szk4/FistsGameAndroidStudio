package com.example.first_game_tutorial.main;

public class GameLoop implements Runnable{
    private Thread gameThread;
    private Game game;
    public GameLoop(Game game) {
        this.game = game;
        gameThread = new Thread(this);
    }

    @Override
    public void run() {
        long lastPFSCheck = System.currentTimeMillis();
        int fps = 0;

        long lastDelta = System.nanoTime();
        final long nanoSec = 1_000_000_000;

        while (true){
            long nowDelta = System.nanoTime();
            double timeSinceLastDelta = nowDelta - lastDelta;
            double delta = timeSinceLastDelta / nanoSec;

            game.update(delta);
            game.render();
            lastDelta = nowDelta;


            fps++;
            long now = System.currentTimeMillis();
            if (now - lastPFSCheck >= 1000){
//                System.out.println(fps);
                fps = 0;
                lastPFSCheck += 1000;
            }
        }
    }
    public void startGameLoop(){
        gameThread.start();
    }
}
