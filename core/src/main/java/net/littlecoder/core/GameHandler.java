package net.littlecoder.core;

import static playn.core.PlayN.*;

import java.util.ArrayDeque;
import java.util.Iterator;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;

class GameHandler implements Keyboard.Listener {

    private static final float SMALL_FONT_SIZE = 15f;
    private static final float LARGE_FONT_SIZE = 45f;
    private static final float TIME_BETWEEN_LEVELS = 3000f;
    private static final float BLINK_INTERVAL = 2000f;

    private Surface surface;

    private Ship ship;
    private ArrayDeque<Asteroid> asteroids = new ArrayDeque<Asteroid>();
    private ArrayDeque<Bullet> bullets = new ArrayDeque<Bullet>();
    private boolean shooting = false;

    private byte lifes = 0;
    private int score = 0;
    private int level = 0;

    private TextFormat smallTextFormat;
    private TextFormat largeTextFormat;
    private CanvasImage scoreImage;
    private CanvasImage levelImage;
    private CanvasImage nextLevelImage;
    private CanvasImage gameOverImage;
    private CanvasImage pressFireImage;

    private Polyline shipPolyline;

    private float timeToNextLevel = TIME_BETWEEN_LEVELS;

    private float blinkTime = 0f;

    public GameHandler(Surface surface) {
	this.surface = surface;

	initTexts();

	ship = new Ship(surface);

	shipPolyline = Ship.shipPolyline.clone();

	keyboard().setListener(this);
    }

    public void paint(float alpha) {
	surface.clear();

	if (!isGameOver()) {
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

	    for (int l = 0; l < lifes; l++)
		shipPolyline.transform(0f, 20f + l * 20f, 20f).paint(surface);
	
	} else
	    paintGameOver();

	paintScore();
	paintLevel();
    }

    public void update(float delta) {
	if (!isGameOver()) {
	    updateShip(delta);
	    updateBullets(delta);
	    updateAsteroids(delta);
	    detectCollisions(delta);
	    advanceLevel(delta);
	} else
	    blinkTime = (blinkTime + delta) % BLINK_INTERVAL;
    }

    public void onKeyDown(Keyboard.Event event) {
	if (event.key() == Key.UP)
	    ship.accelerate(true);
	if (event.key() == Key.RIGHT)
	    ship.steerRight(true);
	if (event.key() == Key.LEFT)
	    ship.steerLeft(true);

	if (event.key() == Key.SPACE)
	    if (isGameOver())
	        initLevel(1);
	    else if (!ship.isDead())
		shooting = !ship.isDisabled() && true;
	    else
		ship.reinitialize();

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

    private void updateBullets(float delta) {
	Iterator i = bullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.update(delta);
	    if (b.isDead())
		i.remove();
	}

 	if (shooting) {
	    bullets.add(new Bullet(ship));
	    shooting = false;
	}
   }

    private void updateShip(float delta) {
	ship.update(delta);
    }

    private void updateAsteroids(float delta) {
	ArrayDeque<Asteroid> newAsteroids = new ArrayDeque<Asteroid>();

	Iterator i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    if (a.isDead()) {
		Asteroid[] children = a.spawnChildren();
		for (Asteroid c : children)
		    newAsteroids.add(c);
		i.remove();
	    } else
		a.update(delta);
	}

