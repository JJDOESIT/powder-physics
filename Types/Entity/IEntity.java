package it.jjdoes.Atomix.Types.Entity;

import com.badlogic.gdx.graphics.Color;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public interface IEntity {

    Entity Add(IEntityComponent component, EntityEnum id);

    boolean Has(EntityEnum id);

    IEntityComponent Get(EntityEnum id);

    void EnableUpdated();

    void DisableUpdated();

    boolean IsUpdated();

    Color GetColor();

    IEntity Clone();
}
