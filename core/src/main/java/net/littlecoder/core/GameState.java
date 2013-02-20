package net.littlecoder.core;

import playn.core.ImmediateLayer;

public abstract class GameState implements ImmediateLayer.Renderer {

    private static SpaceShooterGame game;

    public abstract void update(float delta);

    public static void setGame(SpaceShooterGame game) {
        GameState.game = game;
    }

    protected void exitToState(GameState gameState) {
        game.setCurrentGameState(gameState);
    }

}
