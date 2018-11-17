# CS201 Final Project: New CSCI201 Website
### Team 11
#### Members
- Yash Bhartia – ybhartia@usc.edu
- Addison Herr – aherr@usc.edu
- Yang Li – yli546@usc.edu
- Justin Wilford – jwilford@usc.edu
- Tianyi Yu – tianyiyu@usc.edu
## Software
### Node.js
1. Download [Node.js 8.12.0 LTS](https://nodejs.org/)
2. Install Node with the default settings
3. Restart your computer
**Node.js should be installed now!**
### Angular CLI
1. Open Terminal (Unix) or Command Prompt (Windows) and execute the following command:
```
npm install -g @angular/cli
```
**Angular CLI should be installed now!**
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
      - In the popup window, change the Context Root to "/" and change the Content Directory to "web"
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
To start the backend, click the play button at the top of the window

### IntelliJ IDEA
1. Clone the repository into your IntelliJ Projects folder
```
cd [IntelliJ Projects Directory]
git clone https://github.com/JTWilford/CS201FinalProject-Team11.git
```
2. In IntelliJ, click "File">"Open..." and in the pop-up navigate to the repository you just cloned. Highlight the repository, then click "Ok"
3. Now, click "File">"Project Structure"
    - In the Project Structure window, click "Project" in the left pane
        - Select the Project SDK to be Java 1.8.
        - Select SDK Level to be 8
        - Select the output directory to be "[path to your project directory]/out/"
    - Now, click "Facets" in the left pane
        - At the top of the window, click the plus button, then click "Web"
        - In the new pop-up, highlight the project, then click "Ok"
        - At the bottom of the Project Structure window, click "Create Artifact"
        - Now, click "Apply" at the bottom of the window, then click "Ok" at the bottom of the window
    - In the Project Structure window, click "Modules" in the left pane
        - Now, click the project name, which should be underneath the plus button
            - Click on the "Sources" tab
            - Highlight the "src" folder, then click the "Sources" button with the blue folder icon
            - Highlight the "web" folder, then click the "Sources" button with the blue folder icon
            - Now, "src" and "web" should be blue folders
                - If they are not the right color, redo the step for that folder
            - Click the "Apply" button at the bottom of the Project Structure window
    - In the Project Structure window, click "Libraries" in the left pane
        - Click the plus button at the top of the window, then select "Java"
        - In the new pop-up, navigate to your Apache Tomcat's installation directory
            - For Mac users, you will have to use the "shift-cmd-G" shortcut, then enter "/usr/local/apache-tomcat-9.0.11/"
            - For Windows users, navigate to "C:\Program Files\Apache Software Foundation\Tomcat 9.0\"
            - Once you are in your Tomcat installation directory, open the "lib" folder
            - Select both "servlet-api.jar" and "websocket-api.jar" then click "Ok"
            - In the next pop-up, select the project, then click "Ok"
        - Now, click "Apply" at the bottom of the window then click "Ok" at the bottom of the window
5. Click "Run">"Edit Configurations..."
    - In the Run/Debug Configurations window, click the plus at the top left of the window
        - select "Tomcat Server">"Local"
    - Now, name the new configuration "Run Backend"
    - At the bottom of the window, it should say "Warning: No artifacts marked for deployment". Click the "Fix" button next to the message
    - Now, click "Apply" at the bottom of the window, then click "Ok" at the bottom of the window
**Your IntelliJ Environment should be set up now!**
To start the backend, click the play button at the top right of the window while "Run Backend" is selected

## Running Angular
1. In a terminal/command prompt, navigate to your project directory.
2. cd into "./angular/cs201-web/"
3. run the following commands
```
npm install
npm start
```
4. After the second command, when the terminal says "Compiled Successfully", your angular environment has been started
5. Navigate to "http://localhost:4200/" to view the website