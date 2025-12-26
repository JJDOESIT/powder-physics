package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class ImpactScatter implements IEntityComponent {
    private final float _percentage;

    public ImpactScatter(float percentage) {
        _percentage = percentage;
    }

    public float Transfer(float yVelocity) {
        return yVelocity * _percentage;
    }

    public IEntityComponent Copy() {
        return new ImpactScatter(_percentage);
    }
}
