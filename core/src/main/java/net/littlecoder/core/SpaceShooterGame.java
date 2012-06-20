package net.littlecoder.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Surface;
import playn.core.SurfaceLayer;

public class SpaceShooterGame implements Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private Surface surface;

    private Ship ship;

    @Override
    public void init() {
	graphics().setSize(WIDTH, HEIGHT);

	SurfaceLayer surfaceLayer = graphics().createSurfaceLayer(
	    new Integer(WIDTH).floatValue(),
	    new Integer(HEIGHT).floatValue()
        );
	surface = surfaceLayer.surface();
	graphics().rootLayer().add(surfaceLayer);

	ship = new Ship(WIDTH, HEIGHT);
    }

    @Override
    public void paint(float alpha) {
	surface.clear();

	ship.paint(surface);
    }

    @Override
    public void update(float delta) {
	ship.accelerate(delta);
	ship.update(delta);
    }

    @Override
    public int updateRate() {
	return 25;
    }

}
