package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Resistance implements IEntityComponent {
    private final float _xResistance;

    public Resistance(float xResistance) {
        _xResistance = xResistance;
    }

    public float Update(float xVelocity) {
        return xVelocity * _xResistance;
    }

    public IEntityComponent Copy() {
        return new Resistance(_xResistance);
    }
}
