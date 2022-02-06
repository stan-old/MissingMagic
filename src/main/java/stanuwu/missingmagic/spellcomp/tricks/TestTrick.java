package stanuwu.missingmagic.spellcomp.tricks;

import stanuwu.missingmagic.Utils;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceTrick;

public class TestTrick extends PieceTrick {
    SpellParam<Number> input1;

    public TestTrick(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(input1 = new ParamNumber(SpellParam.GENERIC_NAME_NUMBER, SpellParam.GREEN, false, false));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 10);
        meta.addStat(EnumSpellStat.COST, (int) 100);
    }

    @Override
    public Object execute(SpellContext ctx) throws SpellRuntimeException {
        Utils.QMsg(ctx.caster, "Test: "+getNonnullParamValue(ctx, input1).doubleValue());
        return null;
    }
}
