package it.jjdoes.Atomix.Systems;

import java.util.ArrayList;

import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Engine.Logic.Logic;
import it.jjdoes.Atomix.Systems.PositionHandlers.GasHandler;
import it.jjdoes.Atomix.Systems.PositionHandlers.LiquidHandler;
import it.jjdoes.Atomix.Systems.PositionHandlers.NonstaticSolidHandler;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class PositionHandler {

    public static void Update(Grid grid, IEntity entity, int row, int col) {
        if (entity.Has(EntityEnum.Velocity)) {
            if (entity.Has(EntityEnum.Solid)) {
                NonstaticSolidHandler.Update(grid, entity, row, col);
            } else if (entity.Has(EntityEnum.Liquid)) {
                LiquidHandler.Update(grid, entity, row, col);
            } else if (entity.Has(EntityEnum.Gas)) {
                GasHandler.Update(grid, entity, row, col);
            }
        }
        entity.EnableUpdated();
    }

    public static ArrayList<int[]> MatrixTraversal(int x0, int y0, int x1, int y1) {
        ArrayList<int[]> steps = new ArrayList<int[]>();

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;

        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;

        int error = dx + dy;
        int e2;

        int step = 0;
        int maxSteps = Logic.GetMaxSteps();
        while (step < maxSteps) {
            e2 = 2 * error;
            if (e2 >= dy) {
                if (x0 == x1) {
                    break;
                }
                error += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                if (y0 == y1) {
                    break;
                }
                error += dx;
                y0 += sy;
            }
            steps.add(new int[]{x0, y0});
            step += 1;
        }
        return steps;
    }
}
