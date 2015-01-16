/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import support.StringAdapter;
import support.db.executor.Row;
import support.enums.Validators;
import support.filterValidator.ChainValidator;
import support.settings.ProjectSettings;
import support.web.AbsEnt;
import support.web.EnumAttrNoValue;
import support.web.EnumAttrType;
import support.web.FormOption;
import support.web.FormOptionInterface;
import support.web.HrefOption;
import support.web.HrefOptionInterface;
import support.web.Parameter;
import support.web.RenderConstant;
import support.web.entities.WebEnt;
import support.web.webclient.WebClientImpl;
import support.web.webclient.WebClientImpl.RenderTypes;

/**
 *
 * @author пользователь
 */
public final class FabricRender {

    public RenderConstant rc = new RenderConstant();
    private String baseLinkPath = "";
    private String renderResult = "";

    private FabricRender(ProjectSettings projectSettings) {
        baseLinkPath = projectSettings.getBaseLinkPath();
    }

    public static FabricRender getInstance(ProjectSettings projectSettings) {
        return new FabricRender(projectSettings);
    }

    public final static AbsEnt img(String img, String width, String height, String style) throws Exception {

        AbsEnt ae = WebEnt.getEnt(WebEnt.Type.IMG);
        ae.setAttribute(EnumAttrType.src, img);
        ae.setAttribute(EnumAttrType.width, width);
        ae.setAttribute(EnumAttrType.height, height);
        ae.setAttribute(EnumAttrType.style, style);
        return ae;
    }

    /**
     * получить картинку по переданному контенту в виде строки
     *
     * @param content контент
     * @param width ширина
     * @param height высота
     * @param style стиль
     * @return
     * @throws Exception
     */
    public final AbsEnt imgByContent(Object content, String width, String height, String style) throws Exception {
        return img("data:image/gif;base64," + content, width, height, style);
    }

    public AbsEnt txt(Object value) throws Exception {
        AbsEnt txt = WebEnt.getEnt(WebEnt.Type.TXT);
        txt.setValue(value);
        return txt;
    }

