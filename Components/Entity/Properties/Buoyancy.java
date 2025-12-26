package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Buoyancy implements IEntityComponent {
    private final int _rank;

    public Buoyancy(int rank) {
        _rank = rank;
    }

    public int GetRank() {
        return _rank;
    }

    public IEntityComponent Copy() {
        return new Buoyancy(_rank);
    }
}
