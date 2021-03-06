package com.gpib;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.gpib.JVisa;
import com.gpib.JVisaException;
import com.gpib.JVisaReturnNumber;

import visa.VisaLibrary.ViStatus;
//import be.ac.ulb.gpib.GPIBDevice;
//import be.ac.ulb.gpib.WindowsGPIBDriver;
import visatype.VisatypeLibrary;

public class gpib 
	{	
	public String AGILENT_33220A= "USB0::0x0957::0x0407::MY44021621::0::INSTR";	
	public static void main(String[] args) {
        long resourceManager = -1, viStatus;
        
        /* Add values to the Array */
        List<Double> periodList = new ArrayList<Double>();        
        periodList.add(0.4612);
        periodList.add(0.3157);
        periodList.add(0.4285);
        periodList.add(0.4);
        periodList.add(0.4615);
        periodList.add(0.545);
        periodList.add(0.4);
        periodList.add(0.545);
        periodList.add(0.285);
        periodList.add(0.3);
        periodList.add(0.4612);
        JVisa jVisa = null;        
        final String rmError = "JVisa Test Error: Could not close resource manager session.";
        
        for (Double period : periodList) {        	
        	
	        try {
	            System.out.println("Testing JVisa...");
	            jVisa = new JVisa();
	
	            System.out.println("Open Default Resource Manager.");
	            viStatus = jVisa.openDefaultResourceManager();
	            if (viStatus != VisatypeLibrary.VI_SUCCESS) {
	                throw new JVisaException("Could not create session for resource manager.");
	            }
	            System.out.println("Success.\r\n");
	
	            System.out.println("Get resource version.");
	            resourceManager = jVisa.getVisaResourceManagerHandle();
	            JVisaReturnNumber version = new JVisaReturnNumber(-1L);
	            viStatus = jVisa.getResourceVersion(version);
	            if (viStatus != VisatypeLibrary.VI_SUCCESS) {
	                throw new JVisaException("Could not create session for resource manager.");
	            }
	            // Obtain the version.
	            System.out.println(String.format("Version is 0x%08X.\r\n", version.returnNumber));
	
	
	            String instrumentString = "USB0::0x0957::0x0407::MY44021621::0::INSTR";//TCPIP::138.67.34.158::INSTR";
	            System.out.println(String.format("Open instrument session for %s.", instrumentString));
	            viStatus = jVisa.openInstrument(instrumentString);
	            if (viStatus != VisatypeLibrary.VI_SUCCESS) {
	                throw new JVisaException(String.format("Could not open instrument session for %s.", instrumentString));
	            }
	            System.out.println("Success.\r\n");
	            try{	            	
		            System.out.println ("Resetting the Device");
		            long result = jVisa.write("*RST");            
		            System.out.println(result);
		            Thread.currentThread().sleep(50);
					System.out.println ("Disable The output");
					//long result1 = jVisa.write(":OUTP:STAT 0");            
					//System.out.println(result1);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Freqency");
					long result2 = jVisa.write(":SOUR:FREQ:CW 1.1512 MHZ");            
					System.out.println(result2);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Amplitude");
					long result3 = jVisa.write(":SOUR:VOLT:LEV:IMM:AMPL 0.1 VPP");            
					System.out.println(result3);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Cycle Phase");
					long result4 = jVisa.write(":SOUR:BURS:PHAS 0 ");            
					System.out.println(result4);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Burst State");
					long result5 = jVisa.write(":SOUR:BURS:STAT 1");            
					System.out.println(result5);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Burst Cycles");
					long result6 = jVisa.write(":SOUR:BURS:NCYC 50000");            
					System.out.println(result6);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Burst Cycles");
					long result7 = jVisa.write(":SOUR:BURS:NCYC 50000");            
					System.out.println(result7);
					Thread.currentThread().sleep(50);
					System.out.println ("Setting Period to "+ period);
					long result8 = jVisa.write(":SOUR:BURS:INT:PER "+ period +" S");            
					System.out.println(result8);
					Thread.currentThread().sleep(50);
					System.out.println ("Enable The output");
					long result9 = jVisa.write(":OUTP:STAT 1");            
					System.out.println(result9);
					Thread.currentThread().sleep(12000);
	        	}
				catch(Exception e)
	            {
	               System.out.println("Exception caught-Reset");
	            }
	
	            //print(jVisa.setAttribute(":OUTP:STAT", 0));//  ("*RST"));     
	            /*
	            System.out.println ("Disable the Output");
	            print(inst_33220A.write(":OUTP:STAT 0")); 
	            System.out.println("Setting Frequency");
	            Thread.sleep(50);
	            print(inst_33220A.write(":SOUR:FREQ:CW 1.1512 MHZ"));     
	            System.out.println("Setting Amplitude");
	            Thread.sleep(50);
	            print(inst_33220A.write(":SOUR:VOLT:LEV:IMM:AMPL 0.1 VPP"));        //#:SOUR:VOLT:LEV:IMM:AMPL 0.1
	            System.out.println ("Setting Cycle Phase");
	            Thread.sleep(50);
	            print (inst_33220A.write(":SOUR:BURS:PHAS 0 "));
	            System.out.println ("Enable Burst State");
	            Thread.sleep(50);
	            print(inst_33220A.write(":SOUR:BURS:STAT 1"));  
	            System.out.println ("Setting Burst Cycles");
	            Thread.sleep(50);      
	            print (inst_33220A.write(":SOUR:BURS:NCYC 50000"));
	            System.out.println ("Setting Period to %.2f S" %float(period));
	            Thread.sleep(50);
	            print (inst_33220A.write(":SOUR:BURS:INT:PER %f S" %float(period)));       
	            System.out.println ("Enable the Output");
	            Thread.sleep(50);
	            print(inst_33220A.write(":OUTP:STAT 1"));
	            */
	            System.out.println("Close resource manager session.");
	            viStatus = jVisa.closeResourceManager();
	            if (viStatus != VisatypeLibrary.VI_SUCCESS) {
	                System.err.println(rmError);
	            }
	            System.out.println("Success.\r\n");
	          }
	          catch (JVisaException e) {
	              System.err.println(e.toString());
	              if (resourceManager != -1 && jVisa != null) {
	                  jVisa.closeResourceManager();
	              }
	          }
	          finally {
	            System.out.println("Finished testing JVisa.");
	          }
        }   // End of For Loop        
      }
}
