package stanuwu.missingmagic.spellcomp;

import com.google.common.base.CaseFormat;
import stanuwu.missingmagic.MissingMagic;
import vazkii.psi.api.spell.SpellCompilationException;
import vazkii.psi.api.spell.SpellRuntimeException;

public enum Errors {
    test, range;
    public static final String path = MissingMagic.modId + ".error.";
    public final String name;

    Errors() {
        name = path + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name());
    }
    Errors(String name) {
        this.name = path + name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void compile(String name) throws SpellCompilationException {
        throw new SpellCompilationException(name);
    }

    public static void compile(String name, int x, int y) throws SpellCompilationException {
        throw new SpellCompilationException(name, x, y);
    }

    public void compile() throws SpellCompilationException {
        throw new SpellCompilationException(name);
    }

    public void compile(int x, int y) throws SpellCompilationException {
        throw new SpellCompilationException(name, x, y);
    }

    public static void runtime(String name) throws SpellRuntimeException {
        throw new SpellRuntimeException(name);
    }

    public void runtime() throws SpellRuntimeException {
        throw new SpellRuntimeException(name);
    }
}
