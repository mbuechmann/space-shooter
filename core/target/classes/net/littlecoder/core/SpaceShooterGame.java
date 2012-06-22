package net.littlecoder.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Surface;
import playn.core.SurfaceLayer;

public class SpaceShooterGame implements Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Surface surface;

    private GameHandler gameHandler;

    @Override
    public void init() {
	graphics().setSize(WIDTH, HEIGHT);

	SurfaceLayer surfaceLayer = graphics().createSurfaceLayer(
	    new Integer(WIDTH).floatValue(),
	    new Integer(HEIGHT).floatValue()
        );
	surface = surfaceLayer.surface();
	graphics().rootLayer().add(surfaceLayer);

	gameHandler = new GameHandler(surface);
    }

    @Override
    public void paint(float alpha) {
	gameHandler.paint(alpha);
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
