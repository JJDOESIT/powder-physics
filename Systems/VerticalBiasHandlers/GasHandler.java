package it.jjdoes.Atomix.Systems.VerticalBiasHandlers;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GasHandler {
    public static boolean Update(Grid grid, int y, int dx, int dy) {
        IEntity above;

        // If the potential target is not directly above the current entity
        if (dy != y) {
            // If the potential target is in bounds
            if (dx > 0) {
                // If the entity is of type non-collidable or liquid, return true
                above = grid.GetEntity(dx - 1, dy);
                return (above.Has(EntityEnum.NonCollidable) || above.Has(EntityEnum.Liquid));
            }
        }
        return false;
    }
}
