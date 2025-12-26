package it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Acid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Acid();
    }
}
