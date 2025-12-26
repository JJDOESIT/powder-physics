package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Spread implements IEntityComponent {
    private final int _distance;
    private final float _minimum;
    private final float _maximum;

    public Spread(int distance, float minimum, float maximum) {
        _distance = distance;
        _minimum = minimum;
        _maximum = maximum;
    }

    public int GetDistance() {
        return _distance;
    }

    public float GetMinimum() {
        return _minimum;
    }

    public float GetMaximum() {
        return _maximum;
    }

    public IEntityComponent Copy() {
        return new Spread(_distance, _minimum, _maximum);
    }
}