    public AbsEnt textInput(String name, Object value, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "text");
        ae.setAttribute(EnumAttrType.size, rc.TXT_INPUT_SIZE);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt dateInput(String name, Object value, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "text");
        ae.setAttribute(EnumAttrType.size, rc.TXT_INPUT_SIZE);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setCss("standart_datepicker");
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setValue(dateFormat(value, getRenderConstant().DT_SMALL));
        return ae;
    }

    public AbsEnt dateTimeInput(String name, Object value, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "text");
        ae.setAttribute(EnumAttrType.size, rc.TXT_INPUT_SIZE);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setCss("timepicker");
        ae.removeSingleAttribute(EnumAttrNoValue.readonly);
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setValue(dateFormat(value, getRenderConstant().DT_FULL));
        return ae;
    }

    public AbsEnt passInput(String name, Object value, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "password");
        ae.setAttribute(EnumAttrType.size, rc.TXT_INPUT_SIZE);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt submitInput(String name, Object value) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "submit");
        ae.setAttribute(EnumAttrType.size, "15");
        ae.setAttribute(EnumAttrType.name, "submit");
        ae.setAttribute(EnumAttrType.title, name);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt formSubmitImage(String title) throws Exception {
        return formSubmitImage(title, rc.SUBMITIMG_SIZE);
    }

    public AbsEnt formSubmitImage(String title, String width) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "image");
        ae.setAttribute(EnumAttrType.title, title);
        ae.setAttribute(EnumAttrType.width, width);
        ae.setAttribute(EnumAttrType.name, "submit");
        ae.setValue(title);
        return ae;

    }

    public AbsEnt formSubmit(String title, String img) throws Exception {
        AbsEnt ae;
        if (img == null || img.equals("")) {
            ae = submitInput(title, title);
            ae.setValue(title);
        } else {
            ae = formSubmitImage(title);
            ae.setAttribute(EnumAttrType.src, img);
            ae.setValue(title);
        }
        return ae;
    }

    public AbsEnt hiddenInput(String name, Object value) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "hidden");
        ae.setAttribute(EnumAttrType.name, name);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt fileInput(String name, Object value, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.INPUT);
        ae.setAttribute(EnumAttrType.type, "file");
        ae.setAttribute(EnumAttrType.size, rc.TXT_INPUT_SIZE);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt checkBox(String name, Boolean checked, String id) throws Exception {
        AbsEnt ch;
        ch = WebEnt.getEnt(WebEnt.Type.INPUT);
        ch.setAttribute(EnumAttrType.type, "checkbox").setAttribute(EnumAttrType.id, id);
        ch.setAttribute(EnumAttrType.name, name);
        ch.setValue(1);
        if (checked == true) {
            ch.setJs(" " + ch.getJs() + " checked ");
        }
        return ch;
    }

    public AbsEnt checkBox(String name, Boolean checked, Object value, String id) throws Exception {
        AbsEnt ch;
        ch = WebEnt.getEnt(WebEnt.Type.INPUT);
        ch.setAttribute(EnumAttrType.type, "checkbox").setAttribute(EnumAttrType.id, id);
        ch.setAttribute(EnumAttrType.name, name);
        ch.setValue(value);
        if (checked == true) {
            ch.setJs(" " + ch.getJs() + " checked ");
        }
        return ch;
    }

    public AbsEnt checkBox(String name, Object ob) throws Exception {
        if (StringAdapter.NotNull(ob)) {
            return checkBox(name, Boolean.TRUE, null);
        } else {
            return checkBox(name, Boolean.FALSE, null);
        }
    }

    public AbsEnt combo(Map<String, Object> map, Object value, String name) throws Exception {
        AbsEnt select;
        select = WebEnt.getEnt(WebEnt.Type.SELECT);
        select.setAttribute(EnumAttrType.name, name);
        for (String st : map.keySet()) {
            AbsEnt option = WebEnt.getEnt(WebEnt.Type.OPTION);
            option.setValue(st);
            AbsEnt txt = WebEnt.getEnt(WebEnt.Type.TXT);
            txt.setValue(map.get(st));
            option.addEnt(txt);

            if (value != null && value.toString().equals(st)) {
                option.setJs("selected");
            }
            select.addEnt(option);
        }
        return select;
    }

    /**
     * возвращает элемент формы - выпадающий список
     *
     * @param map массив параметров
     * @param value значение
     * @param name название
     * @param mandatory является ли обязательным
     * @return
     * @throws Exception
     */
    public AbsEnt combo(Map<String, Object> map, Object value, String name, boolean mandatory) throws Exception {
        Map<String, Object> params = new LinkedHashMap();
        if (!mandatory) {
            params.put("", "не выбрано");
        }
        params.putAll(map);
        return combo(params, value, name);
    }
    
    public AbsEnt textArea(String name, Object ob, String placeholder) throws Exception {
        return textArea(name, ob, null, null, placeholder);
    }

    public AbsEnt textArea(String name, Object ob, Object rows, Object cols, String placeholder) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.TEXTAREA);
        ae.setAttribute(EnumAttrType.name, name);
        ae.setValue(ob);
        ae.setAttribute(EnumAttrType.placeholder, placeholder);
        ae.setAttribute(EnumAttrType.rows, StringAdapter.getString(rows));
        ae.setAttribute(EnumAttrType.cols, StringAdapter.getString(cols));
        return ae;

    }

    public String dateFormat(Object ob, String format) {
        String res = "";
        if (ob != null) {
            ob.toString();
            ChainValidator cv = new ChainValidator();
            HashMap<String, Object> hs = new HashMap<String, Object>();
            hs.put("format", format);
            cv.addChain(Validators.DATETOFORMATFILTER, hs);
            cv.execute(ob);
            if (cv.getData() != null) {
                res = cv.getData().toString();
            }
        }
        return res;
    }

    public AbsEnt div(String id, Object value, String css, String javaScript) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.DIV);
        ae.setAttribute(EnumAttrType.id, id);
        ae.setCss(css);
        ae.setJs(javaScript);
        if (value instanceof AbsEnt) {
            ae.addEnt((AbsEnt) value);
        } else {
            ae.setValue(value);
        }
        return ae;
    }

    public String phoneNumberFt(Object number, String base) {
        String newft = "";
        if (number != null) {
            newft = number.toString();
            if (newft.length() == 11) {
                char[] nearr = {newft.charAt(1), newft.charAt(2), newft.charAt(3), newft.charAt(4)};

                char[] one = {newft.charAt(0)};
                char[] three1 = {newft.charAt(1), newft.charAt(2), newft.charAt(3)};
                char[] three2 = {newft.charAt(4), newft.charAt(5), newft.charAt(6)};
                char[] two = {newft.charAt(7), newft.charAt(8)};
                char[] two2 = {newft.charAt(9), newft.charAt(10)};
                String prefix = new String(nearr);
                String res;
                if (prefix.equals(base)) {
                    res = new String(three2) + "-" + new String(two) + "-" + new String(two2);
                } else {
                    res = new String(one) + " (" + new String(three1) + ") " + new String(three2) + "-" + new String(two) + "-" + new String(two2);
                }
                newft = res;
            }
        }
        return newft;
    }

    public AbsEnt table(String id, String style, String border, String cellpadding, String sellspacing, String javaScript) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.TABLE);
        ae.setAttribute(EnumAttrType.id, id);
        ae.setAttribute(EnumAttrType.style, style);
        ae.setJs(javaScript);
        ae.setAttribute(EnumAttrType.cellpadding, cellpadding);
        ae.setAttribute(EnumAttrType.cellspacing, sellspacing);
        ae.setAttribute(EnumAttrType.border, border);
        ae.setAttribute(EnumAttrType.id, id);
        return ae;
    }

    public AbsEnt table(String border, String cellpadding, String sellspacing) throws Exception {
        return table(null, null, border, cellpadding, sellspacing, null);
    }

    /**
     * вывод числа в "денежном" формате
     *
     * @param data
     * @return
     */
    public String renderDecimal(Object data) {
        if (data != null) {
            ChainValidator chain = new ChainValidator();
            chain.addChain(Validators.DECIMALFILTER);
            chain.execute(data);
            return chain.getData().toString();
        } else {
            return "";
        }
    }

    public AbsEnt td(String inner) throws Exception {
        return td(null, inner);
    }

    public AbsEnt td(AbsEnt tr, Object inner) throws Exception {
        AbsEnt td = WebEnt.getEnt(WebEnt.Type.TD);
        if (inner instanceof AbsEnt) {
            AbsEnt inn = (AbsEnt) inner;
            td.addEnt(inn);
        } else {
            td.setValue(inner);
        }
        if (tr != null) {
            tr.addEnt(td);
        }
        return td;
    }

    public AbsEnt th(String inner) throws Exception {
        return th(null, inner);
    }

    public AbsEnt th(AbsEnt tr, Object inner) throws Exception {
        AbsEnt td = WebEnt.getEnt(WebEnt.Type.TH);
        if (inner instanceof AbsEnt) {
            AbsEnt inn = (AbsEnt) inner;
            td.addEnt(inn);
        } else {
            td.setValue(inner);
        }
        if (tr != null) {
            tr.addEnt(td);
        }
        return td;
    }

    public AbsEnt tr(Object... inners) throws Exception {
        return tr(null, inners);
    }

    public AbsEnt tr(AbsEnt table, Object... inners) throws Exception {
        AbsEnt tr = WebEnt.getEnt(WebEnt.Type.TR);
        for (Object obj : inners) {
            td(tr, obj);
        }
        if (table != null) {
            table.addEnt(tr);
        }
        return tr;
    }

    public AbsEnt trTh(AbsEnt table, Object... inners) throws Exception {
        AbsEnt tr = WebEnt.getEnt(WebEnt.Type.TR).addAttribute(EnumAttrType.head, "1");
        for (Object obj : inners) {
            th(tr, obj);
        }
        if (table != null) {
            table.addEnt(tr);
        }
        return tr;
    }

    public AbsEnt span(String id, String style, Object value, String javaScript, String css) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.SPAN);
        ae.setAttribute(EnumAttrType.id, id);
        ae.setAttribute(EnumAttrType.style, style);
        ae.setCss(css);
        ae.setJs(javaScript);
        ae.setValue(value);
        return ae;
    }

    public AbsEnt span(String id, String style, Object value, String javaScript) throws Exception {
        return span(null, style, value, null, null);
    }

    public AbsEnt span(String style, Object value) throws Exception {
        return span(null, style, value, null);
    }

    public AbsEnt span(String style, String css, Object value) throws Exception {
        return span(null, style, value, null, css);
    }

    /**
     * \
     * Создает форму для загрузки файло и кнопку для добавления подобных,
     * связано со скриптом в index.java
     *
     * @return html код в виде сущности (форма для загрузки(
     * @throws Exception
     */
    public AbsEnt multipleFileForm() throws Exception {
        AbsEnt filesDiv = WebEnt.getEnt(WebEnt.Type.DIV);
        filesDiv.addAttribute(EnumAttrType.id, "files");

        AbsEnt file1Div = WebEnt.getEnt(WebEnt.Type.DIV);
        file1Div.addAttribute(EnumAttrType.id, "file1");

        AbsEnt input = WebEnt.getEnt(WebEnt.Type.INPUT);
        input.addAttribute(EnumAttrType.id, "userfile1");
        input.setCss("uploadFile");
        input.addAttribute(EnumAttrType.type, "file");
        input.setJs("onchange='fileAdd(2)'");
        input.addAttribute(EnumAttrType.size, "80");
        input.addAttribute(EnumAttrType.name, "file1[]");
        input.setSingleAttribute(EnumAttrNoValue.multiple);
        file1Div.addEnt(input);
        filesDiv.addEnt(file1Div);

        return (filesDiv);
    }

    public AbsEnt li(AbsEnt obj) throws Exception {
        AbsEnt li = WebEnt.getEnt(WebEnt.Type.LI);
        if (obj != null) {
            li.addEnt(obj);
            return li;
        }
        return null;
    }

    public AbsEnt label(String forName, String value, String css, String javascript) throws Exception {
        AbsEnt label = WebEnt.getEnt(WebEnt.Type.LABEL);
        if (label != null) {
            label.setFor(forName);
            label.setValue(value);
            label.setJs(javascript);
            label.setCss(css);
            return label;
        }
        return null;
    }

    public AbsEnt div(String style, Object value) throws Exception {
        AbsEnt res = div(null, value, null, null);
        if (res != null) {
            res.setAttribute(EnumAttrType.style, style);
        }
        return res;
    }

    /**
     * безопасно добавляет css
     */
    public AbsEnt addCss(AbsEnt ae, String css) {
        if (ae != null) {
            ae.setCss(css);
        }
        return ae;
    }

    /**
     * безопасно добавляет стиль
     */
    public AbsEnt addStyle(AbsEnt ae, String style) {
        if (ae != null) {
            ae.setAttribute(EnumAttrType.style, style);
        }
        return ae;
    }

    /**
     * регистрирует Exception, то есть записывает в renderResult полный стек
     * объекта Exception
     *
     * @param exc объект Exception
     */
    public void registerException(Exception exc) {
        renderResult += StringAdapter.getStackTraceException(exc);
    }

    public void addRenderResult(String str) {
        renderResult += str;
    }

    public RenderConstant getRenderConstant() {
        return rc;
    }

    /**
     * возвращает select с множественным выбором
     *
     * @param map массив значений
     * @param value выбранное значение, либо массив выбранных значений, либо
     * null
     * @param name параметр name в select
     * @param size параметр size. Может быть null
     * @return
     * @throws Exception
     */
    public AbsEnt multipleCombo(Map<String, Object> map, Object value, String name, Integer size) throws Exception {
        AbsEnt select;
        select = WebEnt.getEnt(WebEnt.Type.SELECT);
        select.setAttribute(EnumAttrType.name, name);
        select.setSingleAttribute(EnumAttrNoValue.multiple);
        if (size != null) {
            select.setAttribute(EnumAttrType.size, size.toString());
        }

        List<String> valuesList = new ArrayList();
        if (value != null) {
            try {
                valuesList = Arrays.asList((String[]) value);
            } catch (Exception e) {
                String param = value.toString();
                valuesList.add(param);
            }
        }

        for (String st : map.keySet()) {
            AbsEnt option = WebEnt.getEnt(WebEnt.Type.OPTION);
            option.setValue(st);
            AbsEnt txt = WebEnt.getEnt(WebEnt.Type.TXT);
            txt.setValue(map.get(st));
            option.addEnt(txt);

            if (valuesList.contains(st)) {
                option.setJs("selected");
            }
            select.addEnt(option);
        }
        return select;
    }

    public AbsEnt p(String id, Object value, String css, String javaScript) throws Exception {
        AbsEnt ae = WebEnt.getEnt(WebEnt.Type.P);
        ae.setAttribute(EnumAttrType.id, id);
        ae.setCss(css);
        ae.setJs(javaScript);
        if (value instanceof AbsEnt) {
            ae.addEnt((AbsEnt) value);
        } else {
            ae.setValue(value);
        }
        return ae;
    }

    public AbsEnt form(Boolean formToUploadFiles) throws Exception {
        AbsEnt ae;
        ae = WebEnt.getEnt(WebEnt.Type.FORM);
        if (formToUploadFiles != null && formToUploadFiles) {
            ae.setAttribute(EnumAttrType.enctype, "multipart/form-data");
        }
        ae.setAttribute(EnumAttrType.method, "POST");
        ae.setAttribute(EnumAttrType.action, StringAdapter.getString(baseLinkPath));
        return ae;
    }

    /**
     *
     * @param inner поля формы
     * @param fo объект с настройками формы
     * @return форму
     * @throws Exception
     */
    public AbsEnt rightForm(Map<AbsEnt, String> inner, FormOptionInterface fo) throws Exception {
        AbsEnt ae = WebEnt.getEnt(WebEnt.Type.TXT);
        Boolean rend = true;
        if (fo.isRights()) {
            rend = false;
        }
        if (rend == true) {
            if (fo.isHorizontal() == true) {
                ae = horizontalForm(inner, fo);
            } else {
                ae = verticalForm(inner, fo);
            }
            ae.addEnt(hiddenInput("action", fo.getAction()));
            ae.addEnt(hiddenInput("object", fo.getObject()));
            ae.setAttribute(EnumAttrType.style, "float:left;padding:0;");
            ae.setJs(fo.getJshandler());
            ae.addEnt(hiddenInput("renderType", fo.getRenderType()));
        }
        return ae;
    }

    private AbsEnt horizontalForm(Map<AbsEnt, String> inner, FormOptionInterface fo) throws Exception {
        AbsEnt ae = form(fo.isFile());
        AbsEnt table = WebEnt.getEnt(WebEnt.Type.TABLE);
        ae.addEnt(table);
        AbsEnt tr = WebEnt.getEnt(WebEnt.Type.TR);
        table.addEnt(tr);
        Map<AbsEnt, String> idMap = fo.getIdMap();

        AbsEnt buttonTd = WebEnt.getEnt(WebEnt.Type.TD);
        if (!StringAdapter.NotNull(fo.getВuttonName())) {
            AbsEnt formSumbit = formSubmit(fo.getTytle(), fo.getImg());
            if (fo.getImgWidth() != null) {
                formSumbit.setAttribute(EnumAttrType.width, fo.getImgWidth().toString());
            }
            if (StringAdapter.NotNull(fo.getButtonCssClass())) {
                formSumbit.setCss(fo.getButtonCssClass());
            }
            buttonTd.addEnt(formSumbit);
        } 

        if (fo.isPlaceButtonAtBegin()) {
            tr.addEnt(buttonTd);
        }

        if (inner != null) {
            for (AbsEnt aee : inner.keySet()) {
                if (aee.getAttribute(EnumAttrType.type).equals("hidden")) {
                    tr.addEnt(aee);
                } else {
                    AbsEnt td1 = WebEnt.getEnt(WebEnt.Type.TD);
                    if (idMap != null && idMap.get(aee) != null) {
                        td1.setId(idMap.get(aee));
                    }
                    td1.setValue(inner.get(aee));
                    tr.addEnt(td1);
                    AbsEnt td = WebEnt.getEnt(WebEnt.Type.TD);
                    td.addEnt(aee);
                    tr.addEnt(td);
                }
            }
        }

        if (!fo.isPlaceButtonAtBegin()) {
            tr.addEnt(buttonTd);
        }

        ae.addEnt(hiddenInput("submit", "submit"));
        return ae;
    }

    private AbsEnt verticalForm(Map<AbsEnt, String> inner, FormOptionInterface fo) throws Exception {
        AbsEnt ae = form(fo.isFile());
        AbsEnt table = WebEnt.getEnt(WebEnt.Type.TABLE);
        ae.addEnt(table);
        Map<AbsEnt, String> idMap = fo.getIdMap();
        if (inner != null) {
            for (AbsEnt aee : inner.keySet()) {
                if (aee.getAttribute(EnumAttrType.type).equals("hidden")) {
                    table.addEnt(aee);
                } else {
                    AbsEnt tr = WebEnt.getEnt(WebEnt.Type.TR);
                    if (idMap != null && idMap.get(aee) != null) {
                        tr.setId(idMap.get(aee));
                    }
                    table.addEnt(tr);
                    AbsEnt td1 = WebEnt.getEnt(WebEnt.Type.TD);
                    td1.setValue(inner.get(aee));
                    tr.addEnt(td1);
                    AbsEnt td = WebEnt.getEnt(WebEnt.Type.TD);
                    td.addEnt(aee);
                    tr.addEnt(td);
                }
            }
        }
        AbsEnt tr = WebEnt.getEnt(WebEnt.Type.TR);
        table.addEnt(tr);
        if (!StringAdapter.NotNull(fo.getВuttonName())) {
            AbsEnt td = WebEnt.getEnt(WebEnt.Type.TD);
            AbsEnt formSumbit = formSubmit(fo.getTytle(), fo.getImg());
            if (fo.getImgWidth() != null) {
                formSumbit.setAttribute(EnumAttrType.width, fo.getImgWidth().toString());
            }
            if (StringAdapter.NotNull(fo.getButtonCssClass())) {
                formSumbit.setCss(fo.getButtonCssClass());
            }
            td.addEnt(formSumbit);
            tr.addEnt(td);
        } 
        ae.addEnt(hiddenInput("submit", "submit"));
        return ae;
    }

    public FormOptionInterface getFormOption() {
        return FormOption.getInstance();
    }

    public AbsEnt href(Map<String, Object> params, HrefOptionInterface ho) throws Exception {
        return href(Parameter.getArray(params), ho);
    }

    public AbsEnt href(List<Parameter> params, HrefOptionInterface ho) throws Exception {
        //AbsEnt ae = null;
        AbsEnt ae = txt("");
        Boolean rend = true;
        if (ho.isRights() == true) {
            rend = true;
        }
        if (rend == true) {
            if (params == null) {
                params = new ArrayList();
            }
            ae = WebEnt.getEnt(WebEnt.Type.HREF);
            String href = "?";
            if (ho.getAction() != null) {
                href += "&action=" + WebEnt.escapeHtml(ho.getAction());
            }
            if (ho.getObject() != null) {
                href += "&object=" + WebEnt.escapeHtml(ho.getObject());
            }
            for (Parameter param : params) {
                href += "&" + param.getName() + "=" + WebEnt.escapeHtml(param.getValue());
            }
            if (ho.getRenderType() == RenderTypes.ajax) {
                href += "&renderType=ajax";
                ae.setJs(ho.getJshandler());
            } else if (ho.getRenderType() == RenderTypes.doc) {
                href += "&renderType=doc";
            }
            if (ho.getTytle() != null && !ho.getTytle().isEmpty()) {
                ae.setAttribute(EnumAttrType.title, ho.getTytle());
            }
            ae.setAttribute(EnumAttrType.href, StringAdapter.getString(baseLinkPath) + href);
            ae.setValue(ho.getName());
        } else if (ho.isShow()) {
            ae = WebEnt.getEnt(WebEnt.Type.TXT);
            ae.setValue(ho.getName());
        }
        return ae;
    }

    public HrefOptionInterface getHrefOption() {
        return HrefOption.getInstance();
    }

    public AbsEnt getImgByContent(String content, String width, String height, String style) throws Exception {
        return img("data:image/gif;base64," + content, width, height, style);
    }

    public AbsEnt notExcapeHtml(String html) throws Exception {
        AbsEnt ent = WebEnt.getEnt(WebEnt.Type.NOTESCAPETEXT);
        ent.setValue(html);
        return ent;
    }

    public AbsEnt imgByBytes(byte[] bytes, String width, String height, String style) throws Exception {
        String encodeContent = Base64.encodeBase64String(bytes);
        return getImgByContent(encodeContent, width, height, style);
    }

    public Map<String, Object> createComboMap(List<Row> query, String... names) throws Exception {
        Map<String, Object> map = new LinkedHashMap();
        List<String> nameList = Arrays.asList(names);
        List<String> namesDescription=new ArrayList();
        for(String st:nameList){
            if(nameList.indexOf(st)!=0){
                namesDescription.add(st);
            }
        }
        if (namesDescription != null && !namesDescription.isEmpty()) { 
            String id=nameList.get(0);
            if (query != null) {
                for (Row row : query) {
                    String name="";
                    List<Object> values= row.getValues(namesDescription);
                    map.put(StringAdapter.getString(row.get(id)), StringAdapter.getStringFromObjectList(values));
                }
            }
        }
        return map;
    }
    
    
    
    
}
