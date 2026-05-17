package com.spawnpokegen;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import kotlin.Unit;

/**
 * Registra el listener de Cobblemon que evalúa cada intento de spawn.
 *
 * Cobblemon expone el evento POKEMON_ENTITY_SPAWN
 * que se lanza justo antes de que el motor confirme qué detalle de spawn usar.
 * Si el Pokémon candidato no pertenece a la generación activa, invalidamos el spawn.
 */
public class SpawnFilter {

    /**
     * Registra todos los listeners necesarios.
     * Se llama una sola vez desde el entrypoint principal.
     */
    public static void register() {
        // Evento que se dispara cuando Cobblemon está a punto de spawnear un Pokémon.
        // Retornar Unit es lo que Kotlin espera.
        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(com.cobblemon.mod.common.api.Priority.NORMAL, event -> {
            handleSpawnEvent(event);
            return Unit.INSTANCE;
        });
    }

    /**
     * Lógica central del filtro.
     *
     * Obtiene el número de Pokédex del Pokémon que va a spawnear y lo compara
     * con la generación configurada. Si no coincide, cancela el spawn.
     */
    private static void handleSpawnEvent(SpawnEvent<PokemonEntity> event) {
        int activeGen = SpawnPokeGenConfig.get().activeGeneration;

        // 0 = sin restricción → dejar pasar todo
        if (activeGen == 0) return;

        PokemonGeneration allowed = PokemonGeneration.fromNumber(activeGen);
        if (allowed == null) return; // config inválida, dejar pasar

        // Obtenemos el número de dex del Pokémon candidato
        int dexNumber = getDexNumber(event);
        if (dexNumber <= 0) return; // no se pudo determinar, dejamos pasar

        // Si el Pokémon NO pertenece a la generación permitida → cancelar
        if (!allowed.contains(dexNumber)) {
            event.cancel();
        }
    }

    /**
     * Extrae el número de Pokédex del evento de spawn.
     * El evento expone el PokemonEntity ya construido; de ahí sacamos la especie.
     */
    private static int getDexNumber(SpawnEvent<PokemonEntity> event) {
        try {
            // El Pokémon ya está instanciado en el evento
            var species = event.getEntity().getPokemon().getSpecies();
            return species.getNationalPokedexNumber();
        } catch (Exception e) {
            SpawnPokeGenMod.LOGGER.warn("[SpawnPokeGen] No se pudo obtener el número de dex: {}", e.getMessage());
            return -1;
        }
    }
}
