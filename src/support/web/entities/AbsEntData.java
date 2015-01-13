/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import support.StringAdapter;
import support.web.AbsEnt;
import support.web.EnumAttrNoValue;
import support.web.EnumAttrType;

/**
 *
 * @author кот
 */
final class AbsEntData {

  List<EnumAttrType> accessAttrTypes = new ArrayList<EnumAttrType>();
  EnumMap<EnumAttrType, String> attributes = new EnumMap<EnumAttrType, String>(EnumAttrType.class);
  List<AbsEnt> entities = new ArrayList<AbsEnt>();
  String value = "";
  String css = "";
  String javascript = "";
  List<EnumAttrNoValue> singleAttributes = new ArrayList<EnumAttrNoValue>();
  String forId=null;
  
  Map<String, Object> freeAttributes = new HashMap();

  public final void setAttribute(String name, String value) {
    freeAttributes.put(name, value);
  }
  
  private AbsEntData(EnumAttrType... types) {
    accessAttrTypes.addAll(Arrays.asList(types));
  }

  static AbsEntData getInstance(EnumAttrType... types) {
    return new AbsEntData(types);
  }
  
  public final void setSingleAttribute(EnumAttrNoValue singleAttribute) {
    if (singleAttributes != null) {
      singleAttributes.add(singleAttribute);
    }
  }
  
  public final void removeSingleAttribute(EnumAttrNoValue singleAttribute) {
    singleAttributes.remove(singleAttribute);
  }

  public final void setAttribute(EnumAttrType type, String value) {
    if (accessAttrTypes.contains(type)) {
        attributes.put(type, StringAdapter.getString(value));
    }
  }
  
  public final void addAttribute(EnumAttrType type, String value) {
    setAttribute(type, getAttribute(type) + value);
  }

  public final void setValue(Object o) {
    if (o != null) {
      value = o.toString();
    }
  }
  
  public final void setCss(Object o) {
    if (o != null) {
      css = o.toString();
    }
  }

  public final String getCss() {
    return css;
  }

  public final void setJs(Object o) {
    if (o != null) {
      javascript = o.toString();
    }
  }

  public final String getJs() {
    return javascript;
  }

  public final EnumMap<EnumAttrType, String> getAttributesClone() {
    EnumMap<EnumAttrType, String> clone = new EnumMap<EnumAttrType, String>(EnumAttrType.class);
    clone.putAll(attributes);
    return clone;
  }

  public final String getAttribute(EnumAttrType type) {
    String result = (attributes.get(type) == null ? "" : attributes.get(type));
    return result;
  }

  public final void addEnt(AbsEnt... e) {
    for(AbsEnt ee:e){
      if(ee!=null){
        entities.add(ee);
      }
    }
  }
  
  public final void addEnt(Collection<AbsEnt> e) {
    if (e != null) {
      for (AbsEnt ee : e) {
        addEnt(ee);
      }
    }
  }

  public final String getValue() {
    return value;
  }

  public final String getStringAttrs() {
    String result = " ";
    for (EnumAttrType ty : attributes.keySet()) {
      result += ty.toString() + "=\"" + attributes.get(ty).toString() + "\"";
    }
    if (!css.equals("")) {
        result += "class=\"" + css + "\"";
    }
    for (String name: freeAttributes.keySet()) {
      result += name + "=\"" + freeAttributes.get(name) + "\"";
    }
    result += " ";
    return result;
  }

  public final String getStringSingleAttrs() {
    String result = " ";
    for (EnumAttrNoValue ty : singleAttributes) {
      result += ty.toString();
    }
    result += " ";
    return result;
  }

  public final String renderInnerEntities() {
    String result = "";
    for (AbsEnt ae : entities) {
      if (ae != null) {
        result += ae.render();
      }
    }
    return result;
  }
  
  public Boolean existInnerEntities() {
   if(entities.size()>0){
     return true;
   }
   return false;
  }
  
  public void setFor(String name){
    this.forId=name;
  }
  
  
}
