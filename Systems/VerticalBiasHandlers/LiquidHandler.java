package it.jjdoes.Atomix.Systems.VerticalBiasHandlers;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class LiquidHandler {
    public static boolean Update(Grid grid, int y, int dx, int dy) {
        IEntity below;

        // If the potential target is not directly below the current entity
        if (dy != y) {
            // If the potential target is in bounds
            if (dx < grid.GetHeight() - 1) {
                // If the entity is of type non-collidable, return true
                below = grid.GetEntity(dx + 1, dy);
                return (below.Has(EntityEnum.NonCollidable));
            }
        }
        return false;
    }
}
