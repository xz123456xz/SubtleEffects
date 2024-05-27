package einstein.subtle_effects;

import einstein.subtle_effects.init.ModConfigs;
import einstein.subtle_effects.platform.ForgeRegistryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(SubtleEffects.MOD_ID)
public class SubtleEffectsForge {

    public SubtleEffectsForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SubtleEffects.init();
        ForgeRegistryHelper.PARTICLE_TYPES.register(modEventBus);
        ForgeRegistryHelper.SOUND_EVENTS.register(modEventBus);
        modEventBus.addListener((RegisterParticleProvidersEvent event) ->
                ForgeRegistryHelper.PARTICLE_PROVIDERS.forEach((particle, provider) -> registerParticle(event, particle, provider))
        );
        modEventBus.addListener((FMLClientSetupEvent event) -> SubtleEffects.clientSetup());
        modEventBus.addListener((ModConfigEvent.Reloading event) -> SubtleEffects.configReloaded(event.getConfig()));
        MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
            if (event.phase.equals(TickEvent.Phase.END)) {
                Minecraft minecraft = Minecraft.getInstance();
                SubtleEffects.clientTick(minecraft, minecraft.level);
            }
        });
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModConfigs.SPEC);
    }

    @SuppressWarnings("unchecked")
    private static <T extends ParticleType<V>, V extends ParticleOptions> void registerParticle(RegisterParticleProvidersEvent event, Supplier<? extends ParticleType<?>> particle, Function<SpriteSet, ? extends ParticleProvider<?>> provider) {
        Supplier<T> t = (Supplier<T>) particle;
        Function<SpriteSet, V> v = (Function<SpriteSet, V>) provider;
        event.registerSpriteSet(t.get(), sprites -> (ParticleProvider<V>) v.apply(sprites));
    }
}