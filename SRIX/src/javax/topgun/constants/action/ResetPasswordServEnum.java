package javax.topgun.constants.action;

import java.util.HashSet;

import javax.topgun.constants.BaseEnum;

public enum ResetPasswordServEnum implements BaseEnum {
    retrieveUser, selectUser;

    /**
     * get every enum in resetPasswordActionEnum
     * 
     * @return
     */
    @Override
    public HashSet<String> getEnums() {
	HashSet<String> values = new HashSet<String>();

	for (ResetPasswordServEnum c : ResetPasswordServEnum.values()) {
	    values.add(c.name());
	}
	return values;
    }
}
