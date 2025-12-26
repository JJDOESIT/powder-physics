package it.jjdoes.Atomix.Systems.VerticalBiasHandlers;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class LiquidHandler {
    public static boolean Update(Grid grid, IEntity entity, int y, int dx, int dy) {
        if (entity.Has(EntityEnum.VerticalBias)) {
            IEntity below;
            if (dy != y) {
                if (dx < grid.GetHeight() - 1) {
                    below = grid.GetEntity(dx + 1, dy);
                    return (below.Has(EntityEnum.NonCollidable));
                }
            }
        }
        return false;
    }
}
