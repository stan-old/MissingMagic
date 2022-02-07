package stanuwu.missingmagic.spellcomp.tricks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
            doFreeze(blockpos, world);
        }
        return null;
    }

    public static void doFreeze(BlockPos pos, World world) {
        BlockState state= world.getBlockState(pos);
        if((state.getMaterial() == Material.AIR || state.getMaterial() == Material.SNOW) && world.getBiome(pos).getTemperature() < 2) {
            BlockState below = world.getBlockState(pos.add(0, -1, 0));
            if(world.isRaining()) {
                world.setBlockState(pos, Blocks.ICE.getDefaultState());
            } else if(below.isSolid()) {
                if (state.getMaterial() == Material.SNOW) {
                    if (state.get(SnowBlock.LAYERS) < 8) world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, state.get(SnowBlock.LAYERS) + 1));
                } else {
                    world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, 1));
                }
            }
        } else if (state.getMaterial() == Material.WATER) {
            world.setBlockState(pos, Blocks.ICE.getDefaultState());

        } else if (state.getMaterial() == Material.LAVA) {
            if (state.getFluidState().isSource()) {
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            } else {
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
            }
        }
    }
}
