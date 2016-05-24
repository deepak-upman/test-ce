<%@ page import="com.dbank.fee.calculator.file.processor.TransactionFileProcessor, com.dbank.fee.calculator.common.*, org.apache.commons.io.output.WriterOutputStream,  java.io.PrintStream" %>

<html>
    <head>
        <title>WHat I am calculating!</title>
    </head>
    <body>
        <h2>Magic! I am doing some computation. What? I need to think of!</h2>

        <h2>There are more than one threads calculating the fee of transactions!</h2>
        <br />
        <%
                // Assume the file to exist on the classpath.
                TransactionFileProcessor transactionFileProcessor = new TransactionFileProcessor();
                transactionFileProcessor.processFile(TransactionDataFileType.CSV, "TransactionsSampleData.csv", (new PrintStream(new WriterOutputStream(out))));
    %>
    </body>
</html>
