package stanuwu.missingmagic.spellcomp.tricks;

import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.psi.api.internal.MathHelper;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class TrickFreezeSequence extends PieceTrick {
    SpellParam<Vector3> position;
    SpellParam<Vector3> target;
    SpellParam<Number> maxBlocks;

    public TrickFreezeSequence(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(position = new ParamVector(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, false, false));
        addParam(target = new ParamVector(SpellParam.GENERIC_NAME_TARGET, SpellParam.GREEN, false, false));
        addParam(maxBlocks = new ParamNumber(SpellParam.GENERIC_NAME_MAX, SpellParam.RED, false, true));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);

        Double maxBlocksVal = this.<Double>getParamEvaluation(maxBlocks);
        if (maxBlocksVal == null || maxBlocksVal <= 0) {
            throw new SpellCompilationException(SpellCompilationException.NON_POSITIVE_VALUE, x, y);
        }

        meta.addStat(EnumSpellStat.POTENCY, (int) (maxBlocksVal * 20));
        meta.addStat(EnumSpellStat.COST, (int) ((70 + (maxBlocksVal - 1) * 50)));
    }

    @Override
    public Object execute(SpellContext ctx) throws SpellRuntimeException {
        World world = ctx.focalPoint.getEntityWorld();
        if (world.getDimensionType().isUltrawarm()) {
            return null;
        }

        Vector3 positionV = this.getParamValue(ctx, position);
        Vector3 targetV = this.getParamValue(ctx, target);
        int maxBlocksV = this.getParamValue(ctx, maxBlocks).intValue();

        if (positionV == null) {
            throw new SpellRuntimeException(SpellRuntimeException.NULL_VECTOR);
        }

        Vector3 targetNorm = targetV.copy().normalize();
        for (BlockPos blockPos : MathHelper.getBlocksAlongRay(positionV.toVec3D(), positionV.copy().add(targetNorm.copy().multiply(maxBlocksV)).toVec3D(), maxBlocksV)) {
            if (!ctx.isInRadius(Vector3.fromBlockPos(blockPos))) {
                throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);
            }
            TrickFreeze.doFreeze(blockPos, ctx);
        }

        return null;
    }
}
