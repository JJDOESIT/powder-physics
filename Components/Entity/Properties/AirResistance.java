package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class AirResistance implements IEntityComponent {
    private final float _strength;

    public AirResistance(float strength) {
        _strength = strength;
    }

    public float Update(float velocity) {
        return velocity * _strength;
    }

    public IEntityComponent Copy() {
        return new AirResistance(_strength);
    }
}
