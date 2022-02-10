package stanuwu.missingmagic.spellcomp.tricks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanuwu.missingmagic.Utils;
import stanuwu.missingmagic.network.packets.BroadcastableParticles;
import stanuwu.missingmagic.spellcomp.Errors;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class TrickFreeze extends PieceTrick {
    SpellParam<Vector3> position;

    public TrickFreeze(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(position = new ParamVector(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, false, false));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 20);
        meta.addStat(EnumSpellStat.COST, 80);
    }

    @Override
    public Object execute(SpellContext ctx) throws SpellRuntimeException {
        Vector3 positionV = getParamValueOrDefault(ctx, position, null);
        World world = ctx.focalPoint.getEntityWorld();
        if (!ctx.isInRadius(positionV)) Errors.runtime(SpellRuntimeException.OUTSIDE_RADIUS);
        else {
            BlockPos blockpos = positionV.toBlockPos();
            doFreeze(blockpos, ctx);
        }
        return null;
    }

    public static void doFreeze(BlockPos pos, SpellContext ctx) {
        World world = ctx.focalPoint.getEntityWorld();
        BlockState state= world.getBlockState(pos);
        boolean frozen = false;
        if((state.getMaterial() == Material.AIR || state.getMaterial() == Material.SNOW) && world.getBiome(pos).getTemperature() < 2) {
            BlockState below = world.getBlockState(pos.add(0, -1, 0));
            if(world.isRaining()) {
                world.setBlockState(pos, Blocks.ICE.getDefaultState());
                frozen = true;
            } else if(below.isSolid()) {
                if (state.getMaterial() == Material.SNOW) {
                    if (state.get(SnowBlock.LAYERS) < 8) world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, state.get(SnowBlock.LAYERS) + 1));
                } else {
                    world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, 1));
                }
                frozen = true;
            }
        } else if (state.getMaterial() == Material.WATER) {
            world.setBlockState(pos, Blocks.ICE.getDefaultState());
            frozen = true;

        } else if (state.getMaterial() == Material.LAVA) {
            if (state.getFluidState().isSource()) {
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            } else {
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
            }
            frozen = true;
        } else if (state.getMaterial() == Material.FIRE) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            frozen = true;
        }

        if (frozen) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            for (int l = 0; l < 8; ++l) {
                Utils.BroadcastParticles(world, new BroadcastableParticles(ParticleTypes.ITEM_SNOWBALL, pos.add(.5+ Math.random(), .5+ Math.random(), .5+ Math.random()), world.getDimensionKey().getLocation()));
            }
        }
    }
}
