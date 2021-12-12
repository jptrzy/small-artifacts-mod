package net.jptrzy.small.artifacts.items;

import net.jptrzy.small.artifacts.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

public class IndexOfBabelsLibrary extends ArtifactsItem{
    public IndexOfBabelsLibrary(Settings settings) {
        super(settings.rarity(Rarity.RARE));
    }

    private final Identifier BOOK_ID = Main.id("the-library-of-babel");

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Book book = BookRegistry.INSTANCE.books.get(BOOK_ID);

        if (!world.isClient()) {
            PatchouliAPI.get().openBookGUI((ServerPlayerEntity) player, book.id);
            player.playSound(PatchouliSounds.getSound(book.openSound, PatchouliSounds.BOOK_OPEN), 1, (float) (0.7 + Math.random() * 0.4));
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }
//
//    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
//        return ActionResult.PASS;
//    }
}
