package it.jjdoes.Atomix.Systems.PositionHandlers;

import static it.jjdoes.Atomix.Systems.PositionHandler.MatrixTraversal;

import java.util.ArrayList;

import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.Behaviors.Freefalling;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.DiagonalMovementHandler;
import it.jjdoes.Atomix.Systems.ImpactScatterHandler;
import it.jjdoes.Atomix.Systems.VerticalBiasHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class NonstaticSolidHandler {
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
            // Declare the coordinates of the next step
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

                // If vertical bias is enabled, and if the entity can move down
                if (VerticalBiasHandler.Update(grid, entity, y, dx, dy)) {
                    // Swap the entity with the entity under the next entity
                    // Note: Instead of looking at the cell directly under the entity, we look at the
                    //       cell under the next entity in the path. This is done so we still simulate
                    //       horizontal movement.
                    grid.Swap(x, y, dx + 1, dy);
                    x = dx + 1;
                    y = dy;
                    exit = true;
                }
                // If the next entity is of type solid
                else if (next.Has(EntityEnum.Solid)) {
                    // Clear the horizontal velocity and exit
                    velocity.SetVelocity(0, velocity.GetYVelocity());
                    exit = true;
                }
                // If the next entity is of type non-collidable
                else if (next.Has(EntityEnum.NonCollidable)) {
                    // Swap and continue
                    grid.Swap(x, y, dx, dy);
                    x = dx;
                    y = dy;
                }
                // If the next entity is of type liquid
                else if (next.Has(EntityEnum.Liquid)) {
                    // Swap with the liquid
                    grid.Swap(x, y, dx, dy);
                    x = dx;
                    y = dy;

                    exit = true;
                }
                // If the next entity is of type gas
                else if (next.Has(EntityEnum.Gas)) {
                    // Swap with the gas
                    grid.Swap(x, y, dx, dy);
                    x = dx;
                    y = dy;
                }

                // If the entity has free-falling enabled
                if (entity.Has(EntityEnum.Freefalling)) {
                    CheckFreefalling(grid, entity, x, y);
                }
                stepIndex += 1;
            }
        }
        DiagonalMovementHandler.Update(grid, entity, x, y);
    }

    // General function to update velocity of a given entity
    public static void CalculateVelocity(Grid grid, IEntity entity) {
        // Declare top-level components
        Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);

        if (entity.Has(EntityEnum.ImpactScatter)) {
            ImpactScatterHandler.Update(entity);
        }
        velocity.SetVelocity(velocity.GetXVelocity(), 0);
    }

    private static void CheckFreefalling(Grid grid, IEntity entity, int x, int y) {
        // Declare top-level components
        Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);

        // Declare the width of the grid
        int width = grid.GetWidth();

        // If the entity is grounded, return
        if (grounded.IsGrounded()) {
            return;
        }

        // Fetch the left and right entities
        IEntity left = null;
        IEntity right = null;

        if (y - 1 >= 0) {
            left = grid.GetEntity(x, y - 1);
        }
        if (y + 1 < width) {
            right = grid.GetEntity(x, y + 1);
        }

        // If the left entity is not null
        if (left != null) {
            // If the left entity has freefalling class
            if (left.Has(EntityEnum.Freefalling)) {
                // Fetch top-level components
                Freefalling freefalling = (Freefalling) left.Get(EntityEnum.Freefalling);
                Velocity velocity = (Velocity) left.Get(EntityEnum.Velocity);
                grounded = left.Has(EntityEnum.Grounded) ? (Grounded) left.Get(EntityEnum.Grounded) : null;

                // If grounded is null or if the entity is grounded, update horizontal velocity
                if (grounded == null || grounded.IsGrounded()) {
                    velocity.UpdateVelocity(freefalling.GetStrength(), 0);
                }
            }
        }

        // If the right entity is not null
        if (right != null) {
            // If the right entity has freefalling class
            if (right.Has(EntityEnum.Freefalling)) {
                // Fetch top-level components
                Freefalling freefalling = (Freefalling) right.Get(EntityEnum.Freefalling);
                Velocity velocity = (Velocity) right.Get(EntityEnum.Velocity);
                grounded = right.Has(EntityEnum.Grounded) ? (Grounded) right.Get(EntityEnum.Grounded) : null;

                // If grounded is null or if the entity is grounded, update horizontal velocity
                if (grounded == null || grounded.IsGrounded()) {
                    velocity.UpdateVelocity(-freefalling.GetStrength(), 0);
                }
            }
        }
    }
}
