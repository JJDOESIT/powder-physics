package it.jjdoes.Atomix.Types.World;

import java.util.HashMap;

import it.jjdoes.Atomix.Components.World.IWorldComponent;

public class World implements IWorld {
    private final HashMap<Class<?>, IWorldComponent> _components;

    public World() {
        _components = new HashMap<Class<?>, IWorldComponent>();
    }

    public World Add(IWorldComponent component) {
        _components.put(component.getClass(), component);
        return this;
    }

    public boolean Has(Class<?> component) {
        return this._components.containsKey(component);
    }

    public IWorldComponent Get(Class<?> component) {
        return this._components.get(component);
    }
}
