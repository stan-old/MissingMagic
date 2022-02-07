package stanuwu.missingmagic.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BroadcastableParticles {
    private final BlockPos pos;
    private final Vector3d speed;
    private final BasicParticleType type;

    public BroadcastableParticles(BasicParticleType t, BlockPos p, Vector3d s) {
        this.type = t;
        this.pos = p;
        this.speed = s;
    }

    public BroadcastableParticles(BasicParticleType t, BlockPos p) {
        this.type = t;
        this.pos = p;
        this.speed = Vector3d.ZERO;
    }

    public BroadcastableParticles(PacketBuffer pb) {
        this.type = (BasicParticleType) Registry.PARTICLE_TYPE.getOrDefault(pb.readResourceLocation());
        this.pos = pb.readBlockPos();
        this.speed = new Vector3d(pb.readDouble(), pb.readDouble(), pb.readDouble());
    }

    public void encode(PacketBuffer pb) {
        pb.writeResourceLocation(type.getRegistryName());
        pb.writeBlockPos(this.pos);
        pb.writeDouble(this.speed.x);
        pb.writeDouble(this.speed.y);
        pb.writeDouble(this.speed.z);
    }

    public static boolean receive(BroadcastableParticles p, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = Minecraft.getInstance().world;
            if (world.isRemote) {
                world.addParticle(p.type, p.pos.getX(), p.pos.getY(), p.pos.getZ(), p.speed.getX(), p.speed.getY(), p.speed.getZ());
            }
        });
        return true;
    }
}
