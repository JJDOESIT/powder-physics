package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Pressure implements IEntityComponent {
    private final float _strength;

    public Pressure(float strength) {
        _strength = strength;
    }

    public float Transfer(float rank) {
        return _strength * rank + 1;
    }

    public IEntityComponent Copy() {
        return new Pressure(_strength);
    }
}
