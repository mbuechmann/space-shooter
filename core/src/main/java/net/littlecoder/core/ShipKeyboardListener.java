package net.littlecoder.core;

import playn.core.Key;
import playn.core.Keyboard;

class ShipKeyboardListener implements Keyboard.Listener {

    private Ship ship;
 
    public ShipKeyboardListener(Ship ship) {
	this.ship = ship;
    }

    public void onKeyDown(Keyboard.Event event) {
	if (event.key() == Key.UP)
	    ship.accelerate(true);
	if (event.key() == Key.RIGHT)
	    ship.steerRight(true);
	if (event.key() == Key.LEFT)
	    ship.steerLeft(true);
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