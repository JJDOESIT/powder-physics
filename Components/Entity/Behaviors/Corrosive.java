package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Corrosive implements IEntityComponent {
    private final float _solidPercentage;
    private final float _liquidPercentage;
    private IEntity _particle;

    public Corrosive(float solidPercentage, float liquidPercentage, IEntity particle) {
        _solidPercentage = solidPercentage;
        _liquidPercentage = liquidPercentage;
        _particle = particle;
    }

    public float GetSolidPercentage() {
        return _solidPercentage;
    }

    public float GetLiquidPercentage() {
        return _liquidPercentage;
    }

    public IEntity GetParticle() {
        return _particle;
    }

    public IEntityComponent Copy() {
        return new Corrosive(_solidPercentage, _liquidPercentage, _particle);
    }
}
