package it.jjdoes.Atomix.Systems.TextureHandlers.StaticSolid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class StoneTexture {
    static Color[] colors = new Color[]
        {
            new Color(0.6f, 0.6f, 0.6f, 1f), // Light gray
            new Color(0.5f, 0.5f, 0.5f, 1f), // Medium gray
            new Color(0.4f, 0.4f, 0.4f, 1f)  // Dark gray
        };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
