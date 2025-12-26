package it.jjdoes.Atomix.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Renderer {
    private final SpriteBatch _batch;
    private final Texture texture;
    private final Pixmap pixmap;
    private final FitViewport _viewport;
    private final Stage _stage;

    public Renderer(int width, int height, int headerHeight) {
        pixmap = new Pixmap(width, height + headerHeight, Pixmap.Format.RGBA8888);
        texture = new Texture(pixmap);
        _viewport = new FitViewport(width, height + headerHeight);
        _batch = new SpriteBatch();
        _stage = new Stage(_viewport, _batch);
        Header.InitializeHeader(width, height + headerHeight, headerHeight, _stage);
        Gdx.input.setInputProcessor(_stage);
    }

    public void Render() {
        ScreenUtils.clear(Color.BLACK);
        texture.draw(pixmap, 0, 0);
        _viewport.apply();
        _batch.setProjectionMatrix(_viewport.getCamera().combined);
        _batch.begin();
        _batch.draw(texture, 0, 0);
        _batch.end();
        _stage.draw();
    }

    public void DrawParticle(Color color, int x, int y) {
        pixmap.setColor(color);
        pixmap.drawPixel(x, y);
    }

    public void Resize(int width, int height) {
        _viewport.update(width, height, true);
    }

    public Viewport GetViewport() {
        return _viewport;
    }
}
