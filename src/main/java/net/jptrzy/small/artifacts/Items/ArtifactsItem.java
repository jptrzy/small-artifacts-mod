package net.jptrzy.small.artifacts.Items;

import com.mojang.datafixers.types.templates.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;

public class ArtifactsItem extends Item {

    public ArtifactsItem(Settings settings) {
        super(settings.group(ItemGroup.MISC).rarity(Rarity.UNCOMMON).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, java.util.List<Text> tooltip, TooltipContext context){
        tooltip.add( new TranslatableText("item."+Registry.ITEM.getId(this).toString().replace(":",".") + ".description" ) );
    }
}
