package javax.topgun.constants.action;

import java.util.HashSet;

import javax.topgun.constants.BaseEnum;

public enum HttpMethodEnum implements BaseEnum {
    GET, POST, PUT, DELETE;

    /**
     * get every enum in HttpMethodEnum
     * 
     * @return
     */
    @Override
    public HashSet<String> getEnums() {
	HashSet<String> values = new HashSet<String>();

	for (HttpMethodEnum c : HttpMethodEnum.values()) {
	    values.add(c.name());
	}
	return values;
    }
}
