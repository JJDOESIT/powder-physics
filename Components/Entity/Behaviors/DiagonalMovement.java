package it.jjdoes.Atomix.Components.Entity.Behaviors;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class DiagonalMovement implements IEntityComponent {
    private int _previousX;
    private int _previousY;

    public DiagonalMovement() {
        _previousX = -1;
        _previousY = -1;
    }

    public boolean CanMove(int x, int y) {
        return x != _previousX || y != _previousY;
    }

    public void SetPosition(int x, int y) {
        _previousX = x;
        _previousY = y;
    }

    public IEntityComponent Copy() {
        return new DiagonalMovement();
    }
}
