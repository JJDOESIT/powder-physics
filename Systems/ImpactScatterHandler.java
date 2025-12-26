package it.jjdoes.Atomix.Systems;

import java.util.concurrent.ThreadLocalRandom;

import it.jjdoes.Atomix.Components.Entity.Behaviors.ImpactScatter;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class ImpactScatterHandler {
    public static void Update(Grid grid, IEntity entity) {
        ImpactScatter impactScatter = (ImpactScatter) entity.Get(EntityEnum.ImpactScatter);
        Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);

        float xVelocity = impactScatter.Transfer(velocity.GetYVelocity());

        if (velocity.GetXVelocity() < 0) {
            xVelocity *= -1;
        } else if (velocity.GetXVelocity() == 0) {
            if (ThreadLocalRandom.current().nextFloat() < 0.5) {
                xVelocity *= -1;
            }
        }
        velocity.UpdateVelocity(xVelocity, velocity.GetYVelocity());
    }
}
