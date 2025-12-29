package it.jjdoes.Atomix.Engine.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Renderer {
    private final int _headerHeight;
    private final SpriteBatch _batch;
    private final Texture _texture;
    private final Pixmap _pixmap;
    private final FitViewport _viewport;
    private final Stage _stage;

    public Renderer(int width, int height, int headerHeight) {
        _pixmap = new Pixmap(width, height + headerHeight, Pixmap.Format.RGBA8888);
        _texture = new Texture(_pixmap);
        _viewport = new FitViewport(width, height + headerHeight);
        _batch = new SpriteBatch();
        _stage = new Stage(_viewport, _batch);
        _headerHeight = headerHeight;

        Header.InitializeHeader(width, height, headerHeight, _stage);
        Gdx.input.setInputProcessor(_stage);
    }

    public void Render(Grid grid) {
        DrawParticles(grid);

        ScreenUtils.clear(Color.BLACK);
        _viewport.apply();

        _texture.draw(_pixmap, 0, 0);
       
        _batch.setProjectionMatrix(_viewport.getCamera().combined);
        _batch.begin();
        _batch.draw(_texture, 0, 0);
        _batch.end();
        _stage.draw();
    }

    private void DrawParticles(Grid grid) {
        // Declare width and height of the grid
        int height = grid.GetHeight();
        int width = grid.GetWidth();

        // Draw each particle to the pixmap
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                IEntity entity = grid.GetEntity(row, col);
                _pixmap.setColor(entity.GetColor());
                _pixmap.drawPixel(col, row + _headerHeight);
            }
        }
    }

    public void Resize(int width, int height) {
        _viewport.update(width, height, true);
    }

    public Viewport GetViewport() {
        return _viewport;
    }
}
