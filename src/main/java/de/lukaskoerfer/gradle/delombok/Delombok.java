package de.lukaskoerfer.gradle.delombok;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.JavaExecSpec;

import java.io.File;
import java.nio.file.Paths;

/**
 * Delomboks Java source files
 */
public class Delombok extends SourceTask {
    
    /**
     * -- GETTER --
     * Gets the directory to save delomboked files to
     * <br><br>
     * Defaults to <code>"$buildDir/delombok/$taskName"</code>
     * @return The destination directory
     * -- SETTER --
     * Sets the directory to save delomboked files to
     * <br><br>
     * Defaults to <code>"$buildDir/delombok/$taskName"</code>
     * @param destinationDir Any directory
     */
    @Getter(onMethod = @__(@OutputDirectory)) @Setter
    private File destinationDir = Paths.get(getProject().getBuildDir().toString(), "delombok", getName()).toFile();
    
    /**
     * Runs the delombok tool on the specified source
     */
    @TaskAction
    public void run() {
        getProject().javaexec(this::configure);
    }
    
    private void configure(JavaExecSpec javaExec) {
        Configuration compileOnly = getProject()
            .getConfigurations().getAt(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);
        javaExec.setClasspath(compileOnly);
        javaExec.setMain("lombok.launch.Main");
        javaExec.args("delombok");
        javaExec.args("-d", destinationDir);
        getSource().getFiles().forEach(javaExec::args);
    }

}
