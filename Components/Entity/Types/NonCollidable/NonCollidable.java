package it.jjdoes.Atomix.Components.Entity.Types.NonCollidable;

import com.badlogic.gdx.graphics.Color;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class NonCollidable implements IEntityComponent {
    public IEntityComponent Copy() {
        return new NonCollidable();
    }

    public static IEntity Create() {
        return new Entity(Color.WHITE)
            .Add(new NonCollidable(), EntityEnum.NonCollidable);
    }
}
