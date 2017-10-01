package com.squirrel.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;


/**
 * Utility class to support use of the Apache Derby database.
 *
 * @author Nick Efford
 * @version 1.2 (2016-08-26)
 */
public class DerbyUtils {
   /**
    * Shuts down all Derby databases running within the JVM.
    */
   public static void shutdown() {
      try {
         DriverManager.getConnection("jdbc:derby:;shutdown=true");
         System.err.println("Problem with shutdown!");
      }
      catch (SQLException error) {
         // Successful shutdown always results in an SQLException!
         System.out.println("Shutdown completed successfully.");
      }
   }

   /**
    * Shuts down a specific Derby database running within the JVM.
    *
    * @param database Name of database to be shut down
    */
   public static void shutdown(String database) {
      try {
         String conn = String.format("jdbc:derby:%s;shutdown=true", database);
         DriverManager.getConnection(conn);
         System.err.println("Problem with shutdown!");
      }
      catch (SQLException error) {
         // Successful shutdown always results in an SQLException!
         System.out.println("Shutdown completed successfully.");
      }
   }

   /**
    * Traces SQL exceptions.
    *
    * @param error Reference to first exception
    */
   public static void traceErrors(SQLException error) {
      while (error != null) {
         System.err.printf("Error, SQLSTATE %s, error code %s:%n",
                 error.getSQLState(),
                 error.getErrorCode());
         System.err.printf(" %s%n", error.getMessage());
         error = error.getNextException();
      }
   }

   /**
    * Traces SQL warnings that occurred during execution of a statement.
    *
    * @param warning Reference to first warning
    */
   public static void traceWarnings(SQLWarning warning) {
      while (warning != null) {
         System.out.printf("Warning, SQLSTATE %s, error code %s:%n",
                 warning.getSQLState(),
                 warning.getErrorCode());
         System.out.printf(" %s%n", warning.getMessage());
         warning = warning.getNextWarning();
      }
   }
}