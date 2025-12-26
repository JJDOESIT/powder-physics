package it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid;

import com.badlogic.gdx.graphics.Color;

import java.util.concurrent.ThreadLocalRandom;

public class DirtTexture {
    static Color[] colors = new Color[]
        {
            new Color(0.4314f, 0.3451f, 0.2549f, 1f),
            new Color(0.4549f, 0.3647f, 0.2745f, 1f),
            new Color(0.4078f, 0.3216f, 0.2353f, 1f),
            new Color(0.4706f, 0.3882f, 0.2941f, 1f),
            new Color(0.4431f, 0.3569f, 0.2627f, 1f),
            new Color(0.4196f, 0.3333f, 0.2431f, 1f),
            new Color(0.4627f, 0.3804f, 0.2863f, 1f),
            new Color(0.3961f, 0.3098f, 0.2275f, 1f),
            new Color(0.4784f, 0.4000f, 0.3059f, 1f),
            new Color(0.4353f, 0.3490f, 0.2588f, 1f)
        };

    public static Color GetColor() {
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }
}
