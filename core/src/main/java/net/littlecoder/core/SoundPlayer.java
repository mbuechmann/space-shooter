package net.littlecoder.core;

import static playn.core.PlayN.*;

import java.util.ArrayDeque;
import java.util.HashMap;

import playn.core.Sound;

public class SoundPlayer {

    private static HashMap<String, ArrayDeque<Sound>> soundQueues =
        new HashMap<String, ArrayDeque<Sound>>();

    public static void loadSound(String filename) {
        if (soundQueues.get(filename) == null) {
            ArrayDeque<Sound> queue = new ArrayDeque<Sound>();
            Sound sound = assets().getSound("sounds/" + filename);
            queue.add(sound);
            soundQueues.put(filename, queue);
        }
    }

    public static void playSound(String filename) {
        if (soundQueues.get(filename) == null)
            loadSound(filename);

        ArrayDeque<Sound> queue = soundQueues.get(filename);
        Sound s;
        if (queue.getFirst().isPlaying())
            s = getSound(filename);
        else
            s = queue.remove();

        s.play();
        queue.add(s);
    }

    public static Sound getSound(String filename) {
        return assets().getSound("sounds/" + filename);
    }

}
