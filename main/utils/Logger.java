package main.utils;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
public class Logger {
    private static final String LOG_FILE = "logs.txt";

    public static void log(String message){
        try(FileWriter writer = new FileWriter(LOG_FILE, true)){
            writer.write(LocalDateTime.now()+" - "+ message+"\n");
        }catch(IOException e){
            System.out.println("Failed to write to log file: "+ e.getMessage());
        }

    }
}
