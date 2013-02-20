package net.littlecoder.core;

import playn.core.*;

import static net.littlecoder.core.util.ImageHelper.createTextImage;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;

public class GameOverGameState extends GameState implements Keyboard.Listener {

    private static final float BLINK_INTERVAL = 2000f;
    private static final float SMALL_FONT_SIZE = 15f; // Duplicate in PlayGameState
    private static final float LARGE_FONT_SIZE = 45f;

    private CanvasImage gameOverImage;
    private CanvasImage pressFireImage;

    private float blinkTime = 0f;

    public GameOverGameState() {
        initTexts();
        keyboard().setListener(this);
    }

    @Override
    public void update(float delta) {
        blinkTime = (blinkTime + delta) % BLINK_INTERVAL;
    }

    @Override
    public void render(Surface surface) {
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
        // smallFont is duplicated in PlayGameState
        Font smallFont = graphics().createFont(
                "Vector Battle", Font.Style.PLAIN, SMALL_FONT_SIZE
        );
        Font largeFont = graphics().createFont(
                "Vector Battle", Font.Style.BOLD, LARGE_FONT_SIZE
        );

        TextFormat.Alignment a = TextFormat.Alignment.CENTER; // Duplicate in PlayGameState
        int c = 0xFFFFFFFF; // Duplicate in PlayGameState

        pressFireImage = createTextImage("Press Fire to Start", smallFont, a, c);
        gameOverImage = createTextImage("Game Over", largeFont, a, c);

    }

    @Override
    public void onKeyDown(Keyboard.Event event) {
        exitToState(new PlayGameState());
    }

    @Override
    public void onKeyTyped(Keyboard.TypedEvent typedEvent) {}

    @Override
    public void onKeyUp(Keyboard.Event event) {}

}
