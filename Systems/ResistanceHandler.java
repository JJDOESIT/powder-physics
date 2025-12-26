package it.jjdoes.Atomix.Systems;

import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Resistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class ResistanceHandler {
    public static void Update(IEntity entity) {
        // Check if the entity has velocity
        if (entity.Has(EntityEnum.Velocity)) {
            Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);

            // If the entity has regular resistance
            if (entity.Has(EntityEnum.Resistance)) {
                Resistance resistance = (Resistance) entity.Get(EntityEnum.Resistance);
                float xVelocity = resistance.Update(velocity.GetXVelocity());
                velocity.SetVelocity(xVelocity, velocity.GetYVelocity());
            }

            // If the entity has air resistance
            if (entity.Has(EntityEnum.AirResistance)) {
                // Determine if the entity is grounded
                Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);
                if (!grounded.IsGrounded()) {
                    AirResistance airResistance = (AirResistance) entity.Get(EntityEnum.AirResistance);
                    float xVelocity = airResistance.Update(velocity.GetXVelocity());
                    velocity.SetVelocity(xVelocity, velocity.GetYVelocity());
                }
            }
        }
    }
}
