package net.littlecoder.core;

import playn.core.ImmediateLayer;

public interface GameState extends ImmediateLayer.Renderer {

    public void update(float delta);

}
