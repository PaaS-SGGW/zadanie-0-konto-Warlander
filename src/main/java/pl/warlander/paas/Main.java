package pl.warlander.paas;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {
    
    public static void main(String[] args) {
        Spark.port(getHerokuAssignedPort());
        Spark.staticFiles.location("/");
        
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
