package it.jjdoes.Atomix.Components.World;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import it.jjdoes.Atomix.Components.Entity.Types.NonCollidable.NonCollidable;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class Grid implements IWorldComponent {
    private final ArrayList<IEntity> grid;

    private final int _width;
    private final int _height;

    public Grid(int width, int height) {
        _width = width;
        _height = height;
        grid = new ArrayList<IEntity>();

        for (int index = 0; index < width * height; index++) {
            IEntity component = new Entity(Color.WHITE);
            component.Add(new NonCollidable(), EntityEnum.NonCollidable);
            grid.add(component);
        }
    }

    public int GetIndex(int x, int y) {
        return _height * x + y;
    }

    public IEntity GetEntity(int x, int y) {
        return grid.get(_height * x + y);
    }

    public void Replace(IEntity entity, int x, int y) {
        grid.set(GetIndex(x, y), entity);
    }

    public void Swap(int x, int y, int dx, int dy) {
        IEntity temp = GetEntity(x, y);
        grid.set(GetIndex(x, y), GetEntity(dx, dy));
        grid.set(GetIndex(dx, dy), temp);
    }

    public int GetWidth() {
        return _width;
    }

    public int GetHeight() {
        return _height;
    }
}
