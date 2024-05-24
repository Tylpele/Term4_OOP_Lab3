import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(urlPatterns = {"/addition"})
public class AdditionServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        // создание json объекта для нового ноутбука
        JSONObject laptopJson = new JSONObject();
        laptopJson.put("price", req.getParameter("price"));
        laptopJson.put("display", req.getParameter("display"));
        laptopJson.put("model", req.getParameter("model"));
        laptopJson.put("processor", req.getParameter("processor"));
        laptopJson.put("storage_drive", req.getParameter("storage_drive"));
        laptopJson.put("RAM", req.getParameter("RAM"));

        //путь к json
        String jsonPath = getJsonPath();

        // чтение содержимого
        String jsonContent = new String(Files.readAllBytes(Path.of(jsonPath)));

        //разбиение json на массив
        JSONArray jsonArray = new JSONArray(jsonContent);

        //добавление объекта ноутбука в массив
        jsonArray.put(laptopJson);

        //обновление json локально
        writeJsonToFile(jsonPath, jsonArray);

        //обновление json на сервере
        writeJsonToFile("laptops.json", jsonArray);

        //обновление страницы для отображения новых данных
        updatePage(req, resp);
        }

    private String getJsonPath() {
        try {
            String parentPath = new File(AdditionServlet.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParentFile().getParent();
            return parentPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "laptops.json";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeJsonToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonArray.toString(4));
        } catch (IOException ex) {
            System.out.println("Ошибка записи JSON в файл: " + ex.getMessage());
        }
    }

    private void updatePage(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("index.html");
        view.forward(request, response);
    }


    }
