package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class DiagonalMovement implements IEntityComponent {
    private int _previousX;
    private int _previousY;
    private final float _strength;

    public DiagonalMovement(float strength) {
        _previousX = -1;
        _previousY = -1;
        _strength = strength;
    }

    public boolean CanMove(int x, int y) {
        return x != _previousX || y != _previousY;
    }

    public void SetPosition(int x, int y) {
        _previousX = x;
        _previousY = y;
    }

    public float GetStrength() {
        return _strength;
    }

    public IEntityComponent Copy() {
        return new DiagonalMovement(_strength);
    }
}
