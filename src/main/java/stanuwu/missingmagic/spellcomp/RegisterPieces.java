package stanuwu.missingmagic.spellcomp;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stanuwu.missingmagic.MissingMagic;
import stanuwu.missingmagic.spellcomp.tricks.TrickFreeze;
import stanuwu.missingmagic.spellcomp.tricks.TrickFreezeSequence;
import stanuwu.missingmagic.spellcomp.tricks.TrickTest;
import stanuwu.missingmagic.spellcomp.tricks.TrickTorrentSequence;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterPieces {


    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        //register("trick_test", TrickTest.class, false);

        register("trick_freeze", TrickFreeze.class, false);
        register("trick_freeze_sequence", TrickFreezeSequence.class, false);
        register("trick_torrent_sequence", TrickTorrentSequence.class, false);
    }
    public static void register(String id, Class<? extends SpellPiece> piece, boolean main) {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(MissingMagic.modId, id), piece);
        PsiAPI.addPieceToGroup(piece, new ResourceLocation(MissingMagic.modId, id), main);
    }
}
