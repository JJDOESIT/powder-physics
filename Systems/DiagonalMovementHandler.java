package it.jjdoes.Atomix.Systems;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Systems.DiagonalMovementHandlers.NonstaticSolidHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class DiagonalMovementHandler {
    public static void Update(Grid grid, IEntity entity, int row, int col) {
        if (entity.Has(EntityEnum.DiagonalMovement)) {
            if (entity.Has(EntityEnum.Solid) && entity.Has(EntityEnum.Gravity)) {
                NonstaticSolidHandler.Update(grid, entity, row, col);
            }
        }
    }
}
