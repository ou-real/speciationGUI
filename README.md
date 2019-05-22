# speciationGUI
A GUI in Swing that uses Wolfram Mathematica to graph speciation results. 
This project uses the speciation simulation built in Java by William Booker which was itself based on a project by Mark Woehrer.

The goal of the GUI and visualization code is not to be a comprehensive research tool but to be more of a simulation preview tool. 
It allows users to test ideas of certain effects very easily and quickly provided those ideas have been implemented into the GUI.

Currently the GUI has options for the inheritance method with meiosis vs. pseudomeiosis vs. averaging; mating behavior with sexual (assortative) vs. random;
and dominance method with complete dominance vs. incomplete dominance. \
It also has option for custom resource distributions. The way this is done isn't perfect, but in application its probably good enough for most tests people want to run.
All custom distributions must be sums of normal (Gaussian) curves. For the GUI you must simply list the mean and standard deviations of the curves and it will overlay them on each other.

# How to set it up and use it
The GUI uses Wolfram Mathematica to produce the graphs and visuals. If you haven't used Mathematica before, it is a scientific computing tool, like a more complex version of Wolfram Alpha online.
Mathematic can do many things, but we will only be using it to make plots of the data files output by the speciation simulation.

Mathematica is free for students and faculty through the OU itStore similar to Matlab.
Follow this process to download Mathematica on your computer
1. Create an account (New users only):  
   1. Go to [user.wolfram.com](user.wolfram.com) and click ```Create Account````
   2. Fill out form using a @ou.edu email, and click ```Create Wolfram ID``` 
   3. Check your email and click the link to validate your Wolfram ID 
2. Request the download and key:  
   1. Fill out [this](https://user.wolfram.com/portal/requestAK/07044bb7bb9e0707059e2a9c79705f7095b99e40) form to request an Activation Key 
   2. Click the ```Product Summary page``` link to access your license 
   3. Click ```Get Downloads``` and select ```Download``` next to your platform 
   4. Run the installer on your machine (keep note of where you install it to), and enter Activation Key at prompt 
From here, to use Mathematica normally, just use the desktop links from the installer. The process to call it from Java code is a bit more involved.

Now that Mathematica is downloaded we can call its functions from Java code. The way to do this is through a library called JLink.
JLink was downloaded with Mathematica, to use it in code we need two things: the location of the executable Wolfram kernel, and the location of the JLink.jar file.

On my system the executable is stored at \
```C:\Program Files\Wolfram Research\Mathematica\11.3\WolframKernel.exe```

Similarly, on my system the JLink.jar file is stored at \
```C:\Program Files\Wolfram Research\Mathematica\11.3\SystemFiles\Links\JLink\JLink.jar```

On your system these will be slightly different depending on where you installed Mathematica to, but they will be very similar. Just make sure you are using the addresses from your system.

You are now ready to call Jlink from Java code. 
Suppose you had a file called SampleProgram.java that used JLink, 
you would compile that program (assuming your in the directory containing SampleProgram.java) with \
```javac -classpath ".;C:\Path\to\the\JLink.jar" SampleProgram.java```.

You would execute that file using \
```java -classpath ".;C:\Path\to\the\JLink.jar" SampleProgram -linkmode launch -linkname "C:\Path\to\the\WolframKernel.exe"```.

Unfortunately we still have a few more steps until until we can run the GUI. 
The part of the GUI that produces the visualizations obviously pulls on some custom Mathematica code.
We must add this code to the Mathematica equivalent of the classpath.

Specifically, we need to take the Wolfram package file here \
```https://github.com/ou-real/speciationGUI/blob/master/graphDefinitionPackage.wl``` \
and store it in any folder that is checked by the Wolfram kernel when searching for packages. I use \
```C:\Users\Tanner\AppData\Roaming\Mathematica\Applications```
once again your system may have a different architecture, but but in principle you will have something similar.

Now you should be able to run the GUI. 
Though it is possible to run this from the command line, the command would be somewhat complicated.
Instead I advise running it from an IDE, I use Eclipse.

For Eclipse the easiest way is to make a new project, call it whatever you want, GUISim for example. \
Then add this package to the project \
```https://github.com/ou-real/speciationGUI/tree/master/newsim```\
this contains all of the source files for the simulation and the GUI.\
Next we need to add the JLink.jar to the project, there are many ways of doing this, for me I  do this
1. Right-click on the Project → Build Path → Configure Build Path. 
2. Under the ```Libraries``` tab, click ```Add Jars``` or ```Add External JARs```.
3. Navigate to the same JLink.jar file you found earlier after downloading Mathematica and click ```Import```.

Finally add a folder called ```Output``` to the top level of the project, right next to the ```Referenced Libraries``` folder and ```newsim``` package.

Now the files are ready to go and we just need to add command line arguments to the Run Configurations.\
1. At the top of Eclipse click ```Run → Run Configurations```
2. Select ```RunSimApp``` on the left then click on and enter the ```Arguments``` tab on the right.
3. In the ```Program arguments: ``` box enter this text \
```-linkmode launch -linkname "C:\Path\to\the\WolframKernel.exe"```\
on my system it looks like \
```-linkmode launch -linkname "C:\Program Files\Wolfram Research\Mathematica\11.3\WolframKernel.exe"```.
4. At the bottom click ```Apply``` then ```Close```.

Now you should be ready to run and use the GUI. You can start it up by running the RunSimApp file, and it should bring up the GUI in a few seconds.

# How to Use the GUI for devlopment and research
More coming in this section soon!!
