# viewmodel-changer ported to later versions (THIS IS A FORK)
My fork of a Fabric mod that gives you the ability to change your viewmodel. This fork ports the mod to more modern versions of the game than the original mod supports.

## Table of Contents
* [Versions supported](#versions-supported)
* [Downloading the mod](#downloading-the-mod)
* [Usage](#usage-copied-from-original-readme)
* [Support](#support)
    * [Bugs and feature requests](#bugs-and-feature-requests)
    * [Contributing](#contributing)
    * [Security issues](#security-issues)
    * [Other](#other)
* [Developer info](#for-developers)
    * [Compiling (building) the mod](#compiling-building-the-mod)
    * [Using the mod as a dependency](#using-the-mod-as-a-dependency)

---

### Versions supported
The mod is currently available for the following versions of the game (all of these are clickable and point to the latest release for the game version):
* [`1.16.5`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.0) 
* [`1.17.1`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.1)
* [`1.18.2`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.2)
* [`1.19.2`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.3)
* [`1.19.3`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.4)
* [`1.19.4`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.5)
* [`1.20`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.6) **‼️ VERSION MAY NOT WORK - UNTESTED AND CONTAINS BREAKING CHANGES**
* [`1.20.1`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.9.2)
* [`1.21`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.10.1)
* [`1.21.1`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.10.2)

### Downloading the mod
To download the mod, go to the [Releases page](https://github.com/CyberFlameGO/viewmodel-changer/releases) and download the `viewmodel-*.jar` (where `*` is a version number) file under the Assets section, for the version of the game you want to use the mod on (ideally the latest release for your game version). You can also download the mod from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/viewmodel-changer) or [Modrinth](https://modrinth.com/mod/viewmodel) if you prefer that - the builds are manually kept up to date at the time of writing.


### Usage
To use this mod, open the Viewmodel Settings menu - the default keybind is BACKSLASH ("\"), but this is configurable as of v1.10.3.
Inside the settings menu, you'll see switches and sliders.
For sliders, you can use either your mouse wheel to change the values or click on a specific spot and hope to get it right.
For switches, just click on it to negate the value of that setting.

---

### Support

#### Bugs and feature requests
If you find a bug or have a feature request, please open an issue on the [Issues page](https://github.com/CyberFlameGO/viewmodel-changer/issues/new/choose)

#### Contributing
If you want to contribute to the mod, feel free to open a pull request on the [Pull requests page](https://github.com/CyberFlameGO/viewmodel-changer/compare)

#### Security issues
To report a security issue, please contact me via email at [cyberflameu@gmail.com](mailto:cyberflameu@gmail.com), draft a security advisory [here](https://github.com/CyberFlameGO/viewmodel-changer/security/advisories/new), or for minor security *concerns* please submit an issue (or pull request if you have a fix).

#### Other
If nothing here fits your needs, feel free to contact me via email at [cyberflameu@gmail.com](mailto:cyberflameu@gmail.com) or on Discord with my username `cyberflameu`.

---

### For developers

#### Compiling (building) the mod
To build the mod, you need to have Java 17 or later installed. 

<!--1. Remove the following line from the `sourcesJar` task in `build.gradle`:
    ```groovy
    dependsOn generateSentryDebugMetaPropertiesjava
    ```

2. Remove the `sentry` configuration from the `build.gradle` file.

3. -->Run the following command in the root directory of the project to build the mod:

Linux/Mac (may work on Windows too; though I'm unsure):
```shell
./gradlew build
```

Windows:
```shell
gradlew.bat build
```

#### Using the mod as a dependency
If you need to access the mod's API, the latest release is published to GitHub Packages' Maven registry. **Please note that versions prior to `1.8` use the `me.ethius` groupId, while versions `1.8` and later use the `net.cyberflame` groupId.**

_You will need to adjust the version (and for versions prior to 1.8, the groupId) to your needs. Relevant information regarding using GitHub Packages' Maven registry can be found [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#installing-a-package)._

If you're using Maven, adapt this to your `pom.xml` file:
```xml
<repositories>
  <repository>
    <id>cyberflame-repo</id>
    <url>https://maven.pkg.github.com/cyberflamego/viewmodel-changer</url>
  </repository>
</repositories>


<dependencies>
  <dependency>
    <groupId>net.cyberflame</groupId>
    <artifactId>viewmodel-changer</artifactId>
    <version><!-- DESIRED VERSION HERE --></version>
  </dependency>
</dependencies>
```
If you're using Gradle, add the following to your `build.gradle` file:

- Groovy DSL:
    ```groovy
    repositories {
        maven {
            url = "https://maven.pkg.github.com/cyberflamego/viewmodel-changer"
        }
    }
    
    dependencies {
        modImplementation "net.cyberflame:viewmodel-changer:<DESIRED VERSION HERE>"
    }
    ```
- Kotlin DSL:
    ```kotlin
    repositories {
        maven("https://maven.pkg.github.com/cyberflamego/viewmodel-changer")
    }
    
    dependencies {
        modImplementation("net.cyberflame:viewmodel-changer:<DESIRED VERSION HERE>")
    }
    ```
Or if you're using another build system, as long as it's compatible with GitHub Packages' Maven registry, you can adapt the above snippets to your needs.

---

You've reached the end of this README. Here's the (totally 💯 amazing programmer art) Viewmodel Changer logo image (created by me of course)

![viewmodel-changer art](https://user-images.githubusercontent.com/24910512/228699592-7a31fdb3-c159-4727-b6f3-7cb2bc21d78b.png)
