package support.sender;

import java.util.ArrayList;
import java.util.List;
import support.StringAdapter;



/**
 * сообщение
 *
 * @author Rice Pavel
 */
public class Message {
  
  /**
   * заголовок
   */
  private String subject = "";
  /**
   * текст сообщения
   */
  private String message = "";
  /**
   * набор адресов получателей сообщения
   */
  private List<Object> to = new ArrayList();
  /**
   * адрес отправителя
   */
  private Object from;
  /**
   * файлы вложений
   */
  private List<Attachment> files = new ArrayList();

  /**
   * получить заголовок
   *
   * @return
   */
  public String getSubject() {
    return subject;
  }

  /**
   * установить заловок
   *
   * @param subject
   */
  public void setSubject(String subject) {
    if (StringAdapter.NotNull(subject)) {
      this.subject = subject;
    }
  }

  /**
   * получить текст сообщения
   *
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * установить тест сообщения
   *
   * @param message
   */
  public void setMessage(String message) {
    if (StringAdapter.NotNull(message)) {
      this.message = message;
    }
  }

  /**
   * 
   * @return набор адресов получателей сообщения
   */
  public List<Object> getTo() {
    return to;
  }

  /**
   * добавить адреса получателей сообщения
   * @param to 
   */
  public void setTo(Object... to) {
    if (to != null) {
      for (Object obj : to) {
        addTo(obj);
      }
    }
  }

  /**
   * установить адреса получателей сообщения
   * @param to 
   */
  public void setTo(List<Object> to) {
    if (to != null) {
      for (Object obj : to) {
        addTo(obj);
      }
    }
  }

  /**
   * добавить адрес получателей сообщения
   * @param to 
   */
  public void addTo(Object to) {
    if (StringAdapter.NotNull(to)) {
      this.to.add(to);
    }
  }

  /**
   * очистить адреса получателей сообщения
   */
  public void clearTo() {
    this.to = new ArrayList();
  }

  /**
   * 
   * @return адрес отправителя
   */
  public Object getFrom() {
    return from;
  }

  /**
   * установить адрес отправителя
   * @param from 
   */
  public void setFrom(String from) {
    if (StringAdapter.NotNull(from)) {
      this.from = from;
    }
  }

  /**
   * 
   * @return  файлы вложений
   */
  public List<Attachment> getFiles() {
    return files;
  }

  /**
   * установить файлы вложений
   * @param files 
   */
  public void setFiles(List<Attachment> files) {
    if (files != null) {
      for (Attachment at : files) {
        addFile(at);
      }
    }
  }

  /**
   * установить файлы вложений
   * @param files 
   */
  public void setFiles(Attachment... files) {
    for (Attachment at : files) {
      addFile(at);
    }
  }

  /**
   * очистить файлы вложений
   */
  public void clearFiles() {
    this.files = new ArrayList();
  }

  /**
   * добавить файл вложения
   * @param file 
   */
  public void addFile(Attachment file) {
    if (file != null) {
      files.add(file);
    }
  }
}
