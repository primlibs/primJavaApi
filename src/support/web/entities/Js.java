/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import support.web.AbsEnt;import support.web.EnumAttrType;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
public class Js extends WebEnt implements AbsEnt {

  public Js() {
  }

  @Override
  public String render() {
    String result = "<script type=\"text/javascript\">";
    result += data.getValue();
    result += "</script>";
    return result;
  }
}
