
Steps to reproduce:

```bash
# Put 
#  - compiler plugin (com.example.kcp:plugin:1) 
#  - gradle plugin (com.example.gp.pluginId:com.example.gp.pluginId.gradle.plugin:1) 
#  into local maven:
cd plugin
./gradlew publishToMavenLocal

# run test
cd ../test
./gradlew linuxX64Test  # success [fail if switch off id("com.example.gp.pluginId")]
./gradlew jvmTest       # fail - reason of my pain :)
```

