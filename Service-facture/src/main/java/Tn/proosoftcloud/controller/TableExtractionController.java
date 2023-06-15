package Tn.proosoftcloud.controller;

import Tn.proosoftcloud.services.IFacture;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@CrossOrigin(origins = "*")
public class TableExtractionController {

    @Value("${python.script.path}")
    private String pythonScriptPath;
    JSONObject json = new JSONObject();
    String jsonString = json.toString();
    @Autowired
    IFacture facture;

    @PostMapping(value = "/extract-table/{idfacture}")
    public ResponseEntity<Map<Integer, String[]>> extractTable(@RequestParam("image") MultipartFile imageFile,@PathVariable("idfacture") int idfacture) throws IOException {

        File tempImageFile = null;
        try {
            // Save the image to a temporary file
            tempImageFile = File.createTempFile(UUID.randomUUID().toString(), ".png");
            try (FileOutputStream fos = new FileOutputStream(tempImageFile)) {
                IOUtils.copy(imageFile.getInputStream(), fos);
            }

            // Build the HTTP request to call the Python script
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8000/api/extract_table/");
            FileBody imageBody = new FileBody(tempImageFile, ContentType.DEFAULT_BINARY);
            HttpEntity requestEntity = MultipartEntityBuilder.create()
                    .addPart("image", imageBody)
                    .build();
            httpPost.setEntity(requestEntity);

            // Send the HTTP request to the Python script
            HttpResponse response = httpClient.execute(httpPost);

            // Parse the JSON response from the Python script
            HttpEntity entity = response.getEntity();
            String content = null;
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
                    String responseString = EntityUtils.toString(entity, "UTF-8");

                    System.out.println(responseString);
                    JSONObject jsonResponse = new JSONObject(responseString);
                    String tables = jsonResponse.getString("tables");

                    // Create the response object
                    Map<String, String> responseBody = new HashMap<>();
                    responseBody.put("tables", tables);
                     Map<Integer, String[]> map = facture.divide_table(tables);
                    //testResult.addOrUpdateTestResult();)
                    return ResponseEntity.ok(map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            // Delete the temporary image file
            if (tempImageFile != null && tempImageFile.exists()) {
                tempImageFile.delete();
            }
        }

        // If there's no response from the server or an exception occurs, return a 500 error
        return ResponseEntity.status(500).body(null);
    }
}
