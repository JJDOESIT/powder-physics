package it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes;

import com.badlogic.gdx.graphics.Color;

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
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Oil implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Oil();
    }

    public static IEntity Create() {
        return new Entity(Color.BLACK)
            .Add(new Oil(), EntityEnum.Oil)
            .Add(new Liquid(), EntityEnum.Liquid)
            .Add(new Gravity(1, 1), EntityEnum.Gravity)
            .Add(new Grounded(), EntityEnum.Grounded)
            .Add(new Resistance(1f), EntityEnum.Resistance)
            .Add(new Mass(1f), EntityEnum.Mass)
            .Add(new Pressure(1f), EntityEnum.Pressure)
            .Add(new Spread(20, 5, 10), EntityEnum.Spread)
            .Add(new VerticalBias(), EntityEnum.VerticalBias)
            .Add(new AirResistance(0.7f), EntityEnum.AirResistance)
            .Add(new Buoyancy(2), EntityEnum.Buoyancy)
            .Add(new Velocity(), EntityEnum.Velocity);
    }
}
