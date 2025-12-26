package it.jjdoes.Atomix.Systems.TextureHandlers.Gas;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class AcidTexture {

    static Color[] colors = new Color[]{
        new Color(0.55f, 0.95f, 0.45f, 1f),
        new Color(0.60f, 1.00f, 0.50f, 1f),
        new Color(0.50f, 0.90f, 0.40f, 1f),
        new Color(0.58f, 0.97f, 0.48f, 1f),
        new Color(0.52f, 0.92f, 0.42f, 1f),
        new Color(0.62f, 0.98f, 0.52f, 1f),
        new Color(0.48f, 0.88f, 0.38f, 1f),
        new Color(0.57f, 0.96f, 0.47f, 1f),
        new Color(0.53f, 0.94f, 0.44f, 1f),
        new Color(0.59f, 0.99f, 0.49f, 1f)
    };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
