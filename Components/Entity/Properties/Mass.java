package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Mass implements IEntityComponent {
    private final float _mass;

    public Mass(float mass) {
        _mass = mass;
    }

    public float GetMass() {
        return _mass;
    }

    public IEntityComponent Copy() {
        return new Mass(_mass);
    }
}
