/**
 * GPL Templates examples
 * This class checks the configuration, sets up the template values and generate the
 * Java classes for each generated configuration
 *
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * demo.Main class for GPL example
 *
 * @author Roberto E. Lopez-Herrejon
 * @feature Prog
 */
public class CodeGenerator {
    // ***********************************************************************
    // Definition of variables to indicate that a feature is selected or not
    
    // Core features, initialized to false because they must be set nonetheless in the configuration input/file
    public static boolean GPL   = false;
    public static boolean PROG  = false;
    public static boolean BENCH = false;
    
    // Type of graph
    public static boolean UNDIRECTED = false;
    public static boolean DIRECTED   = false;
    
    // Weights
    public static boolean WEIGHTED = false;
    
    // Search features
    public static boolean DFS = false;
    public static boolean BFS = false;
    
    // Algorithms
    public static boolean NUMBER            = false;
    public static boolean CYCLE             = false;
    public static boolean CONNECTED         = false;
    public static boolean STRONGLYCONNECTED = false;
    public static boolean MSTPRIM           = false;
    public static boolean MSTKRUSKAL        = false;
    public static boolean SHORTESTPATH      = false;
    
    
    /**
     * Method that reads the configuration file, assigns values to the feature variables,
     * checks the validity of the configuration, and generate the Java classes with the templates
     *
     * @param configFilePath
     */
    public static void start(String configFilePath) {
        // Open file and reads the configurations
        LinkedList<String> features = readConfigurationFile(configFilePath);
        // Validates the configuration based on the features selected
        boolean validConfiguration = validateConfiguration(features);
        // @DEBUG
        System.out.println("Configuration consistent " + validConfiguration);
        // Generates Java classes from templates
        generateConfiguration();
    } // of start
    
