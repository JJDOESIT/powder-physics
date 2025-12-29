package it.jjdoes.Atomix.Systems;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.VerticalBiasHandlers.GasHandler;
import it.jjdoes.Atomix.Systems.VerticalBiasHandlers.LiquidHandler;
import it.jjdoes.Atomix.Systems.VerticalBiasHandlers.NonstaticSolidHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class VerticalBiasHandler {
    public static boolean Update(Grid grid, IEntity entity, int y, int dx, int dy) {
        if (entity.Has(EntityEnum.VerticalBias)) {
            if (entity.Has(EntityEnum.Solid)) {
                return NonstaticSolidHandler.Update(grid, y, dx, dy);
            } else if (entity.Has(EntityEnum.Liquid)) {
                return LiquidHandler.Update(grid, y, dx, dy);
            } else if (entity.Has(EntityEnum.Gas)) {
                return GasHandler.Update(grid, y, dx, dy);
            }
        }
        return false;
    }
}
