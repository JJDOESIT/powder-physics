package it.jjdoes.Atomix.Engine.InputProcessing;

import java.util.HashMap;

import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Acid;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Honey;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Lava;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Oil;
import it.jjdoes.Atomix.Components.Entity.Types.Liquid.Subtypes.Water;
import it.jjdoes.Atomix.Components.Entity.Types.NonCollidable.NonCollidable;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Dirt;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Sand;
import it.jjdoes.Atomix.Components.Entity.Types.Solid.Subtypes.Stone;
import it.jjdoes.Atomix.Types.Entity.IEntity;

public class TypeFactory {
    public static IEntity Create(String type) {
        HashMap<String, ITypeFactory> map = new HashMap<>();
        map.put("Sand", Sand::Create);
        map.put("Dirt", Dirt::Create);
        map.put("Water", Water::Create);
        map.put("Erase", NonCollidable::Create);
        map.put("Stone", Stone::Create);
        map.put("Oil", Oil::Create);
        map.put("Honey", Honey::Create);
        map.put("Acid", Acid::Create);
        map.put("Lava", Lava::Create);
        return map.get(type).Create();
    }
}
