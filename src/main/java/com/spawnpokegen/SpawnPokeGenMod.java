package com.spawnpokegen;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entrypoint principal de SpawnPokeGen.
 *
 * Este mod tiene un único propósito:
 *   Añadir el comando /spawnpokegen <generacion> que filtra
 *   los spawns de Pokémon de Cobblemon a una sola generación.
 *
 * ¿Qué pasa cuando se ejecuta el comando?
 *   1. Se guarda la generación elegida en config/spawnpokegen.json
 *   2. El filtro de spawn (SpawnFilter) lee ese valor en cada intento de spawn
 *   3. Si el número de Pokédex del candidato cae fuera del rango permitido,
 *      el spawn se cancela antes de que el Pokémon aparezca en el mundo.
 */
public class SpawnPokeGenMod implements ModInitializer {

    public static final String MOD_ID = "spawnpokegen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[SpawnPokeGen] Iniciando mod...");

        // 1. Cargar configuración persistida
        SpawnPokeGenConfig config = SpawnPokeGenConfig.get();
        LOGGER.info("[SpawnPokeGen] Generación activa al iniciar: {}",
            config.activeGeneration == 0 ? "Sin restricción" : "Gen " + config.activeGeneration);

        // 2. Registrar el filtro de spawns
        SpawnFilter.register();

        // 3. Registrar el comando /spawnpokegen
        SpawnPokeGenCommand.register();

        LOGGER.info("[SpawnPokeGen] Listo. Usa /spawnpokegen <1-9> para elegir generación.");
    }
}
