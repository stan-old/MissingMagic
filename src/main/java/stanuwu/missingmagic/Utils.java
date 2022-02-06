package stanuwu.missingmagic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class Utils {
    public static void QMsg(PlayerEntity p, String msg) {
        p.sendMessage(ITextComponent.getTextComponentOrEmpty(msg), p.getUniqueID());
    }
}
