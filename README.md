# Gradle Delombok Plugin
Gradle plugin to delombok Java source code 

## Motivation
Using Lombok often helps to write less verbose Java code. All differences to *normal* Java code are vanished during compilation due to annotation processing. However, there may be use cases that require to *delombok* the code, which means to transform the code into what gets actually compiled. One of these use cases is the generation of Javadocs. The common [`io.franzbecker.gradle-lombok` plugin](https://github.com/franzbecker/gradle-lombok) does a great job in supporting the integration of Lombok into the Gradle environment, but the provided `DelombokTask` is [rather verbose to use](https://github.com/franzbecker/gradle-lombok/blob/master/examples/delombok-gradle-groovy/build.gradle). This plugins aims to provide a simple way to *delombok* code via Gradle.

## Download
The plugin is available via the [Gradle plugin portal](https://plugins.gradle.org/plugin/de.lukaskoerfer.gradle.delombok). Simply add the following entry to your `plugins` block:

    plugins {
        id 'de.lukaskoerfer.gradle.delombok' version '0.2'
    }
    
Additionally, the plugin requires a version of Lombok in the `compileOnly` configuration (which is required when working with Lombok anyhow). 

Of course it is possible to use this plugin together with the common [`io.franzbecker.gradle-lombok` plugin](https://github.com/franzbecker/gradle-lombok):

    plugins {
        id 'io.franzbecker.gradle-lombok' version '3.0.0'
    }

Alternatively, the Lombok dependency can be registered manually:

    dependencies {
        compileOnly 'org.projectlombok:lombok:1.18.0'
        annotationProcessor 'org.projectlombok:lombok:1.18.0' // not required for this plugin
    }

## Usage
Once the plugin is applied, you can create tasks of type `Delombok` to delombok source code:

    task myDelombok(type: Delombok) {
        source = files(...)                     // specify a collection of source files
        destinationDir = file('delombok')       // defaults to "$buildDir/delombok/$taskName"
    }

If the [Gradle Java plugin](https://docs.gradle.org/current/userguide/java_plugin.html) is applied to your project, the Delombok plugin will create a `Delombok` task for each source set. The task for the main source set will be called `delombok`, the tasks for the other source sets will be a combination of `delombok` and the name of the source set, e.g. `delombokTest` for the source set `test`.

To create Javadocs from delomboked code, which is often required, simply use the output from the `delombok` task in your `javadoc` task:

    javadoc {
        source = delombok
    }

## License
The software is licensed under the [MIT license](https://github.com/lukoerfer/gradle-delombok/blob/master/LICENSE).
