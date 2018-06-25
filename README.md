# Gradle Delombok Plugin

## Motivation


## Download
The plugin is available via the [Gradle plugin portal](https://plugins.gradle.org/plugin/de.lukaskoerfer.gradle.delombok). Simply add the following entry to your `plugins` block:

    plugins {
        id 'de.lukaskoerfer.gradle.delombok' id '0.1'
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
