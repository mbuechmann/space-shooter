package net.littlecoder.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.ImmediateLayer;
import playn.core.Surface;
import playn.core.SurfaceLayer;

public class SpaceShooterGame implements Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private GameHandler gameHandler;

    @Override
    public void init() {
        graphics().setSize(WIDTH, HEIGHT);

        gameHandler = new GameHandler(WIDTH, HEIGHT);
        ImmediateLayer il = graphics().createImmediateLayer(
                WIDTH,
                HEIGHT,
                gameHandler
        );
        graphics().rootLayer().add(il);
    }

    @Override
    public void paint(float alpha) {
    }

    @Override
    public void update(float delta) {
        gameHandler.update(delta);
    }

    @Override
    public int updateRate() {
        return 25;
    }

}
