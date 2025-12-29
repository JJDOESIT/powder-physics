package it.jjdoes.Atomix.Systems.VerticalBiasHandlers;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class NonstaticSolidHandler {
    public static boolean Update(Grid grid, int y, int dx, int dy) {
        // If the potential target is not directly underneath the current entity
        // Note: This is needed or else the current entity will always fall down
        //       since we process bottom-up
        if (dy != y) {
            // If the potential target is in bounds
            if (dx < grid.GetHeight() - 1) {
                // If the entity is of type non-collidable or liquid, or gas, return true
                IEntity below = grid.GetEntity(dx + 1, dy);
                return (below.Has(EntityEnum.NonCollidable) || below.Has(EntityEnum.Liquid) || below.Has(EntityEnum.Gas));
            }
        }
        return false;
    }
}
