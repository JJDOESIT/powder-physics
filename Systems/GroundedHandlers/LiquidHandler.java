package it.jjdoes.Atomix.Systems.GroundedHandlers;

import static it.jjdoes.Atomix.Systems.PositionHandlers.LiquidHandler.CalculateVelocity;

import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class LiquidHandler {

    public static void Update(Grid grid, IEntity entity, int x, int y) {
        int height = grid.GetHeight();
        Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);

        // If the entity has not been processed yet
        if (grounded.GetRank() == -1 && grounded.IsGrounded()) {
            IEntity below = (x + 1 < height) ? grid.GetEntity(x + 1, y) : null;

            // If the entity below the liquid is null
            if (below == null) {
                ApplyRank(grid, x, y);
            }
            // If the entity below the liquid is of type solid
            else if (below.Has(EntityEnum.Solid)) {
                if (entity.Has(EntityEnum.Corrosive) && !below.Has(EntityEnum.Corrosive)) {
                    DisableGrounded(grid, x, y);
                } else {
                    ApplyRank(grid, x, y);
                }
            } else if (below.Has(EntityEnum.NonCollidable) || below.Has(EntityEnum.Gas)) {
                DisableGrounded(grid, x, y);
            } else if (below.Has(EntityEnum.Liquid)) {
                boolean entityAcidic = entity.Has(EntityEnum.Corrosive);
                boolean belowAcidic = below.Has(EntityEnum.Corrosive);

                if (entityAcidic && !belowAcidic) {
                    DisableGrounded(grid, x, y);
                } else {
                    int entityBuoyancy = ((Buoyancy) entity.Get(EntityEnum.Buoyancy)).GetRank();
                    int belowBuoyancy = ((Buoyancy) below.Get(EntityEnum.Buoyancy)).GetRank();

                    if (entityBuoyancy > belowBuoyancy) {
                        DisableGrounded(grid, x, y);
                    } else if (belowBuoyancy > entityBuoyancy) {
                        ApplyRank(grid, x, y);
                    }
                }
            }
        }

        if (grounded.IsGrounded()) {
            CalculateVelocity(grid, entity, x, y);
        }
    }

    // Sets grounded to false for the whole column of similar type
    public static void DisableGrounded(Grid grid, int x, int y) {
        int prevBuoyancy = -1;
        Boolean columnCorrosive = null;

        for (int dx = x; dx >= 0; dx--) {
            IEntity e = grid.GetEntity(dx, y);
            if (!e.Has(EntityEnum.Liquid)) break;

            boolean acidic = e.Has(EntityEnum.Acid);
            if (columnCorrosive == null) {
                columnCorrosive = acidic;
            } else if (acidic != columnCorrosive) {
                break;
            }

            int buoyancy = ((Buoyancy) e.Get(EntityEnum.Buoyancy)).GetRank();

            if (prevBuoyancy != -1 && buoyancy < prevBuoyancy) break;
            prevBuoyancy = buoyancy;

            Grounded g = (Grounded) e.Get(EntityEnum.Grounded);
            g.SetIsGrounded(false);
        }
    }

    // Apply a chronological rank to a grounded column of similar types
    public static void ApplyRank(Grid grid, int x, int y) {
        int length = 0;
        int prevBuoyancy = -1;
        Boolean columnCorrosive = null;

        // First pass: measure
        int dx = x;
        while (dx >= 0) {
            IEntity entity = grid.GetEntity(dx, y);

            if (!entity.Has(EntityEnum.Liquid)) {
                break;
            }

            boolean corrosive = entity.Has(EntityEnum.Corrosive);
            if (columnCorrosive == null) {
                columnCorrosive = corrosive;
            } else if (corrosive != columnCorrosive) {
                break;
            }

            int buoyancy = ((Buoyancy) entity.Get(EntityEnum.Buoyancy)).GetRank();
            if (prevBuoyancy != -1 && buoyancy > prevBuoyancy) {
                break;
            }
            prevBuoyancy = buoyancy;

            length++;
            dx--;
        }

        // Second pass: apply directly
        for (int i = 0; i < length; i++) {
            IEntity e = grid.GetEntity(x - i, y);
            Grounded g = (Grounded) e.Get(EntityEnum.Grounded);
            g.SetRank(length - i);
            g.SetLength(length);
        }
    }
}

