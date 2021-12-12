package net.jptrzy.small.artifacts.registry;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.jptrzy.small.artifacts.triggers.CopperAltarPoweredTrigger;
import net.jptrzy.small.artifacts.triggers.PiglinAngerTrigger;
import net.minecraft.advancement.criterion.AbstractCriterion;

import java.util.*;

public class TriggerRegister {
    public static List<AbstractCriterion> triggers = new ArrayList<AbstractCriterion>();

    public static final AbstractCriterion COPPER_ALTAR_POWERED_TRIGGER = add(new CopperAltarPoweredTrigger());
    public static final AbstractCriterion PIGLIN_ANGER_TRIGGER = add(new PiglinAngerTrigger());

    public static void init() {
        ListIterator<AbstractCriterion> i = triggers.listIterator();
        while (i.hasNext()) {
            CriterionRegistry.register(i.next());
        }
    }

    public static AbstractCriterion add(AbstractCriterion trigger){
        triggers.add(trigger);
        return trigger;
    }
}
