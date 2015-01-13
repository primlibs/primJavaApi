/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.web.entities;

import support.StringAdapter;
import support.web.AbsEnt;import support.web.EnumAttrType;
import support.web.EnumAttrType;

/**
 *
 * @author Кот
 */
public class Table extends WebEnt implements AbsEnt {

  private String head="";
  private String body="";
  public Table() {
    super(EnumAttrType.id,EnumAttrType.name,EnumAttrType.style,EnumAttrType.cellpadding
            ,EnumAttrType.cellspacing,EnumAttrType.border);
  }

  @Override
  public String render() {
    String result = "<table";
    result += getStringAttrs();
    result += getStringSingleAttrs();
    result += " " + data.getJs() + " ";
    result += ">";
    if(!data.entities.isEmpty()){
      for(AbsEnt ae:data.entities){
        if(StringAdapter.NotNull(ae.getAttribute(EnumAttrType.head))){
          head+=ae.render();
        }else{
          body+=ae.render();
        }
      }
    }
    result +="<thead>"+head+"</thead>";
    result +="<tbody>"+body+"</tbody>";
    result += "</table>";

    return result;
  }  
}
