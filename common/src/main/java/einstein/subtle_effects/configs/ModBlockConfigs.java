package einstein.subtle_effects.configs;

import einstein.subtle_effects.SubtleEffects;
import einstein.subtle_effects.configs.blocks.SparksConfigs;
import einstein.subtle_effects.configs.blocks.SteamConfigs;
import einstein.subtle_effects.configs.blocks.UpdatedSmokeConfigs;
import einstein.subtle_effects.init.ModConfigs;
import einstein.subtle_effects.tickers.TickerManager;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedRegistryType;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static einstein.subtle_effects.init.ModConfigs.BASE_KEY;

@Translation(prefix = ModConfigs.BASE_KEY + "blocks")
public class ModBlockConfigs extends Config {

    private static final List<Block> DEFAULT_FALLING_BLOCK_DUST_BLOCKS = Util.make(new ArrayList<>(), blocks -> {
        blocks.add(Blocks.SAND);
        blocks.add(Blocks.RED_SAND);
        blocks.add(Blocks.GRAVEL);

        for (DyeColor color : DyeColor.values()) {
            blocks.add(BuiltInRegistries.BLOCK.get(new ResourceLocation(color.getName() + "_concrete_powder")));
        }
    });

    public SparksConfigs sparks = new SparksConfigs();
    public UpdatedSmokeConfigs updatedSmoke = new UpdatedSmokeConfigs();
    public SteamConfigs steam = new SteamConfigs();

    public boolean redstoneBlockDust = true;
    public BlockDustDensity redstoneBlockDustDensity = BlockDustDensity.DEFAULT;
    public GlowstoneDustDisplayType glowstoneBlockDustDisplayType = GlowstoneDustDisplayType.ON;
    public BlockDustDensity glowstoneBlockDustDensity = BlockDustDensity.DEFAULT;
    public boolean beehivesHaveSleepingZs = true;
    public boolean fallingBlockDust = true;
    public ValidatedList<Block> fallingBlockDustBlocks = new ValidatedList<>(DEFAULT_FALLING_BLOCK_DUST_BLOCKS, ValidatedRegistryType.of(BuiltInRegistries.BLOCK));
    public SmokeType torchflowerSmoke = SmokeType.DEFAULT;
    public boolean torchflowerFlames = true;
    public boolean dragonEggParticles = true;
    public boolean replaceEndPortalSmoke = true;
    public boolean pumpkinCarvedParticles = true;
    public boolean anvilBreakParticles = true;
    public boolean anvilUseParticles = true;
    public boolean grindstoneUseParticles = true;
    public CommandBlockSpawnType commandBlockParticles = CommandBlockSpawnType.ON;
    public boolean slimeBlockBounceSounds = true;
    public boolean beaconParticles = true;
    public boolean compostingParticles = true;
    public boolean respawnAnchorParticles = true;
    public boolean beehiveShearParticles = true;
    public boolean endPortalParticles = true;
    public boolean leavesDecayEffects = true;
    public boolean farmlandDestroyEffects = true;
    public AmethystSparkleDisplayType amethystSparkleDisplayType = AmethystSparkleDisplayType.ON;
    public boolean amethystSparkleSounds = true;
    public boolean floweringAzaleaPetals = true;
    public boolean sculkBlockSculkDust = true;
    public boolean sculkVeinSculkDust = true;
    public boolean sculkShriekerDestroySouls = true;
    public boolean sculkCatalystDestroySouls = true;
    public boolean calibratedSculkSensorAmethystSparkle = true;

    public ModBlockConfigs() {
        super(SubtleEffects.loc("blocks"));
    }

    @Override
    public void onUpdateClient() {
        TickerManager.clear();
    }

    public enum GlowstoneDustDisplayType implements EnumTranslatable {
        OFF,
        ON,
        NETHER_ONLY;

        @NotNull
        @Override
        public String prefix() {
            return BASE_KEY + "blocks.glowstoneBlockDustDisplayType";
        }
    }

    public enum BlockDustDensity implements EnumTranslatable {
        DEFAULT(0),
        MINIMAL(2);

        private final int perSideChance;

        BlockDustDensity(int perSideChance) {
            this.perSideChance = perSideChance;
        }

        @NotNull
        @Override
        public String prefix() {
            return BASE_KEY + "blocks.blockDustDensity";
        }

        public int getPerSideChance() {
            return perSideChance;
        }
    }

    public enum AmethystSparkleDisplayType implements EnumTranslatable {
        OFF,
        ON,
        CRYSTALS_ONLY;

        @NotNull
        @Override
        public String prefix() {
            return BASE_KEY + "blocks.amethystSparkleDisplayType";
        }
    }
}
