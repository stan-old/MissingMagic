package stanuwu.missingmagic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import stanuwu.missingmagic.network.NetworkRegistryHandler;
import stanuwu.missingmagic.network.packets.BroadcastableParticles;

public class Utils {
    public static void QMsg(PlayerEntity p, String msg) {
        p.sendMessage(ITextComponent.getTextComponentOrEmpty(msg), p.getUniqueID());
    }

    public static void BroadcastParticles(World world, BroadcastableParticles p) {
        NetworkRegistryHandler.HANDLER.send(PacketDistributor.DIMENSION.with(() -> world.getDimensionKey()), p);
    }
}
