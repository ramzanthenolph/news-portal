import static spark.Spark.*;

import com.google.gson.Gson;
//import dao.SQL2oSitemapDao;
import models.*;
import dao.Sql2oNewsDao;
import dao.Sql2oDeptDao;
import dao.Sql2oUserDao;

public class App {
    public static void main(String[] args){
        Sql2oDeptDao deptDao = new Sql2oDeptDao();
        Sql2oUserDao userDao = new Sql2oUserDao();
        Sql2oNewsDao newsDao = new Sql2oNewsDao();
        //SQL2oSitemapDao sitemapDao = new SQL2oSitemapDao();
        Gson gson = new Gson();

        /*heroku config*/
        ProcessBuilder process = new ProcessBuilder();
        int port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        //departments

        get("/departments","application/json",(request, response) -> gson.toJson(deptDao.allDepartments()));
    }
}
