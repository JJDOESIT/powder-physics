package it.jjdoes.Atomix.Components.Entity.Types.Gas;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Gas implements IEntityComponent {
    public IEntityComponent Copy() {
        return new Gas();
    }
}
