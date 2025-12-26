package it.jjdoes.Atomix.Systems.GroundedHandlers;

import static it.jjdoes.Atomix.Systems.PositionHandlers.GasHandler.CalculateVelocity;

import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GasHandler {

    // General update function for the nonstatic solid grounded handler
    public static void Update(Grid grid, IEntity entity, int x, int y) {
        Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);

        // If the entity has not been processed yet
        if (grounded.IsGrounded()) {
            // Fetch the cell above
            IEntity above = x - 1 >= 0 ? grid.GetEntity(x - 1, y) : null;

            // If the cell is out of bounds, or if it is of type solid, apply rank for the column
            if (above == null || above.Has(EntityEnum.Solid)) {
                CalculateVelocity(grid, entity, x, y);
            }
            // If the cell is of type non-collidable, set grounded to false
            else if (above.Has(EntityEnum.NonCollidable)) {
                grounded.SetIsGrounded(false);
            }
            // If the cell is of type gas
            else if (above.Has(EntityEnum.Gas)) {
                // Compare buoyancy
                int entityBuoyancy = ((Buoyancy) entity.Get(EntityEnum.Buoyancy)).GetRank();
                int aboveBuoyancy = ((Buoyancy) above.Get(EntityEnum.Buoyancy)).GetRank();

                if (entityBuoyancy > aboveBuoyancy) {
                    grounded.SetIsGrounded(false);
                }
            }
        }

        // If the cell is grounded, calculate velocity
        if (grounded.IsGrounded()) {
            CalculateVelocity(grid, entity, x, y);
        }
    }
}
