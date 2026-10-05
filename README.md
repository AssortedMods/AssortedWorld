# Assorted World

Contains an assorted group of additions based around world generation. Each group is also its own mod if you only
want some of them.

- [Assorted World](mods/world) has all of them in one download
- [Assorted Structures](mods/structures) adds ruins, spires, pyramids, fountains, snowballs, water domes and runes
- [Assorted Portals](mods/portals) adds void crystal and void portal frames
- [Assorted Plants](mods/plants) adds gunpowder reed and glowstone seeds
- [Assorted Floating Islands](mods/floatingislands) adds floating islands
- [Assorted Terrain](mods/terrain) adds randomite ore, desert wells and more to the landscape

Worlds made with Assorted World 9.x work with any of these.

Requires [Assorted Lib](https://github.com/AssortedMods/AssortedLib). Branches are per Minecraft version and `26.2` is the current one.

## Issue Reporting

Please include the following

* Minecraft version
* NeoForge version, or Fabric Loader and Fabric API versions
* Which of these mods you have and their versions
* Assorted Lib version
* The full `latest.log`, and the crash report if the game crashed

## Building

You need JDK 25. Each mod is its own folder under `mods`. The build setup comes from
[AssortedBuild](https://github.com/AssortedMods/AssortedBuild) and `assortedbuild_version` in `gradle.properties`
picks the version.

To build against a local copy of Assorted Lib, publish it first.

```bash
cd ../AssortedLib && ./gradlew publishToMavenLocal
```

Some useful commands

```bash
./gradlew build                                  # build every mod
./gradlew :structures:neoforge:runClient         # run one mod
./gradlew :all:neoforge:runClient                # run every mod together
./gradlew runGameTestServer runGameTest          # gametests on NeoForge and Fabric
./gradlew runClientData runServerData            # datagen
```

Generated resources are committed. The NeoForge datagen writes them for both loaders.

## License

[GPL-3.0-only](LICENSE).
