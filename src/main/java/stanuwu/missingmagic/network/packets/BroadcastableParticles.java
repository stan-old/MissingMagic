package stanuwu.missingmagic.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import stanuwu.missingmagic.MissingMagic;

import java.util.function.Supplier;

public class BroadcastableParticles {
    private final BlockPos pos;
    private final Vector3d speed;
    private final BasicParticleType type;
    private final ResourceLocation dimension;

    public BroadcastableParticles(BasicParticleType t, BlockPos p, Vector3d s, ResourceLocation d) {
        this.type = t;
        this.pos = p;
        this.speed = s;
        this.dimension = d;
    }

    public BroadcastableParticles(BasicParticleType t, BlockPos p, ResourceLocation d) {
        this.type = t;
        this.pos = p;
        this.speed = Vector3d.ZERO;
        this.dimension = d;
    }

    public BroadcastableParticles(PacketBuffer pb) {
        this.type = (BasicParticleType) Registry.PARTICLE_TYPE.getOrDefault(pb.readResourceLocation());
        this.pos = pb.readBlockPos();
        this.speed = new Vector3d(pb.readDouble(), pb.readDouble(), pb.readDouble());
        this.dimension = pb.readResourceLocation();
    }

    public void encode(PacketBuffer pb) {
        pb.writeResourceLocation(type.getRegistryName());
        pb.writeBlockPos(this.pos);
        pb.writeDouble(this.speed.x);
        pb.writeDouble(this.speed.y);
        pb.writeDouble(this.speed.z);
        pb.writeResourceLocation(this.dimension);
    }

    public static boolean receive(BroadcastableParticles p, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                World world = Minecraft.getInstance().world;
                world.addParticle(p.type, p.pos.getX(), p.pos.getY(), p.pos.getZ(), p.speed.getX(), p.speed.getY(), p.speed.getZ());
            } catch (Exception e) {
                World world = MissingMagic.server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, p.dimension));
                world.addParticle(p.type, p.pos.getX(), p.pos.getY(), p.pos.getZ(), p.speed.getX(), p.speed.getY(), p.speed.getZ());
            }
        });
        return true;
    }
}
