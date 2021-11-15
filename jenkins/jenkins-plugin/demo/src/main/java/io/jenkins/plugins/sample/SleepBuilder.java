package io.jenkins.plugins.sample;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.FreeStyleProject;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import java.io.IOException;
import jenkins.tasks.SimpleBuildStep;



/**
 * SleepBuilder
 */
public class SleepBuilder  extends Builder implements SimpleBuildStep {
    private long time;
    
    @DataBoundConstructor
    public SleepBuilder(long time){
        this.time=time;
    }
    /*public SleepBuilder(String time){
        this.time=Long.parseLong(time);
    }*/
    
    public long getTime() {
        return time;
    }
    
    public void setTime(long time) {
        this.time=time;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Sleeping for "+time+" ms.");
        Thread.sleep(time);
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        Class aclass;
        @Override
        public boolean 	isApplicable(Class<? extends AbstractProject> jobType){
            //return aclass == FreeStyleProject.class;
            return true;
        }
    
        @Override 
        public String getDisplayName(){
            return "Sleep builder";
        }
        
    }
}