	i = newAsteroids.iterator();
	while (i.hasNext())
	    asteroids.add((Asteroid)i.next());
    }

    private void detectCollisions(float delta) {
	Iterator i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    Iterator j = bullets.iterator();
	    while (j.hasNext()) {
		Bullet b = (Bullet)j.next();
		if (!b.isDead() && a.isCollidingWith(b)) {
		    a.die();
		    b.die();
		    score += 3 - a.size();
		}
	    }
	}

	i = asteroids.iterator();

	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    if (a.isCollidingWith(ship)) {
		ship.die();
		lifes--;
		break;
	    }
	}
    }

    private void paintScore() {
	String scoreText = String.valueOf(score);
	if (score < 10)
	    scoreText = "0" + scoreText;
	if (score < 100)
	    scoreText = "0" + scoreText;
	if (score < 1000)
	    scoreText = "0" + scoreText;
	if (score < 10000)
	    scoreText = "0" + scoreText;

	TextLayout layout = graphics().layoutText(scoreText, smallTextFormat);

	scoreImage.canvas().clear();
	scoreImage.canvas().drawText(layout, 0, 0);
	surface.drawImage(scoreImage, surface.width() - layout.width() - 10, 10f);
    }

    private void paintLevel() {
	String text = String.valueOf(level);
	if (level < 10)
	    text = "0" + text;

	TextLayout layout = graphics().layoutText(text, smallTextFormat);
	levelImage.canvas().clear();
	levelImage.canvas().drawText(layout, 0, 0);
	surface.drawImage(levelImage, surface.width() / 2f - layout.width(), 10f);

	if (asteroids.isEmpty() && !isGameOver()) {
	    text = "Next Level in\n" + (int)Math.ceil(timeToNextLevel / 1000) + " s";
	    layout = graphics().layoutText(text, smallTextFormat);
	    nextLevelImage.canvas().clear();
	    nextLevelImage.canvas().drawText(layout, 0, 0);
	    float y = surface.height() / 4f;
	    if (ship.y < surface.height() / 2f)
		y += surface.height() / 2f;
	    surface.drawImage(
	        nextLevelImage, (surface.width() - layout.width()) / 2f, y
	    );
	}
    }

    private void paintGameOver() {
	surface.drawImage(
	    gameOverImage,
	    (surface.width() - gameOverImage.width()) / 2f,
	    (surface.height() / 3f - gameOverImage.height() / 2f)
	);

	if (blinkTime < BLINK_INTERVAL / 2f)
	    surface.drawImage(
	        pressFireImage,
		(surface.width() - pressFireImage.width()) / 2f,
		(surface.height() / 3f * 2f - pressFireImage.height() / 2f)
	);
    }

    private void initTexts() {
	Font smallFont = graphics().createFont(
            "Vector Battle", Font.Style.PLAIN, SMALL_FONT_SIZE
        );
        smallTextFormat = new TextFormat().
	    withFont(smallFont).
	    withTextColor(0xFFFFFFFF).
	    withAlignment(TextFormat.Alignment.CENTER);
	TextLayout l = graphics().layoutText("00", smallTextFormat);
	levelImage = graphics().createImage(
	    (int)Math.ceil(l.width()),
	    (int)Math.ceil(l.height())
        );
	l = graphics().layoutText("00000", smallTextFormat);
	scoreImage = graphics().createImage(
	    (int)Math.ceil(l.width()),
	    (int)Math.ceil(l.height())
        );
	l = graphics().layoutText("Next Level in\n0 s", smallTextFormat);
	nextLevelImage = graphics().createImage(
	    (int)Math.ceil(l.width()),
	    (int)Math.ceil(l.height())
        );
	l = graphics().layoutText("Press Fire to start", smallTextFormat);
	pressFireImage = graphics().createImage(
	    (int)Math.ceil(l.width()),
	    (int)Math.ceil(l.height())
        );
	pressFireImage.canvas().drawText(l, 0, 0);

	Font largeFont = graphics().createFont(
            "Vector Battle", Font.Style.BOLD, LARGE_FONT_SIZE
        );
        largeTextFormat = new TextFormat().
	    withFont(largeFont).
	    withTextColor(0xFFFFFFFF).
	    withAlignment(TextFormat.Alignment.CENTER);
	l = graphics().layoutText("Game Over", largeTextFormat);
	gameOverImage = graphics().createImage(
	    (int)Math.ceil(l.width()),
	    (int)Math.ceil(l.height())
        );
	gameOverImage.canvas().drawText(l, 0, 0);
    }

    private void initLevel(int level) {
	System.out.println("Initing level " + level);
	this.level = level;
	if (level == 1) {
	    lifes = 3;
	    score = 0;
	}

	asteroids.clear();
	for (int i = 0; i < level + 2; i++)
	    asteroids.add(new Asteroid((byte)2, surface));
    }

    private void advanceLevel(float delta) {
	if (asteroids.isEmpty()) {
	    timeToNextLevel -= delta;
	    if (timeToNextLevel < 0f) {
		level++;
		initLevel(level);
		timeToNextLevel = TIME_BETWEEN_LEVELS;
	    }
	}
    }

    private boolean isGameOver() {
	return level == 0 || lifes == 0 && ship.isDead();
    }

}
