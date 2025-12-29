package it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Solid;
import it.jjdoes.Atomix.Systems.TextureHandlers.StaticSolid.StoneTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Stone implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Stone();
    }

    public static IEntity Create() {
        return new Entity(StoneTexture.GetColor())
            .Add(new Stone(), EntityEnum.Stone)
            .Add(new Solid(), EntityEnum.Solid);
    }
}
