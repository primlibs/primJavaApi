/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;


/**
 *
 * @author Кот
 */
public class Txt extends WebEnt {

  public Txt() {
  }

  @Override
  public String render() {
    String result = escapeHtml(data.getValue());
    return result;
  }
}
