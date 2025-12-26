package it.jjdoes.Atomix.Systems;

import com.badlogic.gdx.graphics.Color;

import it.jjdoes.Atomix.Components.Entity.Properties.Lifespan;
import it.jjdoes.Atomix.Components.Entity.Types.NonCollidable.NonCollidable;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class LifespanHandler {
    public static void Update(Grid grid, IEntity entity, int row, int col) {
        if (entity.Has(EntityEnum.Lifespan)) {
            // Fetch the lifespan of the entity
            Lifespan lifespan = (Lifespan) entity.Get(EntityEnum.Lifespan);
            int life = lifespan.GetLife();

            // If the lifespan has reached zero, replace the entity with air (deleting it)
            if (life == 0) {
                IEntity air = new Entity(Color.WHITE);
                air.Add(new NonCollidable(), EntityEnum.NonCollidable);
                grid.Replace(air, row, col);
            }
            // Decrease life
            else {
                lifespan.DecreaseLife();
            }
        }
    }
}
