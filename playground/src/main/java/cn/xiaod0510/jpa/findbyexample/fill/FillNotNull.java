package cn.xiaod0510.jpa.findbyexample.fill;

import cn.xiaod0510.jpa.findbyexample.BaseExample;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaod0510@gmail.com on 16-11-30 下午12:37.
 */
public class FillNotNull implements FillCondition {
    private static final Pattern pattern = Pattern.compile("([A-Z])");
    private static Map<String, BaseExample.PredicateType> predicateTypeMap = new HashMap<String, BaseExample.PredicateType>();

    static {
        for (BaseExample.PredicateType type : BaseExample.PredicateType.values()) {
            char[] nameChars = type.name().toCharArray();
            nameChars[0] = Character.toUpperCase(nameChars[0]);
            String name = new String(nameChars);
            predicateTypeMap.put(name, type);
        }
    }

    public void fill(BaseExample condiction, Object pojo) {
        if (pojo == null) return;

        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                //skip static field
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Object value = field.get(pojo);
                if (value == null) {
                    continue;
                }
                if (value instanceof String && ((String) value).length() == 0) {
                    continue;
                }
                String fieldName = field.getName();
                BaseExample.PredicateType predicateType = null;
                Matcher matcher = pattern.matcher(fieldName);

                String nameFound = null;
                while (matcher.find()) {
                    String typeName = fieldName.substring(matcher.start());
                    nameFound = fieldName.substring(0, matcher.start());
                    predicateType = predicateTypeMap.get(typeName);
                    if (predicateType != null) break;
                }
                //type首字母大写,若为空则默认为equals
                if (predicateType == null) {
                    predicateType = BaseExample.PredicateType.eq;
                    nameFound = fieldName;
                }

                condiction.add(nameFound, predicateType, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
