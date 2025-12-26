package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Velocity implements IEntityComponent {
    private float _x;
    private float _y;

    public Velocity() {
        _x = 0;
        _y = 0;
    }

    public float GetXVelocity() {
        return _x;
    }

    public float GetYVelocity() {
        return _y;
    }

    public void SetVelocity(float x, float y) {
        _x = x;
        _y = y;
    }

    public void UpdateVelocity(float x, float y) {
        _x += x;
        _y += y;
    }

    public IEntityComponent Copy() {
        return new Velocity();
    }
}
