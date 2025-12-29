package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;
import it.jjdoes.Atomix.Engine.InputProcessing.ITypeFactory;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Corrosive implements IEntityComponent {
    private final float _solidPercentage;
    private final float _liquidPercentage;
    private final ITypeFactory _typeFactory;

    public Corrosive(float solidPercentage, float liquidPercentage, ITypeFactory typeFactory) {
        _solidPercentage = solidPercentage;
        _liquidPercentage = liquidPercentage;
        _typeFactory = typeFactory;
    }

    public float GetSolidPercentage() {
        return _solidPercentage;
    }

    public float GetLiquidPercentage() {
        return _liquidPercentage;
    }

    public IEntity GetParticle() {
        return _typeFactory.Create();
    }

    public IEntityComponent Copy() {
        return new Corrosive(_solidPercentage, _liquidPercentage, _typeFactory);
    }
}
