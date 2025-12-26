package it.jjdoes.Atomix.Systems.TextureHandlers.Liquid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class LavaTexture {

    static Color[] colors = new Color[]{
        new Color(0.85f, 0.25f, 0.05f, 1f),
        new Color(0.90f, 0.35f, 0.10f, 1f),
        new Color(0.95f, 0.45f, 0.12f, 1f),
        new Color(0.80f, 0.20f, 0.05f, 1f),
        new Color(1.00f, 0.55f, 0.15f, 1f),
        new Color(0.88f, 0.30f, 0.08f, 1f),
        new Color(0.92f, 0.40f, 0.12f, 1f),
        new Color(0.75f, 0.18f, 0.04f, 1f),
        new Color(0.98f, 0.50f, 0.14f, 1f),
        new Color(0.83f, 0.22f, 0.06f, 1f)
    };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
