/**
 * @Package com.tuandai.ms.wallet.dto
 * @author 高磊
 * @date 2018年1月3日 下午4:36:06
 * @version V1.0
 */
package com.array;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author 高磊
 * @ClassName: BaseObject
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年1月3日 下午4:36:06
 */
public class BaseObject implements Serializable {

    private static final long serialVersionUID = -6080379380571758021L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
