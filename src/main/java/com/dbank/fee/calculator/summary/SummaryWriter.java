/**
 *
 */
package com.dbank.fee.calculator.summary;

import com.dbank.fee.calculator.common.Transaction;

import java.io.PrintStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Deepak Kumar Sharma
 */
public class SummaryWriter {

    private static final SummaryWriter summaryGenerator = new SummaryWriter();

    private SummaryWriter() {
    }

    public static SummaryWriter getInstance() {
        return summaryGenerator;
    }

    public void writeReport(Map<SummaryDTO, Double> report, PrintStream out) {

        out
                .println("************** REPORT GENERATION START ************* ");
        for (Map.Entry<SummaryDTO, Double> entry : report.entrySet()) {

            // A hack:( Updating the DTO represented by key, so that toString
            // just prints
            // report for us, rather than geting processing value from map
            // separately and sysout the things
            entry.getKey().setProcessingFee(entry.getValue());
            out.println(entry.getKey());
        }
        out
                .println("************** REPORT GENERATION END ************* ");
        out.flush();
    }
}
