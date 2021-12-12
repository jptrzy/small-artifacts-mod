package net.jptrzy.small.artifacts.items;

import net.jptrzy.small.artifacts.Main;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArtifactsItem extends Item {

    public ArtifactsItem(Settings settings) {
        super(settings.group(Main.ITEM_GROUP).rarity(Rarity.UNCOMMON).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, java.util.List<Text> tooltip, TooltipContext context){
        //tooltip.add( new TranslatableText("item."+Registry.ITEM.getId(this).toString().replace(":",".") + ".description" ) );
    }
}
