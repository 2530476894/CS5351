package com.se.scrumflow.utils;

import com.se.scrumflow.common.convention.exception.ServiceException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;

public class GeneralOperations {

    /**
     * 构建通用查询或更新对象
     *
     * @param param      包含条件的对象
     * @param resultType Query 或 Update 类型
     * @return Query 或 Update 对象
     */
    public static <T> T buildQueryOrUpdate(Object param, Class<T> resultType) {
        T result;
        try {
            result = resultType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ServiceException("服务端异常");
        }
        Field[] fields = param.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(param);
                if (value != null) {
                    String fieldName = field.getName();
                    if (result instanceof Query) {
                        if (fieldName.equals("pageNumber") || fieldName.equals("pageSize")) {
                            continue;
                        }
                        ((Query) result).addCriteria(Criteria.where(fieldName).is(value));
                    } else if (result instanceof Update) {
                        ((Update) result).set(fieldName, value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
