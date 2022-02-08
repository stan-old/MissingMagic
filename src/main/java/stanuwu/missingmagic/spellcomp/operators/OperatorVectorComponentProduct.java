package stanuwu.missingmagic.spellcomp.operators;

import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceOperator;

public class OperatorVectorComponentProduct extends PieceOperator {

    SpellParam<Vector3> vec1;

    public OperatorVectorComponentProduct(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(vec1 = new ParamVector(SpellParam.GENERIC_NAME_TARGET, SpellParam.BLUE, false, false));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 v1 = this.getParamValue(context, vec1);

        return v1.x * v1.y * v1.z;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }

}