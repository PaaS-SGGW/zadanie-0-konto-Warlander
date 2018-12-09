package pl.warlander.paas;

import spark.Spark;

public class Main {
    
    public static void main(String[] args) {
        Spark.port(getHerokuAssignedPort());
        Spark.staticFiles.location("/resources");
    }
    
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080;
    }
    
}
