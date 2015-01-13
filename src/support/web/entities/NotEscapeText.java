/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import java.util.EnumMap;
import support.web.AbsEnt;import support.web.EnumAttrType;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
class NotEscapeText extends WebEnt implements AbsEnt {

  public NotEscapeText() {
    //super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.height,EnumAttrType.width);
  }

  @Override
  public String render() {
    String result = data.getValue();
    return result;
  }

}
