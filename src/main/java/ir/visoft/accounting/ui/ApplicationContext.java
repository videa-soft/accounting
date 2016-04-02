package ir.visoft.accounting.ui;

import ir.visoft.accounting.ui.controller.BaseController;

import java.util.HashMap;

/**
 * @author Amir .
 */
public class ApplicationContext {

    private static HashMap<Class, BaseController> controllers;



    public static BaseController getController(Class clazz) {
        BaseController controller = null;

        for (Class c : controllers.keySet()) {
            if(c.equals(clazz)) {
                controller = controllers.get(c);
                break;
            }
        }
        return controller;
    }

    public static void addController(BaseController controller) {
        if(controllers == null) {
            controllers = new HashMap<Class, BaseController>();
        }
        controllers.put(controller.getClass(), controller);
    }


    public static void removeController(Class clazz) {
        controllers.remove(clazz);
    }
}
