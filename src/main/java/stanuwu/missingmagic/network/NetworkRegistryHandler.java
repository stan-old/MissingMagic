package stanuwu.missingmagic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import stanuwu.missingmagic.MissingMagic;
import stanuwu.missingmagic.network.packets.BroadcastableParticles;

public class NetworkRegistryHandler {
    private static final String VERSION = "1";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MissingMagic.modId, "main"), () -> VERSION, VERSION::equals, VERSION::equals);
    public static void broadcastNear(Object msg) {
        HANDLER.send(PacketDistributor.NEAR.noArg(), msg);
    }

    public static void init() {
        int id = 0;
        HANDLER.messageBuilder(BroadcastableParticles.class, id++)
                .encoder(BroadcastableParticles::encode)
                .decoder(BroadcastableParticles::new)
                .consumer(BroadcastableParticles::receive).add();
    }
}
