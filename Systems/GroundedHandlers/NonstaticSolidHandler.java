package it.jjdoes.Atomix.Systems.GroundedHandlers;

import static it.jjdoes.Atomix.Systems.PositionHandlers.NonstaticSolidHandler.CalculateVelocity;

import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class NonstaticSolidHandler {
    
    public static void Update(Grid grid, IEntity entity, int x, int y) {
        // Declare top-level components
        Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);

        // Declare height of the grid
        int height = grid.GetHeight();

        // If the entity is already grounded, return early
        // Note: This can be done because this means a block underneath has disabled this entity
        //       previously
        if (!grounded.IsGrounded()) {
            return;
        }

        IEntity below;

        // Bounds check
        if (x + 1 < height) {
            // Fetch the entity below
            below = grid.GetEntity(x + 1, y);

            // If the entity below is non-collidable, or a liquid, or a gas, then set grounded to false for the column
            if (below.Has(EntityEnum.NonCollidable) || below.Has(EntityEnum.Liquid) || below.Has(EntityEnum.Gas)) {
                DisableGrounded(grid, x, y);
            }
        }

        // If the entity is grounded, recalculate velocity
        if (grounded.IsGrounded()) {
            CalculateVelocity(grid, entity);
        }
    }

    public static void DisableGrounded(Grid grid, int x, int y) {
        // Scan vertically upwards through the column
        for (int dx = x; dx >= 0; dx--) {
            // Fetch the current entity
            IEntity next = grid.GetEntity(dx, y);

            // If the entity is not a nonstatic solid, break
            if (!(next.Has(EntityEnum.Solid) && next.Has(EntityEnum.Gravity))) {
                break;
            }

            // Set the entity to be grounded
            Grounded grounded = (Grounded) next.Get(EntityEnum.Grounded);
            grounded.SetIsGrounded(false);
        }
    }
}
