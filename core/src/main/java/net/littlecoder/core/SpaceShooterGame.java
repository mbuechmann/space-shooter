package net.littlecoder.core;

import static playn.core.PlayN.*;

import net.littlecoder.core.game_elements.GameElement;
import playn.core.Game;
import playn.core.ImmediateLayer;

public class SpaceShooterGame implements Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private GameState currentGameState;
    private ImmediateLayer currentLayer;

    @Override
    public void init() {
        GameState.setGame(this);
        GameElement.setBoundaries(WIDTH, HEIGHT);
        graphics().setSize(WIDTH, HEIGHT);

        //setCurrentGameState(new PlayGameState());
        setCurrentGameState(new GameOverGameState());
    }

    @Override
    public void paint(float alpha) {
    }

    @Override
    public void update(float delta) {
        currentGameState.update(delta);
    }

    @Override
    public int updateRate() {
        return 25;
    }

    public void setCurrentGameState(GameState gameState) {
        if (currentLayer != null)
            graphics().rootLayer().remove(currentLayer);

        currentGameState = gameState;
        currentLayer = graphics().createImmediateLayer(
            WIDTH,
            HEIGHT,
            currentGameState
        );
        graphics().rootLayer().add(currentLayer);
    }

}
