package it.jjdoes.Atomix;

import com.badlogic.gdx.ApplicationListener;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Engine.InputProcessing.InputProcessor;
import it.jjdoes.Atomix.Engine.Logic.Logic;
import it.jjdoes.Atomix.Engine.Rendering.Renderer;
import it.jjdoes.Atomix.Types.World.IWorld;
import it.jjdoes.Atomix.Types.World.World;

public class Main implements ApplicationListener {
    private Renderer _renderer;
    private InputProcessor _inputProcessor;
    private Logic _logic;
    private IWorld _world;

    @Override
    public void create() {
        final int width = 204;
        final int height = 204;
        final int headerHeight = 99;

        _renderer = new Renderer(width, height, headerHeight);
        _inputProcessor = new InputProcessor();
        _logic = new Logic();
        _world = new World().Add(new Grid(width, height));
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        _renderer.Resize(width, height);
    }

    @Override
    public void render() {
        Grid grid = (Grid) _world.Get(Grid.class);
        _renderer.Render(grid);
        _inputProcessor.Update(grid, _renderer);
        _logic.Update(grid);
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
