package com.spawnpokegen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Maneja la persistencia de la configuración del mod.
 * El único ajuste que guarda es la generación activa (1-9) o 0 para "todas".
 */
public class SpawnPokeGenConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("spawnpokegen.json");

    // ----- Datos guardados -----

    /** Generación activa. 0 = sin restricción, 1-9 = solo esa generación. */
    public int activeGeneration = 0;

    // ----- Singleton -----

    private static SpawnPokeGenConfig INSTANCE;

    public static SpawnPokeGenConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    // ----- Persistencia -----

    private static SpawnPokeGenConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                SpawnPokeGenConfig cfg = GSON.fromJson(reader, SpawnPokeGenConfig.class);
                if (cfg != null) return cfg;
            } catch (IOException e) {
                SpawnPokeGenMod.LOGGER.error("[SpawnPokeGen] Error leyendo config, usando defaults.", e);
            }
        }
        SpawnPokeGenConfig defaults = new SpawnPokeGenConfig();
        defaults.save();
        return defaults;
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            SpawnPokeGenMod.LOGGER.error("[SpawnPokeGen] Error guardando config.", e);
        }
    }
}
