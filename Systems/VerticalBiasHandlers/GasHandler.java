package it.jjdoes.Atomix.Systems.VerticalBiasHandlers;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GasHandler {
    public static boolean Update(Grid grid, IEntity entity, int y, int dx, int dy) {
        if (entity.Has(EntityEnum.VerticalBias)) {
            IEntity above;
            if (dy != y) {
                if (dx > 0) {
                    above = grid.GetEntity(dx - 1, dy);
                    return (above.Has(EntityEnum.NonCollidable) || above.Has(EntityEnum.Liquid));
                }
            }

        }
        return false;
    }
}
