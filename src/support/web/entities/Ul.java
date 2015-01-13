/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import support.web.AbsEnt;
import support.web.EnumAttrType;




/**
 *
 * @author Кот
 */
public class Ul extends WebEnt implements AbsEnt{

  public Ul() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style);
  }

  @Override
  public String render() {
    String result = "<ul";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    result += renderInnerEntities();
    result += "</ul>";

    return result;
  }
}
