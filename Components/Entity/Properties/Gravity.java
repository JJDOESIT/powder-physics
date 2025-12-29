package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Gravity implements IEntityComponent {
    private final int _direction;
    private final float _strength;

    public Gravity(int direction, float strength) {
        _direction = direction;
        _strength = strength;
    }

    public int GetDirection() {
        return _direction;
    }

    public float GetStrength() {
        return _strength;
    }

    public IEntityComponent Copy() {
        return new Gravity(_direction, _strength);
    }
}
