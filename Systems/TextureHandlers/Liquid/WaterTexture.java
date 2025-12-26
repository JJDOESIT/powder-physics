package it.jjdoes.Atomix.Systems.TextureHandlers.Liquid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class WaterTexture {
    static Color[] colors = new Color[]
        {
            new Color(0.6f, 0.8f, 1f, 1f),  // Light blue
        };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
