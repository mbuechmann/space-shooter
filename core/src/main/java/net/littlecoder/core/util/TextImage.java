package net.littlecoder.core.util;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.TextFormat;
import playn.core.TextLayout;

import static playn.core.PlayN.graphics;

public class TextImage {

    private static final int COLOR = 0xFFFFFFFF;
    private static final String FONT_NAME = "Vector Battle";
    private static final Font.Style STYLE = Font.Style.PLAIN;

    private TextFormat textFormat;
    private CanvasImage canvasImage;

    public TextImage(float fontSize, TextFormat.Alignment alignment, String text) {
        Font font = graphics().createFont(FONT_NAME, STYLE, fontSize);
        textFormat = new TextFormat().withFont(font).withAlignment(alignment).withTextColor(COLOR);
        TextLayout layout = graphics().layoutText(text, textFormat);
        canvasImage = graphics().createImage((int) Math.ceil(layout.width()), (int) Math.ceil(layout.height()));
        updateText(text);
    }

    public CanvasImage canvasImage() {
        return canvasImage;
    }

    public CanvasImage updateText(String text) {
        TextLayout layout = graphics().layoutText(text, textFormat);
        canvasImage.canvas().clear();
        canvasImage.canvas().drawText(layout, 0, 0);
        return canvasImage;
    }

}
