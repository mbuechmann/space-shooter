package net.littlecoder.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import net.littlecoder.core.SpaceShooterGame;

public class SpaceShooterGameJava {

    public static void main(String[] args) {
        JavaPlatform platform = JavaPlatform.register();
        platform.assets().setPathPrefix("net/littlecoder/resources");
        platform.graphics().registerFont("Vector Battle", "text/Vectorb.ttf");
        PlayN.run(new SpaceShooterGame());
    }
}
