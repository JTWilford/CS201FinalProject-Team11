# CS201 Final Project: New CSCI201 Website
###Team 11
#### Members
- Yash Bhartia – ybhartia@usc.edu
- Addison Herr – aherr@usc.edu
- Yang Li – yli546@usc.edu
- Justin Wilford – jwilford@usc.edu
- Tianyi Yu – tianyiyu@usc.edu
## Set-up
### Eclipse
1. Clone the repository into your Eclipse Workspace folder
```
cd [Eclipse Workspace Directory]
git clone https://github.com/JTWilford/CS201FinalProject-Team11.git
```
2. In Eclipse, click "File">"Open Projects from File System..."
    - In the Import Wizard, Click on "Directory..." next to the import source
    - Click on the folder you just cloned (Should be named "CS201FinalProject-Team11")
    - Click "Finish" at the bottom of the window
3. Right-click the project in the Project Explorer (The left pane), then click "Properties"
    - On the left, click "Project Facets"
    - Click "Convert to faceted form..."
    - Make sure "Java", "JavaScript", and "Dynamic Web Module" are checked
    - At the bottom, click "Further configuration available..."
      - In the popup window, change the Context Root to "/" and change the Content Directory to "/web"
      - Click "Ok" at the bottom of the window
    - Click "Apply and Close" at the bottom of the window
4. Right-click the project again and click "Properties" (This must be done after you closed the first properties window)
    - On the left, click "Server"
    - Click "Tomcat v9.0 Server at localhost"
      - If this option isn't there, please follow the instructions in [Lab 1.1 Part 2](http://www-scf.usc.edu/~csci201/labs/Lab1.pdf)
    - Make sure "Tomcat v9.0 Server at localhost" is highlighted, then click "Apply"
    - On the left, click "Java Build Path"
    - Open the "Libraries" tab
    - Click "Add Library..." on the right
      - Click "Server Runtime", then click "Next"
      - Click "Apache Tomcat v9.0", then click "Finish"
    - Click "Apply and Close" at the bottom of the window
    
**Your Eclipse Environment should be set up now!**
