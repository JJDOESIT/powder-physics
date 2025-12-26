package it.jjdoes.Atomix.Components.Entity.Properties;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Lifespan implements IEntityComponent {
    private int _life;

    public Lifespan(int life) {
        _life = life;
    }

    public int GetLife() {
        return _life;
    }

    public void DecreaseLife() {
        _life -= 1;
    }

    public IEntityComponent Copy() {
        return new Lifespan(_life);
    }
}
