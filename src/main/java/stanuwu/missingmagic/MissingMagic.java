package stanuwu.missingmagic;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stanuwu.missingmagic.network.NetworkRegistryHandler;

@Mod("missingmagic")
public class MissingMagic {
    public static final String modId = "missingmagic";
    public static final Logger LOGGER = LogManager.getLogger();

    public MissingMagic() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("MassingMagic Loaded");
        NetworkRegistryHandler.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("MissingMagic Loaded");
    }
}
