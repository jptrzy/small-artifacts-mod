package net.jptrzy.small.artifacts.triggers;

import com.google.gson.JsonObject;
import net.jptrzy.small.artifacts.Main;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PiglinAngerTrigger extends AbstractCriterion<PiglinAngerTrigger.Conditions> {

    public static final Identifier ID = Main.id("piglin_anger");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(ID, playerPredicate);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionConditions {

        public Conditions(Identifier id, EntityPredicate.Extended playerPredicate) {
            super(id, playerPredicate);
        }

        @Override
        public Identifier getId() {
            return ID;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            return new JsonObject();
        }
    }
}