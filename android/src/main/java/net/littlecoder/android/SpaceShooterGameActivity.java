package net.littlecoder.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import net.littlecoder.core.SpaceShooterGame;

public class SpaceShooterGameActivity extends GameActivity {

    @Override
    public void main() {
        platform().assets().setPathPrefix("net/littlecoder/resources");
        PlayN.run(new SpaceShooterGame());
    }
}
