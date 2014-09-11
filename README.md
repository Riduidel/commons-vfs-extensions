Commons VFS extensions 
======================

[![Build Status](https://buildhive.cloudbees.com/job/Riduidel/job/commons-vfs-extensions/badge/icon)](https://buildhive.cloudbees.com/job/Riduidel/job/commons-vfs-extensions/)

A set of commons-vfs extensions allowing better behaviour of that excellent library by relying upon some third-party libraries.

Currently

* commons-vfs-webdav-sardine
* commons-vfs-truezip-zip

Notice these artifacts are available, according to POM, under the LGPL license and in a repository near you : jsut add one artifact to your POM and it should work. As an example, commons-vfs-webdav-sardine can be added to your project simply by adding that dependency

    <dependency>
	  	<groupId>fr.perigee</groupId>
  	  <artifactId>commons-vfs-webdav-sardine</artifactId>
  	  <version>0.0.2</version>
    </dependency>

Only version currently available are 0.0.1 and 0.0.2. I suggest you use the 0.0.2 only.
