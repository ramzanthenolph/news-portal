import static spark.Spark.*;

import Exception.ApiException;
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

        post("/departments/new","application/json",(request, response) -> {
            Department department = gson.fromJson(request.body(),Department.class);
            deptDao.add(department);
            response.status(201);
            return gson.toJson(department);
        });

        get("/departments/:id","application/json",(request, response) -> {
            int deptId = Integer.parseInt(request.params("deptId"));
            return gson.toJson(deptDao.findById(deptId));
        });

        post("/departments/:deptId/users/new","application/json",(request, response) -> {
            int deptId = Integer.parseInt(request.params("deptId"));
            Department department = deptDao.findById(deptId);

            if(department != null){
                User employee = gson.fromJson(request.body(),User.class);
                employee.setDepartment(department.getName());
                userDao.add(employee);
                deptDao.addUserToDept(department,employee);
                response.status(201);
                return gson.toJson(employee);
            } else {
                throw new ApiException(404,"Department not found");
            }
        });

        get("/departments/:id/users","application/json",(request, response) -> {
            int deptId = Integer.parseInt(request.params("deptId"));
            return gson.toJson(deptDao.allDepartmentEmployees(deptId));
        });

        get("/departments/:id/news","application/json",(request, response) -> {
            int userId = Integer.parseInt(request.params("userId"));
            User foundUser = userDao.findById(userId);

            if (foundUser != null) {
                return gson.toJson(userDao.myNews(userId));
            }
            else {
                return "{\"Error 404!\":\"User not found\"}";
            }
        });

        post("/departments/:deptId/news/new","application/json",(request, response) -> {
            int userId = Integer.parseInt(request.params("userId"));
            int deptId = Integer.parseInt(request.params("deptId"));
            User foundUser = userDao.findById(userId);
            Department foundDept = deptDao.findById(deptId);

            if (foundUser != null && foundDept != null) {
                News news = gson.fromJson(request.body(),News.class);
                news.setType(foundDept.getName());
                news.setAuthor(foundUser.getName());
                newsDao.add(news);
                newsDao.addNewsToDepartment(deptId,news.getId(),userId);
                response.status(201);
                return gson.toJson(news);
            }
            else {
                return "{\"Error 404!\":\"User or Department not found\"}";
            }
        });

        //.....user

        get("/users","application/json",(request, response) -> gson.toJson(userDao.allUsers()));

        get("/users/:id","application/json",(request, response) -> {
            int userId = Integer.parseInt(request.params("userId"));
            User foundUser = userDao.findById(userId);
            if (foundUser != null) {
                return gson.toJson(userDao.findById(userId));
            }
            else {
                return "{\"Error 404!\":\"User not found.\"}";
            }
        });

        //.....news

        get("/","application/json",(request, response) -> gson.toJson(newsDao.allNews()));

        get("/news/:id","application/json",(request, response) -> {
            int newsId = Integer.parseInt(request.params("newsId"));
            return gson.toJson(newsDao.findById(newsId));
        });
    }
}
