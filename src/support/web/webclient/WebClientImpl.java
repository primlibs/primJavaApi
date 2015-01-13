package support.web.webclient;

import support.UploadedFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import support.StringAdapter;
import support.settings.ProjectSettings;
import support.settings.SqlSettings;


public class WebClientImpl implements WebClient {
  // объект Web-запроса
  private HttpServletRequest request;

  //переформированый запрос, проверенный на параметры, подается в приложение
  private HashMap<String, Object> innerRequest = new HashMap<String, Object>();
  private HashMap<String, Object> innerSession = new HashMap<String, Object>();
  private ProjectSettings projectSettings;
  private SqlSettings sqlSettings;
  private String renderType = RenderTypes.web.toString();
  private String object = "";
  private String action = "";
  private Random random = new Random();
  private List<UploadedFile> fileList = new ArrayList();
  private HashMap<String, String> filearray = new HashMap<String, String>();

  public WebClientImpl(HttpServletRequest request,ProjectSettings projectSettings,SqlSettings sqlSettings) throws Exception {
    this.request = request;
    this.projectSettings=projectSettings;
    this.sqlSettings=sqlSettings;
    checkRequest();
  }

  @Override
  public Map<String, Object> getInnerRequest() {
    innerRequest.put("_FILEARRAY_", filearray);
    return innerRequest;
  }

  @Override
  public String getObject() {
    return object;
  }

  @Override
  public String getAction() {
    return action;
  }

  
  
  @Override
  public String getRenderType() {
    return renderType;
  }

  private void setRenderType(String renderType) {
    if (renderType != null && !"".equals(renderType)) {
      this.renderType = renderType;
    }

  }


  public void setObject(String activeObjects) {
    if (activeObjects != null) {
      this.object = activeObjects;
    }
  }

  public void setAction(String activeAction) {
    if (activeAction != null) {
      this.action = activeAction;
    }
  }

  /**
   * проверяет запрос, выделяет Object-Name action также выделяет из запроса все
   * параметры
   */
  private void checkRequest() throws Exception {
    request.setCharacterEncoding("UTF-8");
    if (ServletFileUpload.isMultipartContent(request) == true) {
      checkMultypart();
    } else {
      for (String str : request.getParameterMap().keySet()) {
        checkParamsOnSpecName(str, request.getParameter(str));
      }
    }
  }

  /**
   * разбирает запрос вида Multipart
   *
   * @throws Exception
   */
  private void checkMultypart() throws Exception {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    Integer maxSize;
    maxSize = 1024 * 1024 * projectSettings.getMaxUploadSizeMb();
    factory.setSizeThreshold(maxSize);
    File tempDir = new File(projectSettings.getUploadPath());
    factory.setRepository(tempDir);
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(1024 * 1024 * projectSettings.getMaxUploadSizeMb()* 1000);
    upload.setHeaderEncoding(sqlSettings.getDbEncoding());
    Boolean canUpload = true;

    List items = upload.parseRequest(request);
    Iterator iter = items.iterator();
    while (iter.hasNext()) {
      FileItem item = (FileItem) iter.next();
      item.getFieldName();
      if (item.isFormField()) {
        checkParamsOnSpecNameMultipart(item.getFieldName(), item.getString(sqlSettings.getDbEncoding()));
      } else if (item.getName() != null && !item.getName().equals("") && canUpload == true) {
        File uploadedFile = null;
        do {
          String path = tempDir + "/" + random.nextInt(99999999) + item.getName();
          uploadedFile = new File(path);
        } while (uploadedFile.exists());
        //создаём файл
        uploadedFile.createNewFile();
        //записываем в него данные
        item.write(uploadedFile);
        if (uploadedFile.length() > maxSize) {
          uploadedFile.delete();
          canUpload = false;
        } else {
          filearray.put(uploadedFile.getAbsolutePath(), item.getName());
          fileList.add(new UploadedFile(uploadedFile.getAbsolutePath(),  item.getName()));
        }
      } else {
      }
    }

  }

  /**
   * Сохранение значения со стандартным именем
   *
   * @param name - наименование параметра
   * @param value - значение указанного выше параметра
   * @return стандартное имя, сохранено - true | нестандартное имя - false
   */
  private boolean saveValueWithStandardName(String name, Object value) {
    boolean valueIsSaved = true;
    if (name.equals(
            "object")) {
      setObject(value.toString().trim());
    } else if (name.equals(
            "action")) {
      setAction(value.toString().trim());
      action = value.toString().trim();
    } else if (name.equals(
            "renderType")) {
      setRenderType(value.toString().trim());
      renderType = value.toString().trim();
    } else { 
      valueIsSaved = false;
    }
    return valueIsSaved;
  }

  private void checkParamsOnSpecName(String name, Object value) {
    if (saveValueWithStandardName(name, value) == false) {
      String[] params = request.getParameterValues(name);
      if (params != null && params.length > 1) {
        innerRequest.put(name, params);
      } else {
        innerRequest.put(name, value.toString().trim());
      }
    }
  }

  private void checkParamsOnSpecNameMultipart(String name, Object value) {

    if (name == null || value == null) {
      return;
    }

    if (saveValueWithStandardName(name, value) == false) {
      if (!innerRequest.containsKey(name)) {
        innerRequest.put(name, value);
      } else {
        try {
          String[] str = (String[]) innerRequest.get(name);
          String[] newStr = Arrays.copyOf(str, str.length + 1);
          newStr[str.length] = value.toString();
          innerRequest.put(name, newStr);
        } catch (Exception e) {
          String oldValue = innerRequest.get(name).toString();
          String[] newStr2 = {oldValue, value.toString()};
          innerRequest.put(name, newStr2);
        }
      }
    }
  }

  @Override
  public List<UploadedFile> getUploadedFile() {
    return fileList;
  }

    @Override
    public Map<String, Object> getInnerSession() {
        return StringAdapter.cloneHashMap(innerSession);
    }
  
  
  public static enum RenderTypes {
    web,
    ajax,
    json, 
    doc
  }
}
