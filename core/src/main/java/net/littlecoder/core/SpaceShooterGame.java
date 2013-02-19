package net.littlecoder.core;

import static playn.core.PlayN.*;

import net.littlecoder.core.game_elements.GameElement;
import playn.core.Game;
import playn.core.ImmediateLayer;

public class SpaceShooterGame implements Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static GameState currentGameState;

    @Override
    public void init() {
        GameElement.setBoundaries(WIDTH, HEIGHT);
        graphics().setSize(WIDTH, HEIGHT);

        currentGameState = new PlayGameState();
        ImmediateLayer il = graphics().createImmediateLayer(
            WIDTH,
            HEIGHT,
            currentGameState
        );
        graphics().rootLayer().add(il);
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

}
