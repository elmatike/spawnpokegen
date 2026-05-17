package com.spawnpokegen;

/**
 * Define el rango de números de Pokédex Nacional para cada generación.
 *
 * Los límites son inclusivos: [min, max].
 *
 *  Gen 1  (#1   – #151)  Kanto
 *  Gen 2  (#152 – #251)  Johto
 *  Gen 3  (#252 – #386)  Hoenn
 *  Gen 4  (#387 – #493)  Sinnoh
 *  Gen 5  (#494 – #649)  Unova
 *  Gen 6  (#650 – #721)  Kalos
 *  Gen 7  (#722 – #809)  Alola
 *  Gen 8  (#810 – #905)  Galar
 *  Gen 9  (#906 – #1025) Paldea
 */
public enum PokemonGeneration {

    GEN_1(1,    1,    151),
    GEN_2(2,  152,   251),
    GEN_3(3,  252,   386),
    GEN_4(4,  387,   493),
    GEN_5(5,  494,   649),
    GEN_6(6,  650,   721),
    GEN_7(7,  722,   809),
    GEN_8(8,  810,   905),
    GEN_9(9,  906,  1025);

    public final int number;
    public final int minDex;
    public final int maxDex;

    PokemonGeneration(int number, int minDex, int maxDex) {
        this.number = number;
        this.minDex  = minDex;
        this.maxDex  = maxDex;
    }

    /**
     * Devuelve la instancia correspondiente al número de generación dado.
     * @param genNumber número entre 1 y 9
     * @return la generación, o null si el número es inválido
     */
    public static PokemonGeneration fromNumber(int genNumber) {
        for (PokemonGeneration g : values()) {
            if (g.number == genNumber) return g;
        }
        return null;
    }

    /**
     * Determina si un número de Pokédex pertenece a esta generación.
     */
    public boolean contains(int dexNumber) {
        return dexNumber >= minDex && dexNumber <= maxDex;
    }
}
