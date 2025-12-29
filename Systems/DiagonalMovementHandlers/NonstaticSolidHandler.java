package it.jjdoes.Atomix.Systems.DiagonalMovementHandlers;

import java.util.concurrent.ThreadLocalRandom;

import it.jjdoes.Atomix.Components.Entity.Behaviors.DiagonalMovement;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class NonstaticSolidHandler {
    public static void Update(Grid grid, IEntity entity, int row, int col) {
        // Declare top-level components
        Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);
        DiagonalMovement diagonalMovement = (DiagonalMovement) entity.Get(EntityEnum.DiagonalMovement);

        // Declare width and height of the grid
        int height = grid.GetHeight();
        int width = grid.GetWidth();

        // Out-of-bounds, break early
        if (row + 1 >= height) {
            return;
        }

        // If the entity is not grounded, or the entity below is not a solid, break early
        if (!grounded.IsGrounded() || !grid.GetEntity(row + 1, col).Has(EntityEnum.Solid)) {
            return;
        }

        // Fetch the entities in the cells diagonally left and right of the entity,
        // and verify that they are of type empty, gas, or liquid
        IEntity leftEntity = col - 1 >= 0 ? grid.GetEntity(row + 1, col - 1) : null;
        IEntity rightEntity = col + 1 < width ? grid.GetEntity(row + 1, col + 1) : null;
        if (leftEntity != null && !((leftEntity.Has(EntityEnum.NonCollidable) || leftEntity.Has(EntityEnum.Liquid) || leftEntity.Has(EntityEnum.Gas)))) {
            leftEntity = null;
        }
        if (rightEntity != null && !((rightEntity.Has(EntityEnum.NonCollidable) || rightEntity.Has(EntityEnum.Liquid) || rightEntity.Has(EntityEnum.Gas)))) {
            rightEntity = null;
        }

        // If at least one of the diagonal entities is empty
        if (leftEntity != null || rightEntity != null) {
            // If the entity is allowed to move diagonally
            if (diagonalMovement.CanMove(row, col)) {
                diagonalMovement.SetPosition(row, col);
                if (ThreadLocalRandom.current().nextFloat() < diagonalMovement.GetStrength()) {
                    // Choose a random direction if the entity can move left or right
                    if (leftEntity != null && rightEntity != null) {
                        boolean direction = ThreadLocalRandom.current().nextBoolean();
                        if (direction) {
                            grid.Swap(row, col, row + 1, col - 1);
                        } else {
                            grid.Swap(row, col, row + 1, col + 1);
                        }
                    }
                    // Move right diagonally
                    else if (rightEntity != null) {
                        grid.Swap(row, col, row + 1, col + 1);
                    }
                    // Move left diagonally
                    else {
                        grid.Swap(row, col, row + 1, col - 1);
                    }
                }
            }
        }
    }
}
