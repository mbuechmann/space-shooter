package net.littlecoder.core;

import net.littlecoder.core.util.TextImage;
import playn.core.*;

import static playn.core.PlayN.keyboard;

public class GameOverGameState extends GameState implements Keyboard.Listener {

    private static final float BLINK_INTERVAL = 2000f;
    private static final float SMALL_FONT_SIZE = 15f; // Duplicate in PlayGameState
    private static final float LARGE_FONT_SIZE = 45f;
    private static final String START_TEXT = "Press Fire to Start";
    private static final String GAME_OVER_TEXT = "Game Over";

    private CanvasImage gameOverImage;
    private CanvasImage pressFireImage;

    private float blinkTime = 0f;

    public GameOverGameState() {
        pressFireImage = new TextImage(SMALL_FONT_SIZE, TextFormat.Alignment.CENTER, START_TEXT).canvasImage();
        gameOverImage = new TextImage(LARGE_FONT_SIZE, TextFormat.Alignment.CENTER, GAME_OVER_TEXT).canvasImage();
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

    @Override
    public void onKeyDown(Keyboard.Event event) {
        exitToState(new PlayGameState());
    }

    @Override
    public void onKeyTyped(Keyboard.TypedEvent typedEvent) {}

    @Override
    public void onKeyUp(Keyboard.Event event) {}

}
