package it.jjdoes.Atomix.Systems;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.GroundedHandlers.GasHandler;
import it.jjdoes.Atomix.Systems.GroundedHandlers.NonstaticSolidHandler;
import it.jjdoes.Atomix.Systems.GroundedHandlers.LiquidHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GroundedHandler {
    public static void Update(Grid grid, IEntity entity, int row, int col) {
        if (entity.Has(EntityEnum.Grounded)) {
            if (entity.Has(EntityEnum.Solid)) {
                NonstaticSolidHandler.Update(grid, entity, row, col);
            } else if (entity.Has(EntityEnum.Liquid)) {
                LiquidHandler.Update(grid, entity, row, col);
            } else if (entity.Has(EntityEnum.Gas)) {
                GasHandler.Update(grid, entity, row, col);
            }
        }
    }
}
