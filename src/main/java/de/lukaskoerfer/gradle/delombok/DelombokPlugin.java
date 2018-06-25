package de.lukaskoerfer.gradle.delombok;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;

/**
 * Provides tasks to delombok Java sources
 */
public class DelombokPlugin implements Plugin<Project> {
    
    public static final String DELOMBOK_GROUP_NAME = "Delombok";
    
    @Override
    public void apply(Project project) {
        // Register Delombok task type
        project.getExtensions().getExtraProperties().set(Delombok.class.getSimpleName(), Delombok.class);
        // Configure Java plugin
        project.getPluginManager().withPlugin("java", plugin -> {
            JavaPluginConvention javaPlugin = project.getConvention().getPlugin(JavaPluginConvention.class);
            javaPlugin.getSourceSets().all(sourceSet -> {
                String name = sourceSet.getTaskName("delombok", "");
                Delombok delombok = project.getTasks().create(name, Delombok.class);
                delombok.setDescription("Delomboks all Java source in source set " + sourceSet.getName());
                delombok.setGroup(DELOMBOK_GROUP_NAME);
                delombok.setSource(sourceSet.getAllJava());
            });
        });
    }
    
}
