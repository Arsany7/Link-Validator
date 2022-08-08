package threadsandvalidation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ALEX STORE
 */
public class Validation extends Thread  {

    private static int numberOfThreads = 1;
    public static ExecutorService executor;
    private Links currentlyCheckingLink;
    private int currentDepth;
    private int finalDepth;

    public Validation(Links currentlyCheckingLink, int currentDepth, int finalDepth) {
        this.currentlyCheckingLink = currentlyCheckingLink;
        this.currentDepth = currentDepth;
        this.finalDepth = finalDepth;
    }

    public Validation() {
    }

    
    public static int getNumberOfThreads() {
        return numberOfThreads;
    }

    public static void setNumberOfThreads(int numberOfThreads) {
        Validation.numberOfThreads = numberOfThreads;
    }

    public Links getCurrentlyCheckingLink() {
        return currentlyCheckingLink;
    }

    public void setCurrentlyCheckingLink(Links currentlyCheckingLink) {
        this.currentlyCheckingLink = currentlyCheckingLink;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }

    public int getFinalDepth() {
        return finalDepth;
    }

    public void setFinalDepth(int finalDepth) {
        this.finalDepth = finalDepth;
    }
    
    @Override
       public void run() {
       
        try {
            currentlyCheckingLink.validation(getNumberOfThreads(),currentDepth,finalDepth);
        }catch(IOException ex){
                    System.err.println("Error In Excecutor");
        }
       
   
     }
    
}
