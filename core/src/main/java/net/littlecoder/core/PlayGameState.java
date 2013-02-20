package net.littlecoder.core;

import static playn.core.PlayN.*;

import static net.littlecoder.core.util.ImageHelper.*;

import net.littlecoder.core.game_elements.Ship;
import net.littlecoder.core.game_elements.primitives.Polyline;
import net.littlecoder.core.managers.AsteroidManager;
import net.littlecoder.core.managers.BulletManager;
import playn.core.*;

// TODO: Move rendering of dynamic texts to ImageHelper
class PlayGameState extends GameState implements Keyboard.Listener {

    private static final float SMALL_FONT_SIZE = 15f;
    private static final float TIME_BETWEEN_LEVELS = 3000f;

    private Ship ship;
    private BulletManager bulletManager;
    private AsteroidManager asteroidManager;

    private boolean shooting = false;

    private byte lives = 3;
    private int score = 0;
    private int level;

    private TextFormat smallTextFormat;
    private CanvasImage scoreImage;
    private CanvasImage levelImage;
    private CanvasImage nextLevelImage;

    private Polyline shipPolyline;

    private float timeToNextLevel = TIME_BETWEEN_LEVELS;

    public PlayGameState() {
        initTexts();

        ship = new Ship();
        bulletManager = new BulletManager();
        asteroidManager = new AsteroidManager();

        shipPolyline = Ship.shipPolyline.copy();

        keyboard().setListener(this);

        initLevel(1);
    }

    @Override
    public void render(Surface surface) {
        surface.clear();

        ship.paint(surface);

        bulletManager.paint(surface);
        asteroidManager.paint(surface);

        for (int l = 0; l < lives; l++)
            shipPolyline.transform(0f, 20f + l * 20f, 20f).paint(surface);

        paintScore(surface);
        paintLevel(surface);
    }

    public void update(float delta) {
        ship.update(delta);
        updateBullets(delta);
        asteroidManager.update(delta);
        detectCollisions();
        advanceLevel(delta);

        if (isGameOver())
            exitToState(new GameOverGameState());
    }

    public void onKeyDown(Keyboard.Event event) {
        if (event.key() == Key.UP)
            ship.accelerate(true);
        if (event.key() == Key.RIGHT)
            ship.steerRight(true);
        if (event.key() == Key.LEFT)
            ship.steerLeft(true);

        if (event.key() == Key.SPACE)
            if (!ship.isDead())
                shooting = !ship.isDisabled();
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
        bulletManager.update(delta);
        if (shooting) {
            bulletManager.addBullet(ship);
            shooting = false;
        }
    }

    private void detectCollisions() {
        score += bulletManager.detectCollisions(asteroidManager);

        if (asteroidManager.isCollidingWith(ship)) {
            ship.die();
            lives--;
        }
    }

    private void paintScore(Surface surface) {
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

    private void paintLevel(Surface surface) {
        String text = String.valueOf(level);
        if (level < 10)
            text = "0" + text;

        TextLayout layout = graphics().layoutText(text, smallTextFormat);
        levelImage.canvas().clear();
        levelImage.canvas().drawText(layout, 0, 0);
        surface.drawImage(levelImage, surface.width() / 2f - layout.width(), 10f);

        if (asteroidManager.isEmpty() && !isGameOver()) {
            text = "Next Level in\n\n" + (int) Math.ceil(timeToNextLevel / 1000) + " Sec";
            layout = graphics().layoutText(text, smallTextFormat);
            nextLevelImage.canvas().clear();
            nextLevelImage.canvas().drawText(layout, 0, 0);
            float y = surface.height() / 4f;
            if (ship.getY() < surface.height() / 2f)
                y += surface.height() / 2f;
            surface.drawImage(
                    nextLevelImage, (surface.width() - layout.width()) / 2f, y
            );
        }
    }

    private void initTexts() {
        Font smallFont = graphics().createFont(
                "Vector Battle", Font.Style.PLAIN, SMALL_FONT_SIZE
        );
        TextFormat.Alignment a = TextFormat.Alignment.CENTER;
        int c = 0xFFFFFFFF;

        smallTextFormat = new TextFormat().
            withFont(smallFont).
            withAlignment(a).
            withTextColor(c);

        levelImage = createTextImage("00", smallFont, a, c);
        scoreImage = createTextImage("00000", smallFont, a, c);
        nextLevelImage = createTextImage("Next Level in\n\n0 Sec", smallFont, a, c);
    }

    private void initLevel(int level) {
        this.level = level;
        if (level == 1) {
            lives = 3;
            score = 0;
        }

        asteroidManager.initLevel(level);
    }

    private void advanceLevel(float delta) {
        if (asteroidManager.isEmpty()) {
            timeToNextLevel -= delta;
            if (timeToNextLevel < 0f) {
                level++;
                initLevel(level);
                timeToNextLevel = TIME_BETWEEN_LEVELS;
            }
        }
    }

    private boolean isGameOver() {
        return lives == 0 && ship.isDead();
    }
}
