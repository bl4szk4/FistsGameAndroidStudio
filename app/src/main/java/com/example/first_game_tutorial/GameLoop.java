package com.example.first_game_tutorial;

public class GameLoop implements Runnable{
    private Thread gameThread;
    private GamePanel gamePanel;
    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

            gamePanel.update(delta);
            gamePanel.render();
            lastDelta = nowDelta;


            fps++;
            long now = System.currentTimeMillis();
            if (now - lastPFSCheck >= 1000){
                System.out.println(fps);
                fps = 0;
                lastPFSCheck += 1000;
            }
        }
    }
    public void startGameLoop(){
        gameThread.start();
    }
}
