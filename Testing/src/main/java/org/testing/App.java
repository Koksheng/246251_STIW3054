package org.testing;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        ReadPDF readPDF = new ReadPDF();
        readPDF.extractText("D:/Users/User/Desktop/Homework/Sem 5/JWP Final A181.pdf");
        readPDF.calculateText();

    }
}
