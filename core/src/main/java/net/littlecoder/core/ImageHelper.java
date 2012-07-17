package net.littlecoder.core;

import static playn.core.PlayN.*;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.TextFormat;
import playn.core.TextLayout;

class ImageHelper {

    public static CanvasImage createTextImage(
	String text, 
	Font font, 
	TextFormat.Alignment alignment, 
	int color
    ) {
	TextFormat format = new TextFormat().
	    withFont(font).
	    withAlignment(alignment).
	    withTextColor(color);

	TextLayout layout = graphics().layoutText(text, format);

	CanvasImage res = graphics().createImage(
	    (int)Math.ceil(layout.width()),
	    (int)Math.ceil(layout.height())
        );
	res.canvas().drawText(layout, 0, 0);

	return res;
    }

}
