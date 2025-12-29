package it.jjdoes.Atomix.Systems;

import java.util.concurrent.ThreadLocalRandom;

import it.jjdoes.Atomix.Components.Entity.Behaviors.ImpactScatter;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class ImpactScatterHandler {
    public static void Update(IEntity entity) {
        // Declare top-level components
        ImpactScatter impactScatter = (ImpactScatter) entity.Get(EntityEnum.ImpactScatter);
        Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);

        float xVelocity = impactScatter.Transfer(velocity.GetYVelocity());

        // If the velocity is already negative
        if (velocity.GetXVelocity() < 0) {
            xVelocity *= -1;
        }
        // If the velocity is zero
        else if (velocity.GetXVelocity() == 0) {
            // 50/50 chance whether it goes positive or negative
            if (ThreadLocalRandom.current().nextFloat() < 0.5) {
                xVelocity *= -1;
            }
        }
        velocity.UpdateVelocity(xVelocity, velocity.GetYVelocity());
    }
}
