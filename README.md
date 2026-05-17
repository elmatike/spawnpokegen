# 🎮 SpawnPokeGen Mod (Fabric)

[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.1-blue.svg?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Cobblemon Version](https://img.shields.io/badge/Cobblemon-1.7.3-red.svg?style=for-the-badge)](https://cobblemon.com/)
[![Loader](https://img.shields.io/badge/Loader-Fabric-lightgrey.svg?style=for-the-badge)](https://fabricmc.net/)

An ultra-lightweight, high-performance Fabric mod for Minecraft 1.21.1 and Cobblemon 1.7.3 that allows you to dynamically restrict Pokémon spawns to a specific generation of your choice in real-time!

Un mod de Fabric ultra-ligero y de alto rendimiento para Minecraft 1.21.1 y Cobblemon 1.7.3 que te permite restringir de forma dinámica la aparición de Pokémon a una generación específica de tu elección en tiempo real.

---

## ✨ Features / Características

*   **⚡ High Performance:** Filters spawns during the early spawn stage (`POKEMON_ENTITY_SPAWN` event), avoiding overhead or world lag by canceling spawn before entities are loaded.
*   **🎮 In-game Commands:** Switch generations on-the-fly without restarting your game or server using command-line commands.
*   **💾 Auto-Save Config:** The chosen generation is saved automatically in `config/spawnpokegen.json` and persists across game restarts or server reloads.
*   **🔒 OP Level 2 Protected:** Commands require Operator level 2 to prevent unauthorized players from changing settings on servers.

---

## 💬 Commands / Comandos

### 🟢 Restrict to a Specific Generation / Restringir a una Generación
Set the active generation (1 to 9). Pokémon outside this generation will be blocked from spawning.
*Establece la generación activa (1 al 9). Los Pokémon fuera de este rango no aparecerán.*
```text
/spawnpokegen <1-9>
```
*   `/spawnpokegen 1` — Only **Gen 1 (Kanto)** Pokémon will spawn (#1 to #151).
*   `/spawnpokegen 9` — Only **Gen 9 (Paldea)** Pokémon will spawn (#906 to #1025).

### 🔴 Disable Restrictions / Desactivar Restricciones
Allows all Pokémon from all generations to spawn normally.
*Permite que todos los Pokémon de todas las generaciones aparezcan con normalidad.*
```text
/spawnpokegen 0
```

### 🔍 View Active Status / Ver Estado Activo
Shows the currently allowed generation and its respective national Pokédex ranges.
*Muestra la generación permitida actual y sus rangos respectivos en la Pokédex.*
```text
/spawnpokegen status
```

---

## 📊 Supported Generations / Generaciones Soportadas

| Generation | Region | National Pokédex Range |
| :--- | :--- | :--- |
| **Gen 1** | Kanto | #1 – #151 |
| **Gen 2** | Johto | #152 – #251 |
| **Gen 3** | Hoenn | #252 – #386 |
| **Gen 4** | Sinnoh | #387 – #493 |
| **Gen 5** | Unova | #494 – #649 |
| **Gen 6** | Kalos | #650 – #721 |
| **Gen 7** | Alola | #722 – #809 |
| **Gen 8** | Galar | #810 – #905 |
| **Gen 9** | Paldea | #906 – #1025 |

---

## 🛠️ Installation / Instalación

1.  Make sure you have **Fabric Loader** installed for Minecraft 1.21.1.
2.  Drop the `spawnpokegen-1.0.0.jar` into your `.minecraft/mods/` directory.
3.  Ensure you have **Cobblemon 1.7.3** (Fabric version) inside your mods folder.
4.  Launch the game, open your world, and enjoy!

---

Created with ❤️ by **elmatike**. Feel free to fork, open issues, or contribute!
*Creado con ❤️ por **elmatike**. ¡Siéntete libre de clonarlo, reportar fallos o contribuir!*
