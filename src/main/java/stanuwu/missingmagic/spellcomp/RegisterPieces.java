package stanuwu.missingmagic.spellcomp;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stanuwu.missingmagic.MissingMagic;
import stanuwu.missingmagic.spellcomp.tricks.FreezeTrick;
import stanuwu.missingmagic.spellcomp.tricks.TestTrick;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterPieces {


    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        register("trick_test", TestTrick.class, false);
        register("trick_freeze", FreezeTrick.class, false);
    }
    public static void register(String id, Class<? extends SpellPiece> piece, boolean main) {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(MissingMagic.modId, id), piece);
        PsiAPI.addPieceToGroup(piece, new ResourceLocation(MissingMagic.modId, id), main);
    }
}
