package it.jjdoes.Atomix.Engine.Logic;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.GravityHandler;
import it.jjdoes.Atomix.Systems.GroundedHandler;
import it.jjdoes.Atomix.Systems.LifespanHandler;
import it.jjdoes.Atomix.Systems.PositionHandler;
import it.jjdoes.Atomix.Systems.ResistanceHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Logic {
    private final Random _random;
    private boolean _evenFirst;
    private final ExecutorService _executor;
    private static int _maxSteps;
    private final int _numChunks;

    public Logic() {
        _random = new Random();
        _evenFirst = true;
        _executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        _maxSteps = 15;
        _numChunks = 12;
    }

    public void Update(Grid grid) {
        LogicAsync(grid);
    }

    private List<Callable<Void>> CreateChunkTasks(Grid grid, int startIndex, int chunkWidth, float deltaTime, int offset) {
        List<Callable<Void>> tasks = new ArrayList<>();

        // Build boundaries based on offset
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

        // Iterate pairs of boundaries, skipping even/odd if needed
        for (int i = startIndex; i < boundaries.size() - 1; i += 2) {
            int x = boundaries.get(i);
            int x2 = boundaries.get(i + 1);

            ArrayList<Integer> cols = new ArrayList<>();
            for (int col = x; col < x2; col++) {
                if (col >= 0 && col < grid.GetWidth()) {
                    cols.add(col);
                }
            }

            // Randomize column order
            Collections.shuffle(cols);

            tasks.add(() -> {
                for (int row = grid.GetHeight() - 1; row >= 0; row--) {
                    for (int col : cols) {
                        IEntity entity = grid.GetEntity(row, col);

                        if (entity.IsUpdated()) {
                            continue;
                        }

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

    private void LogicAsync(Grid grid) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Determine chunk width
        int chunkWidth = grid.GetWidth() / _numChunks;

        // Frame-wide offset
        int offset = chunkWidth;
        if (_random.nextBoolean()) {
            offset = -offset;
        }

        // Initial pass
        try {
            for (int row = grid.GetHeight() - 1; row >= 0; row--) {
                for (int col = 0; col < grid.GetWidth(); col++) {
                    IEntity entity = grid.GetEntity(row, col);
                    GroundedHandler.Update(grid, entity, row, col);
                    LifespanHandler.Update(grid, entity, row, col);
                }
            }

            // Multi-threaded pass
            List<Callable<Void>> firstTasks = CreateChunkTasks(grid, _evenFirst ? 0 : 1, chunkWidth, deltaTime, offset);
            List<Callable<Void>> secondTasks = CreateChunkTasks(grid, _evenFirst ? 1 : 0, chunkWidth, deltaTime, offset);
            _executor.invokeAll(firstTasks);
            _executor.invokeAll(secondTasks);

            // Reset pass
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
            _evenFirst = !_evenFirst;
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void LogicNoAsync(Grid grid) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Initial pass
        for (int row = grid.GetHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < grid.GetWidth(); col++) {
                IEntity entity = grid.GetEntity(row, col);
                GroundedHandler.Update(grid, entity, row, col);
                LifespanHandler.Update(grid, entity, row, col);
            }
        }

        // Main pass
        for (int row = grid.GetHeight() - 1; row >= 0; row--) {
            ArrayList<Integer> cols = new ArrayList<Integer>();
            for (int column = 0; column < grid.GetWidth(); column++) {
                cols.add(column);
            }

            Collections.shuffle(cols);

            for (int col : cols) {
                IEntity entity = grid.GetEntity(row, col);
                if (entity.IsUpdated()) {
                    continue;
                }

                GravityHandler.Update(entity, deltaTime);
                ResistanceHandler.Update(entity);
                PositionHandler.Update(grid, entity, row, col);

                entity.EnableUpdated();
            }
        }

        // Reset pass
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

    public static int GetMaxSteps() {
        return _maxSteps;
    }
}
