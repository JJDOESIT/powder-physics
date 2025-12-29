package it.jjdoes.Atomix.Types.Entity;

public enum EntityEnum {
    Acid,
    AirResistance,
    Buoyancy,
    Corrosive,
    DiagonalMovement,
    Dirt,
    Freefalling,
    Gas,
    Gravity,
    Grounded,
    Honey,
    ImpactScatter,
    Lava,
    Lifespan,
    Liquid,
    Mass,
    NonCollidable,
    Oil,
    Pressure,
    Resistance,
    Sand,
    Smoke,
    Solid,
    Spread,
    Stone,
    Velocity,
    VerticalBias,
    Water;

    public static EntityEnum ValueOf(int index) {
        return EntityEnum.values()[index];
    }
}
