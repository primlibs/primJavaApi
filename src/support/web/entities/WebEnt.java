/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import java.util.Collection;
import java.util.EnumMap;
import org.apache.commons.lang3.StringEscapeUtils;
import support.StringAdapter;
import support.web.AbsEnt;
import support.web.EnumAttrNoValue;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
public class WebEnt implements AbsEnt {

  protected AbsEntData data;

  WebEnt(EnumAttrType... types) {
    data = AbsEntData.getInstance(types);
  }

  public static AbsEnt getEnt(Type absEntType) throws Exception {
    AbsEnt ae;
    if(absEntType.equals(Type.B)){
        ae=new B();
    }else if(absEntType.equals(Type.BUTTON)){
        ae=new Button();
    }else if(absEntType.equals(Type.DIV)){
        ae=new Div();
    }else if(absEntType.equals(Type.FORM)){
        ae=new Form();
    }else if(absEntType.equals(Type.HREF)){
        ae=new Href();
    }else if(absEntType.equals(Type.IMG)){
        ae=new Img();
    }else if(absEntType.equals(Type.INPUT)){
        ae=new Input();
    }else if(absEntType.equals(Type.JS)){
        ae=new Js();
    }else if(absEntType.equals(Type.LABEL)){
        ae=new Label();
    }else if(absEntType.equals(Type.LI)){
        ae=new Li();
    }else if(absEntType.equals(Type.NOTESCAPETEXT)){
        ae=new NotEscapeText();
    }else if(absEntType.equals(Type.OPTION)){
        ae=new Option();
    }else if(absEntType.equals(Type.P)){
        ae=new P();
    }else if(absEntType.equals(Type.SELECT)){
        ae=new Select();
    }else if(absEntType.equals(Type.SPAN)){
        ae=new Span();
    }else if(absEntType.equals(Type.TABLE)){
        ae=new Table();
    }else if(absEntType.equals(Type.TD)){
        ae=new Td();
    }else if(absEntType.equals(Type.TEXTAREA)){
        ae=new TextArea();
    }else if(absEntType.equals(Type.TH)){
        ae=new Th();
    }else if(absEntType.equals(Type.TR)){
        ae=new Tr();
    }else if(absEntType.equals(Type.TXT)){
        ae=new Txt();
    }else if(absEntType.equals(Type.UL)){
        ae=new Ul();
    }else{
        throw new Exception("This AbsEntType does not supported yet");
    }
   return ae;
  }

  @Override
  public final AbsEnt setSingleAttribute(EnumAttrNoValue singleAttribute) {
    data.setSingleAttribute(singleAttribute);
    return this;
  }

  @Override
  public final AbsEnt removeSingleAttribute(EnumAttrNoValue singleAttribute) {
    data.removeSingleAttribute(singleAttribute);
    return this;
  }
  
  @Override
  public final AbsEnt setAttribute(EnumAttrType type, String value) {
    data.setAttribute(type, value);
    return this;
  }

  @Override
  public final AbsEnt addAttribute(EnumAttrType type, String value) {
    data.setAttribute(type, data.getAttribute(type) + value);
    return this;
  }

  @Override
  public final AbsEnt setValue(Object o) {
    data.setValue(o);
    return this;
  }

  @Override
  public final AbsEnt setCss(Object o) {
    data.setCss(o);
    return this;
  }

  @Override
  public final String getCss() {
    return data.getCss();
  }

  @Override
  public final AbsEnt setJs(Object o) {
    data.setJs(o);
    return this;
  }

  @Override
  public final String getJs() {
    return data.getJs();
  }

  @Override
  public final EnumMap<EnumAttrType, String> getAttributesClone() {
    return data.getAttributesClone();
  }

  @Override
  public final String getAttribute(EnumAttrType type) {
    return data.getAttribute(type);
  }

  @Override
  public final AbsEnt addEnt(AbsEnt... e) {
    data.addEnt(e);
    return this;
  }

  @Override
  public final AbsEnt addEnt(Collection<AbsEnt> e) {
    data.addEnt(e);
    return this;
  }

  @Override
  public final String getValue() {
    return data.getValue();
  }

  @Override
  public final String getStringAttrs() {
    return data.getStringAttrs();
  }

  @Override
  public final String getStringSingleAttrs() {
    return data.getStringSingleAttrs();
  }

  public final String renderInnerEntities() {
    return data.renderInnerEntities();
  }

  @Override
  public String render() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public static final String escapeHtml(Object value) {
    if (value != null) {
      return StringEscapeUtils.escapeHtml4(value.toString());
    } else {
      return "";
    }
  }

  @Override
  public Boolean existInnerEntities() {
   return data.existInnerEntities();
  }

  @Override
  public AbsEnt setFor(String name) {
   data.setFor(name);
   return this;
  }
  
  
  public final String renderFor() {
    if(StringAdapter.NotNull(data.forId)){
      return " for=\""+data.forId+"\" ";
    }
    return null;
  }

  @Override
  public AbsEnt setId(String name) {
    return addAttribute(EnumAttrType.id, name);    
  }
  
  public static enum Type{
      B,BUTTON,DIV,FORM,HREF,IMG,INPUT,JS,LABEL,LI,NOTESCAPETEXT
      ,OPTION,P,SELECT,SPAN,TABLE,TD,TEXTAREA,TH,TR,TXT,UL
  }
  
}
