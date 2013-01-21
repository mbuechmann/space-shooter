package net.littlecoder.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import net.littlecoder.core.SpaceShooterGame;

public class SpaceShooterGameHtml extends HtmlGame {

    @Override
    public void start() {
        HtmlPlatform platform = HtmlPlatform.register();
        platform.assets().setPathPrefix("shooter/");
        PlayN.run(new SpaceShooterGame());
    }
}
