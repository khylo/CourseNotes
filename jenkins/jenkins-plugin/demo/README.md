# demo

## Introduction
Watching 
https://www.youtube.com/watch?v=azyv183Ua6U&ab_channel=Jenkins
Created using https://www.jenkins.io/doc/developer/tutorial/create/
'''
mvn hpi:create   # this is the original.. I think its superceded by this now
# To create (Note, my mvn.bat script doens't work with -D switch)
mvn -U archetype:generate -Dfilter="io.jenkins.archetypes:"
4
9
demo
# to verify
mvn verify


# to run 
mvn hpi:run   # build jenkins with this plugin

'''

## Getting started

We have added a new RootAction.. See it when running mvn hpi:run

@DataBoundContructor.. Allows .jelly / .groovy files to pass information from browser to java
Annoation guides marshalling

Added SleepBuilder step to jenkins for builds.. 
See (Create a job)

## Issues

TODO Decide where you're going to host your issues, the default is Jenkins JIRA, but you can also enable GitHub issues,
If you use GitHub issues there's no need for this section; else add the following line:

Report issues and enhancements in the [Jenkins issue tracker](https://issues.jenkins-ci.org/).

## Contributing

TODO review the default [CONTRIBUTING](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) file and make sure it is appropriate for your plugin, if not then add your own one adapted from the base file

Refer to our [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

