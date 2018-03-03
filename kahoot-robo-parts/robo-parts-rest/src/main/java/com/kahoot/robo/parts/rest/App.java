package com.kahoot.robo.parts.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kahoot.robo.parts.rest.controllers.*;
import com.typesafe.config.Config;
import joni.dep.ConfigParser;
import joni.dep.Container;
import joni.dep.ContainerBuilder;
import org.hibernate.SessionFactory;
import spark.Filter;
import spark.ResponseTransformer;

import static com.kahoot.robo.parts.rest.SparkHelper.enableCors;
import static spark.Spark.*;

public class App {
    private static Gson gson = new GsonBuilder().create();

    public static void main(String[] args) {
        Config config = new ConfigParser().parse(args);
        Container container = new ContainerBuilder(config, "com.kahoot.robo.parts.containers").build();

        Controller getAllPartsController = container.get(GetAllPartsController.class);
        Controller getPartController = container.get(GetPartController.class);
        Controller addPartController = container.get(AddPartController.class);
        Controller updatePartController = container.get(UpdatePartController.class);
        Controller deletePartController = container.get(DeletePartController.class);
        Controller getCompatiblePartsController = container.get(GetCompatiblePartsController.class);
        Controller addCompatibilityController = container.get(AddCompatibilityController.class);
        Controller removeCompatibilityController = container.get(RemoveCompatibilityController.class);

        enableCors();
        get("/parts", (req, res) -> getAllPartsController.execute(req), json());
        get("/parts/:id", (req, res) -> getPartController.execute(req), json());
        get("/parts/:id/compatible", (req, res) -> getCompatiblePartsController.execute(req), json());
        post("/parts", (req, res) -> addPartController.execute(req), json());
        post("/parts/:id/compatibility/:other/add", (req, res) -> addCompatibilityController.execute(req), json());
        post("/parts/:id/compatibility/:other/remove", (req, res) -> removeCompatibilityController.execute(req), json());
        put("/parts", (req, res) -> updatePartController.execute(req), json());
        delete("/parts/:id", (req, res) -> deletePartController.execute(req), json());
        after((Filter) (request, response) -> response.type("application/json"));
        before((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "X-Requested-With");
            response.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SessionFactory sessionFactory = container.get(SessionFactory.class);
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }));
    }

    private static ResponseTransformer json() {
        return gson::toJson;
    }
}
