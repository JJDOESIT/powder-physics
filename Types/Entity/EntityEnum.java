package it.jjdoes.Atomix.Types.Entity;

public enum EntityEnum {
    AirResistance,
    Buoyancy,
    Freefalling,
    Gravity,
    ImpactScatter,
    Mass,
    Pressure,
    Resistance,
    Spread,
    Velocity,
    VerticalBias,
    Grounded,
    Gas,
    Liquid,
    Solid,
    NonCollidable,
    Acid,
    Smoke,
    Blood,
    Honey,
    Oil,
    Water,
    Sand,
    Stone,
    Lifespan,
    DiagonalMovement,
    Lava,
    Dirt,
    Corrosive;

    public static EntityEnum ValueOf(int index) {
        return EntityEnum.values()[index];
    }
}
