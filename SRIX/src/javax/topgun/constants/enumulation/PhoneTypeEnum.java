package javax.topgun.constants.enumulation;

import java.util.HashSet;
import javax.topgun.constants.BaseEnum;

public enum PhoneTypeEnum implements BaseEnum {
    HOME, WORK, MOBILE, PAGER, FAX, DSN;

    /**
     * get every enum in PhoneTypeEnum
     * 
     * @return
     */
    @Override
    public HashSet<String> getEnums() {
	HashSet<String> values = new HashSet<String>();

	for (PhoneTypeEnum c : PhoneTypeEnum.values()) {
	    values.add(c.name());
	}
	return values;
    }
}