    /**
     * Reads the configuration file
     *
     * @param configFilePath
     * @return
     */
    public static LinkedList<String> readConfigurationFile(String configFilePath) {
        //@DEBUG
        System.out.println("Config file " + configFilePath);
        // Creates a file for reading it
        File       file       = new File(configFilePath);
        FileReader fileReader = null;
        // List of features that will be read from the configuration file
        LinkedList<String> featuresList = new LinkedList<String>();
        try {
            fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String         line   = "";
            // Keeps reading while there are lines in the file
            while ((line = reader.readLine()) != null) {
                // @DEBUG
                System.out.println("Feature " + line);
                // Add the feature name read from the file
                featuresList.add(line);
            } //for all the lines in the
            // Closes the buffered reader
            reader.close();
            // Returns the list with the features read
            return featuresList;
        } catch (Exception e) {
            System.out.println("Error while reading file " + file.getName());
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    System.out.println("Error while closing file " + file.getName());
                }
            }
        } // of finally
        // An error happened in the execution of the reading
        return null;
    } // of readConfiguration
    
    
    /**
     * Programatic verification of configuration consistency. Does the selected features are a
     * valid configuration?
     *
     * @param featuresSelected*
     */
    private static boolean validateConfiguration(LinkedList<String> featuresSelected) {
        boolean consistent = true;
        // *******
        // For all the features selected in the list, check that the names are correct and set the corresponding boolean variables
        for (String feature : featuresSelected) {
            if (feature.equals("PROG")) {
                PROG = true;
                continue;
            }
            if (feature.equals("GPL")) {
                GPL = true;
                continue;
            }
            if (feature.equals("BENCH")) {
                BENCH = true;
                continue;
            }
            if (feature.equals("UNDIRECTED")) {
                UNDIRECTED = true;
                continue;
            }
            if (feature.equals("DIRECTED")) {
                DIRECTED = true;
                continue;
            }
            if (feature.equals("WEIGHTED")) {
                WEIGHTED = true;
                continue;
            }
            if (feature.equals("DFS")) {
                DFS = true;
                continue;
            }
            if (feature.equals("BFS")) {
                BFS = true;
                continue;
            }
            if (feature.equals("NUMBER")) {
                NUMBER = true;
                continue;
            }
            if (feature.equals("CYCLE")) {
                CYCLE = true;
                continue;
            }
            if (feature.equals("CONNECTED")) {
                CONNECTED = true;
                continue;
            }
            if (feature.equals("STRONGLYCONNECTED")) {
                STRONGLYCONNECTED = true;
                continue;
            }
            if (feature.equals("MSTPRIM")) {
                MSTPRIM = true;
                continue;
            }
            if (feature.equals("MSTKRUSKAL")) {
                MSTKRUSKAL = true;
                continue;
            }
            if (feature.equals("SHORTESTPATH")) {
                SHORTESTPATH = true;
                continue;
            }
            // If it reaches here is because it did not find a feature with the given name
            System.out.println("Error: Feature " + feature + " not found");
            System.exit(0);
        } // of for all the features selected
        // Displays the configuration of the features
        displayConfigurationValues();
        // *********
        // Start the verification of the conditions
        // Only one of DIRECTED and UNDIRECTED can be selected
        if (DIRECTED == UNDIRECTED)
            consistent = false;
        // Only one search method of DFS and BFS can be selected at most
        // true, when both unselected (search is optional), and one of two selected
        // false, if both are selected
        if (DFS && BFS)
            consistent = false;
        // Specific constraints for algorithms
        //If NUMBER is selected then one search method must be selected
        if (NUMBER && !(DFS || BFS))
            consistent = false;
        //If CONNECTED is selected then a search is required on undirected graphs
        if (CONNECTED && (!UNDIRECTED || !(DFS || BFS)))
            consistent = false;
        //If STRONGLYCONNECTED is selected then DFS must be selected on directed graphs
        if (STRONGLYCONNECTED && (!DIRECTED || !DFS))
            consistent = false;
        // If CYCLE is selected it requires DFS search
        if (CYCLE && !DFS)
            consistent = false;
        // MSTKRUSKAL and MSTPRIM cannot be selected at the same time
        // true, when both unselected (MST are not mandatory), and one of two selected
        // false, if both are selected
        if (MSTPRIM && MSTKRUSKAL)
            consistent = false;
        // If MSTRPRIM selected must have Undirected and Weighted graphs
        if (MSTPRIM && (!UNDIRECTED || !WEIGHTED))
            consistent = false;
        // If MSTKRUSKAL selected must have Undirected and Weighted graphs
        if (MSTKRUSKAL && (!UNDIRECTED || !WEIGHTED))
            consistent = false;
        // If SHORTESTPATH selected must have DIRECTED and Weighted graphs
        if (SHORTESTPATH && (!DIRECTED || !WEIGHTED))
            consistent = false;
        return consistent;
    } // of validConfiguration
    
    
    /**
     * Shows the names of the selected features
     */
    private static void displayConfigurationValues() {
        StringBuffer configuration = new StringBuffer();
        if (GPL)
            configuration.append("GPL ");
        else
            configuration.append("!GPL ");
        if (PROG)
            configuration.append("PROG ");
        else
            configuration.append("!PROG ");
        if (BENCH)
            configuration.append("BENCH ");
        else
            configuration.append("!BENCH ");
        if (UNDIRECTED)
            configuration.append("UNDIRECTED ");
        else
            configuration.append("!UNDIRECTED ");
        if (DIRECTED)
            configuration.append("DIRECTED ");
        else
            configuration.append("!DIRECTED ");
        if (WEIGHTED)
            configuration.append("WEIGHTED ");
        else
            configuration.append("!WEIGHTED ");
        if (DFS)
            configuration.append("DFS ");
        else
            configuration.append("!DFS ");
        if (BFS)
            configuration.append("BFS ");
        else
            configuration.append("!BFS ");
        if (NUMBER)
            configuration.append("NUMBER ");
        else
            configuration.append("!NUMBER ");
        if (CYCLE)
            configuration.append("CYCLE ");
        else
            configuration.append("!CYCLE ");
        if (CONNECTED)
            configuration.append("CONNECTED ");
        else
            configuration.append("!CONNECTED ");
        if (STRONGLYCONNECTED)
            configuration.append("STRONGLYCONNECTED ");
        else
            configuration.append("!STRONGLYCONNECTED ");
        if (MSTPRIM)
            configuration.append("MSTPRIM ");
        else
            configuration.append("!MSTPRIM ");
        if (MSTKRUSKAL)
            configuration.append("MSTKRUSKAL ");
        else
            configuration.append("!MSTKRUSKAL ");
        if (SHORTESTPATH)
            configuration.append("SHORTESTPATH ");
        else
            configuration.append("!SHORTESTPATH ");
        // @DEBUG
        System.out.println("Input configuration: " + configuration);
    } // of displayConfigurationValues
    
    
    /*
     * This method creates the Velocity context according to the configuration values of the variables
     * and instantiates all the templates contained in ./templates directory and produces the class files in ./target directory
     */
    public static void generateConfiguration() {
        // Reads the template files
        LinkedList<String> templateFilesNames = readsTemplatesFilesNames();
        // Creates the context to generate the files
        createMergeContext();
        // Generates the class files from the templates
        generateClassFiles(templateFilesNames);
    } // of generateConfiguration
    
    
    // Velocity context
    static VelocityContext context;
    
    
    /**
     * Creates a Velocity context that contains all the boolean feature variables
     */
    public static void createMergeContext() {
        // Initializes the template engine
        try {
            Properties p = new Properties();
            Velocity.init(p);
        } catch (Exception e) {
            System.out.println("Problem initializing Velocity : " + e);
            return;
        }
        // Creates the context based on the configuration selected
        context = new VelocityContext();
        context.put("GPL", CodeGenerator.GPL);
        context.put("PROG", CodeGenerator.PROG);
        context.put("BENCH", CodeGenerator.BENCH);
        context.put("UNDIRECTED", CodeGenerator.UNDIRECTED);
        context.put("DIRECTED", CodeGenerator.DIRECTED);
        context.put("WEIGHTED", CodeGenerator.WEIGHTED);
        context.put("DFS", CodeGenerator.DFS);
        context.put("BFS", CodeGenerator.BFS);
        context.put("NUMBER", CodeGenerator.NUMBER);
        context.put("CYCLE", CodeGenerator.CYCLE);
        context.put("CONNECTED", CodeGenerator.CONNECTED);
        context.put("STRONGLYCONNECTED", CodeGenerator.STRONGLYCONNECTED);
        context.put("MSTPRIM", CodeGenerator.MSTPRIM);
        context.put("MSTKRUSKAL", CodeGenerator.MSTKRUSKAL);
        context.put("SHORTESTPATH", CodeGenerator.SHORTESTPATH);
        // TODO check if it works
    }  // of createMergeContext
    
    
    /**
     * Generates the file for a class
     *
     * @param templateFilesNames
     */
    public static void generateClassFiles(LinkedList<String> templateFilesNames) {
        // Writer for Java file
        FileWriter outWriter;
        // 	For all the templates
        for (String templateFileName : templateFilesNames) {
            // TODO to be completed ...
            // Creates the output file
            Pattern pattern = Pattern.compile("(.*)\\\\(.*)\\.java\\.vm");
            Matcher matcher = pattern.matcher(templateFileName);
            if (matcher.find()) {
                
                String fileName = "./src/main/java/%s.java".formatted(matcher.group(2));
                try {
                    outWriter = new FileWriter(fileName);
                } catch (IOException e) {
                    System.out.println("Error while creating output file");
                    e.printStackTrace();
                    return;
                }
                // Merges the context with the template
                try {
                    Template template = Velocity.getTemplate(templateFileName);
                    template.merge(context, outWriter);
                    outWriter.flush();
                    outWriter.close();
                } catch (Exception e) {
                    System.out.println("Error while creating output file");
                    e.printStackTrace();
                    return;
                }
            }
        } // of all the templates
    } // of generateClass
    
    // Default template directory
    private static final String templateDir  = "./src/main/templates/";
    private static final String connectedWS  = "ConnectedWorkSpace.java.vm";
    private static final String cycleWS      = "CycleWorkSpace.java.vm";
    private static final String finishTimeWS = "FinishTimeWorkSpace.java.vm";
    private static final String numberWS     = "NumberWorkSpace.java.vm";
    private static final String transposeWS  = "TransposeWorkSpace.java.vm";
    private static final String WS           = "WorkSpace.java.vm";
    private static final String PrimDep      = "MSTPrimQueueComparator.java.vm";
    
    /**
     * This method reads the names of the files in the default template directory and eliminates from
     * the list the files that are not instantiated based on he configuration selected
     *
     * @return
     */
    public static LinkedList<String> readsTemplatesFilesNames() {
        LinkedList<String> fileNames = new LinkedList<String>();
        File               file      = new File(templateDir);
        // Checks if the default template directory is actually a directory
        if (file.isDirectory()) {
            for (File templateFile : file.listFiles()) {
                String templateFilePath = templateFile.getPath();
                // Checks for templates that are needed only when certain features are selected
                // if CONNECTED not selected exclude file demo.ConnectedWorkSpace.java.vm
                if ((templateFilePath.indexOf(connectedWS) != -1) && !CodeGenerator.CONNECTED)
                    continue;
                // if CYCLE not selected exclude file CycledWorkSpace.vm
                if ((templateFilePath.indexOf(cycleWS) != -1) && !CodeGenerator.CYCLE)
                    continue;
                // if STRONGLYCONNECTED not selected exclude file demo.FinishTimeWorkSpace.java.vm
                if ((templateFilePath.indexOf(finishTimeWS) != -1) && !CodeGenerator.STRONGLYCONNECTED)
                    continue;
                // if NUMBER not selected exclude file demo.NumberWorkSpace.java.vm
                if ((templateFilePath.indexOf(numberWS) != -1) && !CodeGenerator.NUMBER)
                    continue;
                // if STRONGLYCONNECTED not selected exclude file demo.TransposeWorkSpace.java.vm
                if ((templateFilePath.indexOf(transposeWS) != -1) && !CodeGenerator.STRONGLYCONNECTED)
                    continue;
                // demo.WorkSpace is selected whenever BFS or DFS are selected. Thus if neither is selected and the demo.WorkSpace.java.vm is found, then skip
                if ((templateFilePath.indexOf(WS) != -1) && !CodeGenerator.DFS && !CodeGenerator.BFS)
                    continue;
                if ((templateFilePath.indexOf(PrimDep) != -1) && !CodeGenerator.MSTPRIM)
                    continue;
                // Adds the name of the template file
                fileNames.add(templateFilePath);
            } // of all the files in the templates directory
        } // of checking the directory
        // returns the names of the template files that are going to be instantiated
        return fileNames;
    } // of readTemplatesFilesNames
    
    
    /**
     * Method main receives as command argument the path to the configuration file
     * Example: ./configs/prod1.conf
     *
     * @param args
     */
    public static void main(String[] args) {
        start(args[0]);
    }
} // class demo.Main
