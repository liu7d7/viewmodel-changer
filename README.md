# viewmodel-changer ported to later versions (THIS IS A FORK)
My fork of a Fabric mod that gives you the ability to change your viewmodel. This fork ports the mod to more modern versions of the game than the original mod supports.

## Table of Contents
* [Versions supported](#versions-supported)
* [Downloading the mod](#downloading-the-mod)
* [Usage](#usage-copied-from-original-readme)
* [Developer info](#for-developers)

---

### Versions supported
The mod is currently available for the following versions of the game (all of these are clickable and point to the latest release for the game version):
* [`1.16.5`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.0) 
* [`1.17.1`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.1)
* [`1.18.2`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.2)
* [`1.19.2`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.3)
* [`1.19.3`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.4)
* [`1.19.4`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.5)
* [`1.20`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.6)
* [`1.20.1`](https://github.com/CyberFlameGO/viewmodel-changer/releases/tag/v1.8)

### Downloading the mod
To download the mod, go to the [Releases page](https://github.com/CyberFlameGO/viewmodel-changer/releases) and download the `viewmodel-*.jar` (where `*` is a version number) file under the Assets section, for the version of the game you want to use the mod on (ideally the latest release for your game version). You can also download the mod from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/viewmodel-changer) if you prefer that.

Please create any issues you deem relevant for me to resolve in this repository, or check out other ways to contact me on my GitHub profile

---

### Usage (copied from original README)
> to open the gui, press the BACKSLASH key.  
> inside it, you'll see switches and sliders.  
> for sliders, you can use either your mouse wheel to change the values or click on a specific spot and hope to get it right.  
> for switches, just click on it to negate the value of that setting.

### For developers
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
### Viewmodel Changer logo image (created by me of course)

![viewmodel-changer art](https://user-images.githubusercontent.com/24910512/228699592-7a31fdb3-c159-4727-b6f3-7cb2bc21d78b.png)

---
