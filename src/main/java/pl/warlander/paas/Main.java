package pl.warlander.paas;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Sql2o sql;
    
    public static void main(String[] args) {
        String databaseString = System.getenv("JDBC_DATABASE_URL");
        if (databaseString != null) {
            logger.info(databaseString);
            logger.info("Connecting to database");
            sql = new Sql2o(databaseString);
        }
        else {
            logger.info("Launching locally, aborting");
            System.exit(0);
        }
        
        logger.info("Detecting port");
        Spark.port(getHerokuAssignedPort());
        Spark.staticFiles.location("/");

        logger.info("Initializing Spark");
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
