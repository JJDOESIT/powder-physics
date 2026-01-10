package it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.Behaviors.DiagonalMovement;
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
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.SandTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Sand implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Sand();
    }

    public static IEntity Create() {
        return new Entity(SandTexture.GetColor())
            .Add(new Sand(), EntityEnum.Sand)
            .Add(new Solid(), EntityEnum.Solid)
            .Add(new ImpactScatter(0.5f), EntityEnum.ImpactScatter)
            .Add(new Gravity(1, 1), EntityEnum.Gravity)
            .Add(new Grounded(), EntityEnum.Grounded)
            .Add(new VerticalBias(), EntityEnum.VerticalBias)
            .Add(new Resistance(0.9f), EntityEnum.Resistance)
            .Add(new AirResistance(0.8f), EntityEnum.AirResistance)
            .Add(new Freefalling(10), EntityEnum.Freefalling)
            .Add(new Mass(1f), EntityEnum.Mass)
            .Add(new DiagonalMovement(0.95f), EntityEnum.DiagonalMovement)
            .Add(new Velocity(), EntityEnum.Velocity);
    }
}
