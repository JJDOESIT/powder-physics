package it.jjdoes.Atomix.Components.Entity.Types.Solid;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Solid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Solid();
    }
}
