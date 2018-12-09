package pl.warlander.paas;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;
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
            try {
                URI databaseUri = new URI(databaseString);
                int port = databaseUri.getPort();
                String host = databaseUri.getHost();
                String path = databaseUri.getPath();
                String username = (databaseUri.getUserInfo() == null) ? null : databaseUri.getUserInfo().split(":")[0];
                String password = (databaseUri.getUserInfo() == null) ? null : databaseUri.getUserInfo().split(":")[1];
                
                System.out.println("Connecting to database");
                sql = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
            } catch (URISyntaxException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        List<Text> texts = getTextListFromDatabase();
        attributes.put("texts", texts);
        
        return new FreeMarkerEngine().render(new ModelAndView(attributes, "index.ftl"));
    }
    
    private static List<Text> getTextListFromDatabase() {
        String query = "SELECT * FROM paas";
        
        try (Connection con = sql.open()) {
            return con.createQuery(query).executeAndFetch(Text.class);
        }
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080;
    }

}
