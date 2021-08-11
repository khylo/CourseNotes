package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class  OnlineRootAction implements RootAction{

    public String  getIconFileName() {
        return  "clipboard.png";
    }

    public String getDisplayName() {
        return "MyNew RootAction";
    }

    public String getUrlName() {
        return "http://google.com";
    }
    
}
