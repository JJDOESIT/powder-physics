package it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.Behaviors.Freefalling;
import it.jjdoes.Atomix.Components.Entity.Behaviors.ImpactScatter;
import it.jjdoes.Atomix.Components.Entity.Behaviors.VerticalBias;
import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Resistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Solid;
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.DirtTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Dirt implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Dirt();
    }

    public static IEntity Create() {
        return new Entity(DirtTexture.GetColor())
            .Add(new Dirt(), EntityEnum.Dirt)
            .Add(new Solid(), EntityEnum.Solid)
            .Add(new ImpactScatter(0.3f), EntityEnum.ImpactScatter)
            .Add(new Gravity(1, 1), EntityEnum.Gravity)
            .Add(new Grounded(), EntityEnum.Grounded)
            .Add(new VerticalBias(), EntityEnum.VerticalBias)
            .Add(new Resistance(0.9f), EntityEnum.Resistance)
            .Add(new AirResistance(0.9f), EntityEnum.AirResistance)
            .Add(new Freefalling(1), EntityEnum.Freefalling)
            .Add(new Mass(1f), EntityEnum.Mass)
            .Add(new Velocity(), EntityEnum.Velocity);
    }
}
