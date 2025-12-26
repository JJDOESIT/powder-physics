package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Freefalling implements IEntityComponent {
    private final float _strength;

    public Freefalling(float strength) {
        _strength = strength;
    }

    public float GetStrength() {
        return _strength;
    }

    public IEntityComponent Copy() {
        return new Freefalling(_strength);
    }
}
