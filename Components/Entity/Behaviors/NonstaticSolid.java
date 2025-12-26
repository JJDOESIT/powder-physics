package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class NonstaticSolid implements IEntityComponent {
    public IEntityComponent Copy() {
        return new NonstaticSolid();
    }
}
