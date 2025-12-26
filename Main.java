package it.jjdoes.Atomix;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.jjdoes.Atomix.Components.Entity.Behaviors.Corrosive;
import it.jjdoes.Atomix.Components.Entity.Behaviors.NonstaticGas;
import it.jjdoes.Atomix.Components.Entity.Behaviors.VerticalBias;
import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.Behaviors.DiagonalMovement;
import it.jjdoes.Atomix.Components.Entity.Behaviors.ImpactScatter;
import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Lifespan;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Pressure;
import it.jjdoes.Atomix.Components.Entity.Properties.Resistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Spread;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.Behaviors.Freefalling;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Gas;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes.Acid;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes.Smoke;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Liquid;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Honey;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Lava;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Oil;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Water;
import it.jjdoes.Atomix.Components.Entity.Types.NonCollidable.NonCollidable;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Solid;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Dirt;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Sand;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Stone;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.InputProcessing.InputProcessor;
import it.jjdoes.Atomix.Rendering.Renderer;
import it.jjdoes.Atomix.Systems.GravityHandler;
import it.jjdoes.Atomix.Systems.GroundedHandler;
import it.jjdoes.Atomix.Systems.LifespanHandler;
import it.jjdoes.Atomix.Systems.PositionHandler;
import it.jjdoes.Atomix.Systems.ResistanceHandler;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.AcidTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.LavaTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.WaterTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.DirtTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.SandTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.StaticSolid.StoneTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;
import it.jjdoes.Atomix.Types.World.IWorld;
import it.jjdoes.Atomix.Types.World.World;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main implements ApplicationListener {
    private Renderer _renderer;
    private InputProcessor _inputProcessor;
    private IWorld _world;
    private final int _width = 264;
    private final int _headerHeight = 120;
    private final int _height = 264;
    private float _timeSinceLastDraw = 0;
    private static TextButton _selectedType;
    private static int _brushSize = 50;
    private final Random rng = new Random();
    private boolean evenFirst = true;
    private ExecutorService _executor;

    @Override
    public void create() {
        // Prepare your application here.
        _renderer = new Renderer(_width, _height, _headerHeight);
        _inputProcessor = new InputProcessor();
        _world = new World().Add(new Grid(_width, _height));
        _executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if (width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
        _renderer.Resize(width, height);
    }

    public static void SetSelectedType(TextButton type) {
        _selectedType = type;
    }

    public static void SetBrushSize(int size) {
        _brushSize = size;
    }

    public static int GetBrushSize() {
        return _brushSize;
    }

    public static TextButton GetSelectedType() {
        return _selectedType;
    }

    @Override
    public void render() {
        // Draw your application here.
        Grid grid = (Grid) _world.Get(Grid.class);
        float deltaTime = Gdx.graphics.getDeltaTime();
        logic();

        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                IEntity entity = grid.GetEntity(row, col);
                _renderer.DrawParticle(entity.GetColor(), col, row + _headerHeight);
            }
        }
        _renderer.Render();
        _inputProcessor.Update((Grid) _world.Get(Grid.class), _renderer);

    }

    public void logic() {
        logicNoAsync();
    }

    private List<Callable<Void>> createChunkTasks(
        Grid grid,
        int startIdx,
        int chunkWidth,
        int numChunks,
        float deltaTime,
        int offset) {

        List<Callable<Void>> tasks = new ArrayList<>();

        // build boundaries based on offset
        List<Integer> boundaries = new ArrayList<>();
        boundaries.add(0);
        int start = offset;
        while (start < grid.GetWidth()) {
            boundaries.add(start);
            start += chunkWidth;
        }

        if (boundaries.get(boundaries.size() - 1) != grid.GetWidth()) {
            boundaries.add(grid.GetWidth());
        }

        // now iterate pairs of boundaries, skipping even/odd if needed
        for (int i = startIdx; i < boundaries.size() - 1; i += 2) {
            int x = boundaries.get(i);
            int x2 = boundaries.get(i + 1);

            ArrayList<Integer> cols = new ArrayList<>();
            for (int col = x; col < x2; col++) {
                if (col >= 0 && col < grid.GetWidth()) {
                    cols.add(col);
                }
            }

            Collections.shuffle(cols);

            tasks.add(() -> {
                for (int row = grid.GetHeight() - 1; row >= 0; row--) {
                    for (int col : cols) {
                        IEntity entity = grid.GetEntity(row, col);
                        if (entity.IsUpdated()) continue;

                        GravityHandler.Update(entity, deltaTime);
                        ResistanceHandler.Update(entity);
                        PositionHandler.Update(grid, entity, row, col);

                        entity.EnableUpdated();
                    }
                }
                return null;
            });
        }

        return tasks;
    }

    private List<Callable<Void>> CreateThreadUnsafeTasks(Grid grid) {
        List<Callable<Void>> tasks = new ArrayList<>();

        tasks.add(() -> {
            for (int row = grid.GetHeight() - 1; row >= 0; row--) {
                for (int col = 0; col < grid.GetWidth(); col++) {
                    IEntity entity = grid.GetEntity(row, col);
                    GroundedHandler.Update(grid, entity, row, col);
                    LifespanHandler.Update(grid, entity, row, col);
                }
            }
            return null;
        });

        return tasks;
    }

    private List<Callable<Void>> CreateResetTasks(Grid grid,
                                                  int chunkWidth) {
        List<Callable<Void>> tasks = new ArrayList<>();
        // build boundaries based on offset
        List<Integer> boundaries = new ArrayList<>();
        boundaries.add(0);
        int start = 0;
        while (start < grid.GetWidth()) {
            boundaries.add(start);
            start += chunkWidth;
        }

        if (boundaries.get(boundaries.size() - 1) != grid.GetWidth()) {
            boundaries.add(grid.GetWidth());
        }

        // now iterate pairs of boundaries, skipping even/odd if needed
        for (int i = 0; i < boundaries.size() - 1; i += 1) {
            int x = boundaries.get(i);
            int x2 = boundaries.get(i + 1);

            ArrayList<Integer> cols = new ArrayList<>();
            for (int col = x; col < x2; col++) {
                if (col >= 0 && col < grid.GetWidth()) {
                    cols.add(col);
                }
            }

            tasks.add(() -> {
                for (int row = grid.GetHeight() - 1; row >= 0; row--) {
                    for (int col : cols) {
                        IEntity entity = grid.GetEntity(row, col);
                        entity.DisableUpdated();

                        if (entity.Has(EntityEnum.Grounded)) {
                            Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);
                            grounded.SetIsGrounded(true);
                            grounded.SetRank(-1);
                        }
                    }
                }
                return null;
            });
        }
        return tasks;
    }


    public void logicAsync() {
        Grid grid = (Grid) _world.Get(Grid.class);
        float deltaTime = Gdx.graphics.getDeltaTime();

        //if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
        final int chunkWidth = 50;
        int numChunks = grid.GetWidth() / chunkWidth;

        // frame-wide offset
        int offset = rng.nextInt(50);

        if (rng.nextBoolean()) offset = -offset;

        List<Callable<Void>> groundedTasks = CreateThreadUnsafeTasks(grid);

        try {
            _executor.invokeAll(groundedTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            List<Callable<Void>> firstTasks = createChunkTasks(grid, evenFirst ? 0 : 1, chunkWidth, numChunks, deltaTime, offset);
            List<Callable<Void>> secondTasks = createChunkTasks(grid, evenFirst ? 1 : 0, chunkWidth, numChunks, deltaTime, offset);

            _executor.invokeAll(firstTasks);
            _executor.invokeAll(secondTasks);
            evenFirst = !evenFirst;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Callable<Void>> resetTasks = CreateResetTasks(grid, 50);
        try {
            _executor.invokeAll(resetTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logicNoAsync() {
        Grid grid = (Grid) _world.Get(Grid.class);
        float deltaTime = Gdx.graphics.getDeltaTime();

        for (int row = grid.GetHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < grid.GetWidth(); col++) {
                IEntity entity = grid.GetEntity(row, col);
                GroundedHandler.Update(grid, entity, row, col);
                LifespanHandler.Update(grid, entity, row, col);
            }
        }


        for (int row = grid.GetHeight() - 1; row >= 0; row--) {
            ArrayList<Integer> cols = new ArrayList<Integer>();
            for (int column = 0; column < grid.GetWidth(); column++) {
                cols.add(column);
            }

            Collections.shuffle(cols);

            for (int col : cols) {
                IEntity entity = grid.GetEntity(row, col);
                if (entity.IsUpdated()) continue;

                GravityHandler.Update(entity, deltaTime);
                ResistanceHandler.Update(entity);
                PositionHandler.Update(grid, entity, row, col);

                entity.EnableUpdated();
            }
        }

        // Post-processing
        for (int row = grid.GetHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < grid.GetWidth(); col++) {
                IEntity entity = grid.GetEntity(row, col);
                entity.DisableUpdated();

                if (entity.Has(EntityEnum.Grounded)) {
                    Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);
                    grounded.SetIsGrounded(true);
                    grounded.SetRank(-1);
                }
            }
        }
    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}
