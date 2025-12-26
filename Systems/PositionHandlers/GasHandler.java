package it.jjdoes.Atomix.Systems.PositionHandlers;

import static it.jjdoes.Atomix.Systems.PositionHandler.MatrixTraversal;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.Properties.Spread;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.VerticalBiasHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GasHandler {
    // General update function for the nonstatic liquid position handler
    public static void Update(Grid grid, IEntity entity, int x, int y) {
        // Declare top-level components
        Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);

        // Fetch velocity, and round down to the nearest integer if positive, or up if negative
        float xVelocity = velocity.GetXVelocity();
        float yVelocity = velocity.GetYVelocity();
        xVelocity = xVelocity >= 0 ? (float) Math.floor(xVelocity) : (float) Math.ceil(xVelocity);
        yVelocity = yVelocity >= 0 ? (float) Math.floor(yVelocity) : (float) Math.ceil(yVelocity);

        // Fetch the given path of the entity based on its velocity
        ArrayList<int[]> steps = MatrixTraversal(x, y, (int) (x + yVelocity), (int) (y + xVelocity));

        // Declare width and height of the grid
        int height = grid.GetHeight();
        int width = grid.GetWidth();

        // Loop through each possible step in the given path of the entity
        int stepIndex = 0;
        boolean exit = false;
        while (stepIndex < steps.size() && !exit) {
            int[] step = steps.get(stepIndex);
            int dx = step[0];
            int dy = step[1];

            // If the next step is out of bounds
            if (dx < 0 || dx >= height || dy < 0 || dy >= width) {
                // If the next step takes you out of bounds either left or right,
                // clear the horizontal velocity
                if (dy < 0 || dy >= width) {
                    velocity.SetVelocity(0, velocity.GetYVelocity());
                }
                exit = true;
            }
            // If the next step is in bounds
            else {
                // Fetch the next entity in the path
                IEntity next = grid.GetEntity(dx, dy);

                // If vertical bias is enabled, and the gas can move up,
                // move up
                if (VerticalBiasHandler.Update(grid, entity, y, dx, dy)) {
                    // Swap the entity with the entity under the next entity
                    // Note: Instead of looking at the cell directly under the entity, we look at the
                    //       cell under the next entity in the path. This is done so we still simulate
                    //       horizontal movement.
                    grid.Swap(x, y, dx - 1, dy);
                    x = dx - 1;
                    y = dy;
                    exit = true;
                }
                // If the next entity is of type solid
                else if (next.Has(EntityEnum.Solid)) {
                    // Clear the horizontal velocity and exit
                    velocity.SetVelocity(0, velocity.GetYVelocity());
                    exit = true;
                }
                // If the next entity is of type liquid or non-collidable
                else if (next.Has(EntityEnum.Liquid) || next.Has(EntityEnum.NonCollidable)) {
                    grid.Swap(x, y, dx, dy);
                    x = dx;
                    y = dy;
                }
                // If the next entity is a gas
                else if (next.Has(EntityEnum.Gas)) {
                    // Compare the buoyancy
                    int entityBuoyancy = ((Buoyancy) entity.Get(EntityEnum.Buoyancy)).GetRank();
                    int nextBuoyancy = ((Buoyancy) next.Get(EntityEnum.Buoyancy)).GetRank();

                    if (entityBuoyancy > nextBuoyancy) {
                        grid.Swap(x, y, dx, dy);
                    }
                }
                stepIndex += 1;
            }
        }
    }

    // General function to update velocity of a given entity
    public static void CalculateVelocity(Grid grid, IEntity entity, int x, int y) {
        // Declare top-level components
        Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);
        Spread spread = (Spread) entity.Get(EntityEnum.Spread);

        // Check if there is a lower empty cell on the left or right of the entity
        boolean left = IsHigherPoint(grid, entity, x, y, -1);
        boolean right = IsHigherPoint(grid, entity, x, y, 1);

        float xVelocity = (spread.GetMaximum() - spread.GetMinimum()) * ThreadLocalRandom.current().nextFloat() + spread.GetMinimum();

        // If theres a lower point on the left or right side, or no lower point, choose a random direction
        if (left == right) {
            velocity.SetVelocity(ThreadLocalRandom.current().nextBoolean() ? -xVelocity : xVelocity, 0);
        }
        // Go towards the lower point on the left
        else if (left) {
            velocity.SetVelocity(-xVelocity, 0);
        }
        // Go towards the lower point on the right
        else {
            velocity.SetVelocity(xVelocity, 0);
        }
    }

    // General function to determine if there is a lower available cell within the given distance
    public static boolean IsHigherPoint(Grid grid, IEntity entity, int x, int y, int direction) {
        // Declare top-level components
        Spread spread = (Spread) entity.Get(EntityEnum.Spread);
        int spreadDistance = spread.GetDistance();

        // Declare width of the grid
        int width = grid.GetWidth();

        int step = 0;
        y += direction;

        // While we are still within the search distance
        while (step < spreadDistance) {
            IEntity below = null;
            IEntity next = null;

            // Fetch the next cell horizontally
            if (y >= 0 && y < width) {
                next = grid.GetEntity(x, y);
            }

            // Determine if it is "empty"
            boolean nextEmpty = next != null && (next.Has(EntityEnum.NonCollidable) || next.Has(EntityEnum.Liquid));

            // If it's not empty, return
            if (!nextEmpty) {
                return false;
            }

            // Fetch the next cell above
            if (x - 1 >= 0) {
                below = grid.GetEntity(x - 1, y);
            }

            // Determine if the cell is "empty"
            boolean belowEmpty = false;
            if (below != null) {
                if (below.Has(EntityEnum.NonCollidable)) {
                    belowEmpty = true;
                }
            }

            // If an available cell was found, return
            if (belowEmpty) {
                return true;
            }

            y += direction;
            step += 1;
        }
        return false;
    }
}
