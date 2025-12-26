package it.jjdoes.Atomix.Components.Entity.Types.Liquid;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Liquid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Liquid();
    }
}
