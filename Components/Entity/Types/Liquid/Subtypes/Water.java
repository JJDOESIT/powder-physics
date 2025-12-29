package it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.Behaviors.VerticalBias;
import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Pressure;
import it.jjdoes.Atomix.Components.Entity.Properties.Resistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Spread;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Liquid;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.WaterTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Water implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Water();
    }

    public static IEntity Create() {
        return new Entity(WaterTexture.GetColor())
            .Add(new Water(), EntityEnum.Water)
            .Add(new Liquid(), EntityEnum.Liquid)
            .Add(new Gravity(1, 1), EntityEnum.Gravity)
            .Add(new Grounded(), EntityEnum.Grounded)
            .Add(new Resistance(1f), EntityEnum.Resistance)
            .Add(new Mass(1f), EntityEnum.Mass)
            .Add(new Pressure(1f), EntityEnum.Pressure)
            .Add(new Spread(0, 20, 50), EntityEnum.Spread)
            .Add(new VerticalBias(), EntityEnum.VerticalBias)
            .Add(new AirResistance(0.5f), EntityEnum.AirResistance)
            .Add(new Velocity(), EntityEnum.Velocity)
            .Add(new Buoyancy(4), EntityEnum.Buoyancy);
    }
}
