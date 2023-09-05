- Compiler-plugin Extension: https://github.com/vdshb/kcp-reproducer/blob/main/plugin/compiler-plugin/src/main/kotlin/com/example/kcp/AllowTestReturnExtension.kt
- Test: https://github.com/vdshb/kcp-reproducer/blob/main/test/src/commonTest/kotlin/com/example/kcptest/KmpTestPromiseTest.kt

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

