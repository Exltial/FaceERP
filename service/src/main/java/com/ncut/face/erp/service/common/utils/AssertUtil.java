package com.ncut.face.erp.service.common.utils;

import com.ncut.face.erp.service.common.exception.BaseException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class AssertUtil {
    public static void notNull(Object object, String errorMsg) {
        if (object == null) {
            throw new BaseException(errorMsg);
        }
    }

    public static void notEmpty(String str, String errorMsg) {
        if (StringUtils.isEmpty(str)) {
            throw new BaseException(errorMsg);
        }
    }

    public static void notEmpty(List list, String errorMsg) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException(errorMsg);
        }
    }
}
