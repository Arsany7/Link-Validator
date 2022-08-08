/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsandvalidation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.lang3.time.StopWatch;
import static threadsandvalidation.Validation.setNumberOfThreads;

/**
 *
 * @author ALEX STORE
 */
public class Driver {
    public static int numbOfThreads = 1;
    public static ArrayList <Long> executionTimeArray;

    public static ArrayList<Long> getExecutionTimeArray() {
        return executionTimeArray;
    }
    
    
    public static void main(String[] args) throws IOException {
     UserInput FF= new UserInput();  
    }
    public void Check(Links L,int fdepth) throws MalformedURLException{
        
        long timeInMelliSecond;
        long previousTime = 1000000000;
        executionTimeArray=new ArrayList<>();
        while (true) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            setNumberOfThreads(numbOfThreads++);
            Validation.executor = Executors.newFixedThreadPool(Validation.getNumberOfThreads());
            L.validation(Validation.getNumberOfThreads(),0, fdepth);
            System.out.println("Number of links = " + L.getNumberOfLinks());
               
            while (((ThreadPoolExecutor) Validation.executor).getActiveCount() != 0) 
            {
                
            }
            Validation.executor.shutdown();
            stopWatch.stop();
            timeInMelliSecond = stopWatch.getTime();
            executionTimeArray.add(timeInMelliSecond);
            if (timeInMelliSecond > previousTime || previousTime - timeInMelliSecond < 1000) {
                System.out.println("Number of Threads: " + (numbOfThreads-1));
                System.out.println("Optimum Time in melliseconds: " + previousTime + "  ms (as at current number of threads time = " + timeInMelliSecond + " )");
                break;
            } else {
                previousTime = timeInMelliSecond;
            }
            System.out.println("Number of Threads: " + (numbOfThreads-1));
            System.out.println("Time in melliseconds: " + timeInMelliSecond + "  ms");
            Links.setNumberOfValidLinks(0);
            Links.setNumberOfInvalidLinks(0);
        }
        ValidationOutput OF =new ValidationOutput(Links.getNumberOfValidLinks()-1,Links.getNumberOfInvalidLinks(),Validation.getNumberOfThreads()-1,previousTime);
        OF.setVisible(true);
        System.out.println("Number Of Valid Links: " + (Links.getNumberOfValidLinks()-1));
        System.out.println("Number Of Invalid Links: " + Links.getNumberOfInvalidLinks());
   
}

}