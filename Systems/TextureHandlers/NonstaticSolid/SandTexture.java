package it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class SandTexture {
    static Color[] colors = new Color[]
        {
            new Color(0.7608f, 0.6980f, 0.5020f, 1f),
            new Color(0.7843f, 0.7137f, 0.5216f, 1f),
            new Color(0.7412f, 0.6745f, 0.4706f, 1f),
            new Color(0.8039f, 0.7451f, 0.5490f, 1f),
            new Color(0.7765f, 0.7098f, 0.5098f, 1f),
            new Color(0.7529f, 0.6863f, 0.4902f, 1f),
            new Color(0.7882f, 0.7255f, 0.5294f, 1f),
            new Color(0.7333f, 0.6667f, 0.4627f, 1f),
            new Color(0.7961f, 0.7373f, 0.5412f, 1f),
            new Color(0.7647f, 0.7059f, 0.5020f, 1f)
        };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
