package it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Acid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Acid();
    }
}
