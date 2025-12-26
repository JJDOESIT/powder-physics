package it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Stone implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Stone();
    }
}
