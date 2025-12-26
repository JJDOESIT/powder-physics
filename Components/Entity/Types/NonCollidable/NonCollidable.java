package it.jjdoes.Atomix.Components.Entity.Types.NonCollidable;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class NonCollidable implements IEntityComponent {
    public IEntityComponent Copy() {
        return new NonCollidable();
    }
}
