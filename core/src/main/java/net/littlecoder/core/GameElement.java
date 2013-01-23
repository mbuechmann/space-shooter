package net.littlecoder.core;

class GameElement {

    private static int width = 100;
    private static int height = 100;

    protected float x = 0f;
    protected float y = 0f;
    protected float vx = 0f;
    protected float vy = 0f;
    // rotation from 0 to 360, where 0 is pointing up
    protected float rot = 0f;
    protected float vrot = 0f;

    public GameElement() {
    }

    public static void setBoundaries(int width, int height) {
        GameElement.width = width;
        GameElement.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    protected void updatePosition(float delta) {
        x += vx * (delta / 1000f);
        y += vy * (delta / 1000f);

        while (x < 0)
            x += width;
        while (x > width)
            x -= width;
        while (y < 0)
            y += height;
        while (y > height)
            y -= height;

        rot += (delta / 1000f) * vrot;
    }

}
