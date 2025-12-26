package it.jjdoes.Atomix.InputProcessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import it.jjdoes.Atomix.Components.Entity.Behaviors.Corrosive;
import it.jjdoes.Atomix.Components.Entity.Behaviors.DiagonalMovement;
import it.jjdoes.Atomix.Components.Entity.Behaviors.Freefalling;
import it.jjdoes.Atomix.Components.Entity.Behaviors.ImpactScatter;
import it.jjdoes.Atomix.Components.Entity.Behaviors.NonstaticGas;
import it.jjdoes.Atomix.Components.Entity.Behaviors.VerticalBias;
import it.jjdoes.Atomix.Components.Entity.Properties.AirResistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Buoyancy;
import it.jjdoes.Atomix.Components.Entity.Properties.Gravity;
import it.jjdoes.Atomix.Components.Entity.Properties.Lifespan;
import it.jjdoes.Atomix.Components.Entity.Properties.Mass;
import it.jjdoes.Atomix.Components.Entity.Properties.Pressure;
import it.jjdoes.Atomix.Components.Entity.Properties.Resistance;
import it.jjdoes.Atomix.Components.Entity.Properties.Spread;
import it.jjdoes.Atomix.Components.Entity.Properties.Velocity;
import it.jjdoes.Atomix.Components.Entity.States.Grounded;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Gas;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes.Acid;
import it.jjdoes.Atomix.Components.Entity.Types.Gas.Subtypes.Smoke;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Liquid;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Honey;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Lava;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Oil;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Water;
import it.jjdoes.Atomix.Components.Entity.Types.NonCollidable.NonCollidable;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Solid;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Dirt;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Sand;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Stone;
import it.jjdoes.Atomix.Components.World.Grid;
import it.jjdoes.Atomix.Main;
import it.jjdoes.Atomix.Rendering.Renderer;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.AcidTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.LavaTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.Liquid.WaterTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.DirtTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.NonstaticSolid.SandTexture;
import it.jjdoes.Atomix.Systems.TextureHandlers.StaticSolid.StoneTexture;
import it.jjdoes.Atomix.Types.Entity.Entity;
import it.jjdoes.Atomix.Types.Entity.EntityEnum;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class InputProcessor {
    public void Update(Grid grid, Renderer renderer) {
        // Declare width and height of the grid
        int height = grid.GetHeight();
        int width = grid.GetWidth();

        // Left-click detected
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            // Fetch mouse-coordinates
            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            renderer.GetViewport().unproject(mousePosition);

            int gridX = (int) mousePosition.x;
            int gridY = height - (int) mousePosition.y;

            int brushSize = Main.GetBrushSize();
            String selectedType = Main.GetSelectedType().getText().toString();

            for (int i = gridY; i < gridY + Main.GetBrushSize(); i++) {
                for (int j = gridX; j < gridX + brushSize; j++) {
                    if (i >= 0 && i < height && j >= 0 && j < width) {
                        switch (selectedType) {
                            case "Sand":
                                IEntity sand = new Entity(SandTexture.GetColor())
                                    .Add(new Sand(), EntityEnum.Sand)
                                    .Add(new Solid(), EntityEnum.Solid)
                                    .Add(new ImpactScatter(0.5f), EntityEnum.ImpactScatter)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new Resistance(0.9f), EntityEnum.Resistance)
                                    .Add(new AirResistance(0.8f), EntityEnum.AirResistance)
                                    .Add(new Freefalling(10), EntityEnum.Freefalling)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new DiagonalMovement(), EntityEnum.DiagonalMovement)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(sand, i, j);
                                break;
                            case "Dirt":
                                IEntity dirt = new Entity(DirtTexture.GetColor())
                                    .Add(new Dirt(), EntityEnum.Dirt)
                                    .Add(new Solid(), EntityEnum.Solid)
                                    .Add(new ImpactScatter(0.3f), EntityEnum.ImpactScatter)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new Resistance(0.9f), EntityEnum.Resistance)
                                    .Add(new AirResistance(0.9f), EntityEnum.AirResistance)
                                    .Add(new Freefalling(1), EntityEnum.Freefalling)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(dirt, i, j);
                                break;
                            case "Water":
                                IEntity water = new Entity(WaterTexture.GetColor())
                                    .Add(new Water(), EntityEnum.Water)
                                    .Add(new Liquid(), EntityEnum.Liquid)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Resistance(1f), EntityEnum.Resistance)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Pressure(1f), EntityEnum.Pressure)
                                    .Add(new Spread(0, 20, 50), EntityEnum.Spread)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new AirResistance(0.5f), EntityEnum.AirResistance)
                                    .Add(new Velocity(), EntityEnum.Velocity)
                                    .Add(new Buoyancy(4), EntityEnum.Buoyancy);
                                grid.Replace(water, i, j);
                                break;
                            case "Erase":
                                IEntity air = new Entity(Color.WHITE)
                                    .Add(new NonCollidable(), EntityEnum.NonCollidable);
                                grid.Replace(air, i, j);
                                break;
                            case "Stone":
                                IEntity stone = new Entity(StoneTexture.GetColor())
                                    .Add(new Stone(), EntityEnum.Stone)
                                    .Add(new Solid(), EntityEnum.Solid);
                                grid.Replace(stone, i, j);
                                break;
                            case "Oil": {
                                IEntity oil = new Entity(Color.BLACK)
                                    .Add(new Oil(), EntityEnum.Oil)
                                    .Add(new Liquid(), EntityEnum.Liquid)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Resistance(1f), EntityEnum.Resistance)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Pressure(1f), EntityEnum.Pressure)
                                    .Add(new Spread(20, 5, 10), EntityEnum.Spread)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new AirResistance(0.7f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(2), EntityEnum.Buoyancy)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(oil, i, j);
                                break;
                            }
                            case "Honey": {
                                IEntity oil = new Entity(Color.GOLD)
                                    .Add(new Honey(), EntityEnum.Honey)
                                    .Add(new Liquid(), EntityEnum.Liquid)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Resistance(1f), EntityEnum.Resistance)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Pressure(1f), EntityEnum.Pressure)
                                    .Add(new Spread(20, 1, 5), EntityEnum.Spread)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new AirResistance(0.9f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(3), EntityEnum.Buoyancy)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(oil, i, j);
                                break;
                            }
                            case "Acid":
                                IEntity acidParticle = new Entity(it.jjdoes.Atomix.Systems.TextureHandlers.Gas.AcidTexture.GetColor())
                                    .Add(new Acid(), EntityEnum.Acid)
                                    .Add(new Gas(), EntityEnum.Gas)
                                    .Add(new NonstaticGas(), EntityEnum.Gas)
                                    .Add(new Gravity(-1, 1f), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new Pressure(0.5f), EntityEnum.Pressure)
                                    .Add(new Spread(200, 5, 20), EntityEnum.Spread)
                                    .Add(new AirResistance(0.5f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(2), EntityEnum.Buoyancy)
                                    .Add(new Velocity(), EntityEnum.Velocity)
                                    .Add(new Lifespan(20), EntityEnum.Lifespan);
                                IEntity acid = new Entity(AcidTexture.GetColor())
                                    .Add(new Acid(), EntityEnum.Acid)
                                    .Add(new Liquid(), EntityEnum.Liquid)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Resistance(1f), EntityEnum.Resistance)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Pressure(1f), EntityEnum.Pressure)
                                    .Add(new Spread(200, 5, 10), EntityEnum.Spread)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new AirResistance(0.75f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(1), EntityEnum.Buoyancy)
                                    .Add(new Corrosive(0.1f, 0.5f, acidParticle), EntityEnum.Corrosive)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(acid, i, j);
                                break;
                            case "Lava":
                                IEntity smokeParticle = new Entity(Color.GRAY)
                                    .Add(new Smoke(), EntityEnum.Smoke)
                                    .Add(new Gas(), EntityEnum.Gas)
                                    .Add(new Gravity(-1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new Pressure(0.5f), EntityEnum.Pressure)
                                    .Add(new Spread(200, 5, 10), EntityEnum.Spread)
                                    .Add(new AirResistance(0.9f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(1), EntityEnum.Buoyancy)
                                    .Add(new Lifespan(20), EntityEnum.Lifespan)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                IEntity lava = new Entity(LavaTexture.GetColor())
                                    .Add(new Lava(), EntityEnum.Lava)
                                    .Add(new Liquid(), EntityEnum.Liquid)
                                    .Add(new Gravity(1, 1), EntityEnum.Gravity)
                                    .Add(new Grounded(), EntityEnum.Grounded)
                                    .Add(new Resistance(1f), EntityEnum.Resistance)
                                    .Add(new Mass(1f), EntityEnum.Mass)
                                    .Add(new Pressure(1f), EntityEnum.Pressure)
                                    .Add(new Spread(100, 4, 8), EntityEnum.Spread)
                                    .Add(new VerticalBias(), EntityEnum.VerticalBias)
                                    .Add(new AirResistance(0.75f), EntityEnum.AirResistance)
                                    .Add(new Buoyancy(1), EntityEnum.Buoyancy)
                                    .Add(new Corrosive(0.025f, 0.2f, smokeParticle), EntityEnum.Corrosive)
                                    .Add(new Velocity(), EntityEnum.Velocity);
                                grid.Replace(lava, i, j);
                                break;
                        }
                    }
                }
            }
        }
    }
}
