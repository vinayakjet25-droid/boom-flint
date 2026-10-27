BOOM FLINT (Fabric, Minecraft 26.3)
- Right-click ANY block with flint and steel -> it explodes.
- Recipe: 2 iron ingots + 2 flint (shapeless) -> flint and steel.

BUILD
1. Install JDK 25.
2. Easiest: clone https://github.com/FabricMC/fabric-example-mod, copy in the
   gradle wrapper (gradlew, gradlew.bat, gradle/) into this folder.
   Gradle 9.7.1 is required.
3. Run:  ./gradlew build
4. Jar is in build/libs/boomflint-1.0.0.jar -> put in .minecraft/mods
   together with Fabric API (26.3) and Fabric Loader >= 0.19.3.

Test with: ./gradlew runClient
