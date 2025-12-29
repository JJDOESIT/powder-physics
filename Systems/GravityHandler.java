package it.jjdoes.Atomix.Systems;

import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class GravityHandler {
    public static void Update(IEntity entity, float deltaTime) {
        if (entity.Has(EntityEnum.Gravity) && entity.Has(EntityEnum.Mass) && entity.Has(EntityEnum.Velocity)) {
            // Declare top-level components
            Gravity gravity = (Gravity) entity.Get(EntityEnum.Gravity);
            Mass mass = (Mass) entity.Get(EntityEnum.Mass);
            Velocity velocity = (Velocity) entity.Get(EntityEnum.Velocity);
            Grounded grounded = (Grounded) entity.Get(EntityEnum.Grounded);

            // If the entity is grounded, return early
            if (grounded.IsGrounded()) {
                return;
            }

            // Apply gravity
            float yVelocity = (float) (gravity.GetDirection() == 1 ? Math.ceil(gravity.GetDirection() * gravity.GetStrength() * mass.GetMass() * deltaTime) : Math.floor(gravity.GetDirection() * gravity.GetStrength() * mass.GetMass() * deltaTime));
            velocity.UpdateVelocity(0, yVelocity);
        }
    }
}
