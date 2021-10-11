package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 * Modi-in, YACHAD high-school.
 * *****************************************************************
 * <p>
 * Class description:
 * The Explosion , when two images collide, class
 */

public class Explosion extends AnimatedSpritesObject {
    public Explosion(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    public void update() {
        // we want the explosion to animate/ happen only once
        if (!animation.getPlayedOnce()) {
            animation.update();
        }
    }

    public void draw(Canvas canvas) {
        if (!animation.getPlayedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    }
}
