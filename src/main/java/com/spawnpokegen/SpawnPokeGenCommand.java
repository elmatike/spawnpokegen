package com.spawnpokegen;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * Registra el comando /spawnpokegen en el servidor.
 *
 * Uso:
 *   /spawnpokegen <1-9>   – Solo spawnea Pokémon de esa generación
 *   /spawnpokegen 0       – Sin restricción, todas las generaciones
 *   /spawnpokegen status  – Muestra la generación activa
 */
public class SpawnPokeGenCommand {

    private static final String[] GEN_NAMES = {
        "",              // índice 0: no usado
        "Gen 1 - Kanto",
        "Gen 2 - Johto",
        "Gen 3 - Hoenn",
        "Gen 4 - Sinnoh",
        "Gen 5 - Unova",
        "Gen 6 - Kalos",
        "Gen 7 - Alola",
        "Gen 8 - Galar",
        "Gen 9 - Paldea"
    };

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(
                literal("spawnpokegen")
                    // Permiso de operador (nivel 2) para cambiar la generación
                    .requires(src -> src.hasPermissionLevel(2))

                    // /spawnpokegen status
                    .then(literal("status")
                        .executes(SpawnPokeGenCommand::executeStatus)
                    )

                    // /spawnpokegen <0-9>
                    .then(argument("generacion", IntegerArgumentType.integer(0, 9))
                        .executes(SpawnPokeGenCommand::executeSetGen)
                    )

                    // /spawnpokegen sin argumentos → muestra ayuda
                    .executes(SpawnPokeGenCommand::executeHelp)
            );
        });
    }

    // ------------------------------------------------------------------
    // Handlers
    // ------------------------------------------------------------------

    /** Muestra la generación actualmente activa. */
    private static int executeStatus(CommandContext<ServerCommandSource> ctx) {
        int gen = SpawnPokeGenConfig.get().activeGeneration;
        String msg = gen == 0
            ? "§aSpawnPokeGen: §fSin restricción §7(todas las generaciones)"
            : "§aSpawnPokeGen: §fSolo " + GEN_NAMES[gen] + " §7(#" + getDexRange(gen) + ")";

        ctx.getSource().sendFeedback(() -> Text.literal(msg), false);
        return 1;
    }

    /** Cambia la generación activa y guarda el cambio en disco. */
    private static int executeSetGen(CommandContext<ServerCommandSource> ctx) {
        int gen = IntegerArgumentType.getInteger(ctx, "generacion");

        SpawnPokeGenConfig config = SpawnPokeGenConfig.get();
        config.activeGeneration = gen;
        config.save();

        String msg;
        if (gen == 0) {
            msg = "§aSpawnPokeGen: §fRestricción desactivada. Todas las generaciones pueden spawnear.";
        } else {
            PokemonGeneration pg = PokemonGeneration.fromNumber(gen);
            msg = "§aSpawnPokeGen: §fAhora solo spawnean Pokémon de §e" + GEN_NAMES[gen]
                + "§f (#" + pg.minDex + " – #" + pg.maxDex + ").";
        }

        ctx.getSource().sendFeedback(() -> Text.literal(msg), true); // broadcast a ops
        SpawnPokeGenMod.LOGGER.info("[SpawnPokeGen] Generación activa cambiada a: {}", gen);
        return 1;
    }

    /** Muestra ayuda básica. */
    private static int executeHelp(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.literal(
            "§6SpawnPokeGen §7v1.0\n" +
            "§f/spawnpokegen §e<0-9>  §7– Cambiar generación (0 = todas)\n" +
            "§f/spawnpokegen §estatus  §7– Ver generación activa"
        ), false);
        return 1;
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private static String getDexRange(int gen) {
        PokemonGeneration pg = PokemonGeneration.fromNumber(gen);
        return pg != null ? pg.minDex + " – " + pg.maxDex : "?";
    }
}
