package net.jptrzy.small.artifacts.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.blocks.CopperAltarBlock;
import net.jptrzy.small.artifacts.blocks.CopperAltarEntity;
import net.jptrzy.small.artifacts.triggers.CopperAltarPoweredTrigger;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class TriggerRegister {
    public static List<AbstractCriterion> triggers = new ArrayList<AbstractCriterion>();

    public static final AbstractCriterion COPPER_ALTAR_POWERED_TRIGGER = add(new CopperAltarPoweredTrigger());

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
