package pl.warlander.paas;

import java.util.HashMap;
import java.util.Map;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

    private static Sql2o sql;
    
    public static void main(String[] args) {
        String databaseString = System.getenv("JDBC_DATABASE_URL");
        if (databaseString != null) {
            System.out.println(databaseString);
            System.out.println("Connecting to database");
            sql = new Sql2o(databaseString);
        }
        else {
            System.out.println("Launching locally, aborting");
            System.exit(0);
        }
        
        System.out.println("Detecting port");
        Spark.port(getHerokuAssignedPort());
        Spark.staticFiles.location("/");

        System.out.println("Initializing Spark");
        Spark.get("*", Main::handleMainPage);
    }

    private static String handleMainPage(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("title", "Heroku PaaS");
        attributes.put("message", "Current route: " + request.pathInfo());

        return new FreeMarkerEngine().render(new ModelAndView(attributes, "index.ftl"));
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080;
    }

}
