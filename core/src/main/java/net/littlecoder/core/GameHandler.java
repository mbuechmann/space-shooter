package net.littlecoder.core;

import static playn.core.PlayN.*;

import java.util.ArrayDeque;
import java.util.Iterator;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Surface;

class GameHandler implements Keyboard.Listener {

    private Surface surface;

    private Ship ship;
    private ArrayDeque<Asteroid> asteroids;
    private ArrayDeque<Bullet> bullets;
    private boolean shooting = false;
 
    public GameHandler(Surface surface) {
	this.surface = surface;
	ship = new Ship(surface);
	asteroids = new ArrayDeque<Asteroid>();
	for (int i = 0; i < 5; i++)
	    asteroids.add(new Asteroid((byte)0, surface));
	bullets = new ArrayDeque<Bullet>();
	keyboard().setListener(this);
    }

    public void paint(float alpha) {
	surface.clear();

	ship.paint(alpha);

	Iterator i = bullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.paint(alpha);
	}

	i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    a.paint(alpha);
	}
    }

    public void update(float delta) {
	ship.update(delta);

	Iterator i = bullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.update(delta);
	    if (b.isDead())
		i.remove();
	}

	i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    a.update(delta);
	}

	if (shooting) {
	    bullets.add(new Bullet(ship));
	    shooting = false;
	}
    }

    public void onKeyDown(Keyboard.Event event) {
	if (event.key() == Key.UP)
	    ship.accelerate(true);
	if (event.key() == Key.RIGHT)
	    ship.steerRight(true);
	if (event.key() == Key.LEFT)
	    ship.steerLeft(true);
	if (event.key() == Key.SPACE)
	    shooting = true;
	if (event.key() == Key.ESCAPE)
	    System.exit(0);
    }

    public void onKeyUp(Keyboard.Event event) {
	if (event.key() == Key.UP)
	    ship.accelerate(false);
	if (event.key() == Key.RIGHT)
	    ship.steerRight(false);
	if (event.key() == Key.LEFT)
	    ship.steerLeft(false);
    }
    
    public void onKeyTyped(Keyboard.TypedEvent event) {}

}