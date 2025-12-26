package it.jjdoes.Atomix.Types.World;

import it.jjdoes.Atomix.Components.World.IWorldComponent;

public interface IWorld {
    World Add(IWorldComponent component);

    boolean Has(Class<?> component);

    IWorldComponent Get(Class<?> component);
}
