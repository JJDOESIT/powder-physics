package it.jjdoes.Atomix.Types.Entity;

import com.badlogic.gdx.graphics.Color;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Entity implements IEntity {
    private final IEntityComponent[] _components;
    private boolean _updated;
    private final Color _color;

    public Entity(Color color) {
        _components = new IEntityComponent[100];
        _color = color;
    }

    public Entity Add(IEntityComponent component, EntityEnum id) {
        _components[id.ordinal()] = component;
        return this;
    }

    public boolean Has(EntityEnum id) {
        return _components[id.ordinal()] != null;
    }

    public IEntityComponent Get(EntityEnum id) {
        return _components[id.ordinal()];
    }

    public void EnableUpdated() {
        _updated = true;
    }

    public void DisableUpdated() {
        _updated = false;
    }

    public boolean IsUpdated() {

        return _updated;
    }

    public Color GetColor() {
        return _color;
    }

    public IEntity Clone() {
        IEntity clone = new Entity(GetColor());
        for (int index = 0; index < _components.length; index++) {
            if (_components[index] != null) {
                clone.Add(_components[index].Copy(), EntityEnum.ValueOf(index));
            }
        }
        return clone;
    }
}
