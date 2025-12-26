package it.jjdoes.Atomix.Systems.TextureHandlers.Liquid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class AcidTexture {

    static Color[] colors = new Color[]{
        new Color(0.10f, 0.40f, 0.15f, 1f),
        new Color(0.12f, 0.45f, 0.18f, 1f),
        new Color(0.08f, 0.35f, 0.12f, 1f),
        new Color(0.15f, 0.50f, 0.20f, 1f),
        new Color(0.11f, 0.42f, 0.16f, 1f),
        new Color(0.09f, 0.38f, 0.14f, 1f),
        new Color(0.14f, 0.48f, 0.19f, 1f),
        new Color(0.07f, 0.33f, 0.11f, 1f),
        new Color(0.13f, 0.46f, 0.17f, 1f),
        new Color(0.10f, 0.41f, 0.15f, 1f)
    };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
