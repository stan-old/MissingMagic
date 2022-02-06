package stanuwu.missingmagic.spellcomp;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stanuwu.missingmagic.MissingMagic;
import stanuwu.missingmagic.spellcomp.tricks.TestTrick;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterPieces {

    public enum PType {
        test,
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        register("trick_test", TestTrick.class, PType.test.toString(), false);
    }
    public static void register(String id, Class<? extends SpellPiece> piece, String group, boolean main) {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(MissingMagic.modId, id), piece);
        PsiAPI.addPieceToGroup(piece, new ResourceLocation(MissingMagic.modId, group), main);
    }
}
