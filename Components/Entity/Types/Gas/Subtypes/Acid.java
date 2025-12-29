package it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes;

import it.jjdoes.Atomix.Components.Entity.Behaviors.NonstaticGas;
import it.jjdoes.Atomix.Components.Entity.Behaviors.VerticalBias;
import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Lifespan;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Pressure;
import it.jjdoes.Atomix.Components.Entity.Properties.Spread;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Gas;
import it.jjdoes.Atomix.Systems.TextureHandlers.Gas.AcidTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Acid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Acid();
    }

    public static IEntity Create() {
        return new Entity(AcidTexture.GetColor())
            .Add(new Acid(), EntityEnum.Acid)
            .Add(new Gas(), EntityEnum.Gas)
            .Add(new NonstaticGas(), EntityEnum.Gas)
            .Add(new Gravity(-1, 1f), EntityEnum.Gravity)
            .Add(new Grounded(), EntityEnum.Grounded)
            .Add(new Mass(1f), EntityEnum.Mass)
            .Add(new VerticalBias(), EntityEnum.VerticalBias)
            .Add(new Pressure(0.5f), EntityEnum.Pressure)
            .Add(new Spread(200, 5, 20), EntityEnum.Spread)
            .Add(new AirResistance(0.5f), EntityEnum.AirResistance)
            .Add(new Buoyancy(2), EntityEnum.Buoyancy)
            .Add(new Velocity(), EntityEnum.Velocity)
            .Add(new Lifespan(20), EntityEnum.Lifespan);
    }
}
