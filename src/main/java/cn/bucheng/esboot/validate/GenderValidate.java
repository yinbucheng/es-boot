package cn.bucheng.esboot.validate;

import cn.bucheng.esboot.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ：yinchong
 * @create ：2019/7/26 13:49
 * @description：
 * @modified By：
 * @version:
 */
public class GenderValidate implements ConstraintValidator<Gender, String> {
    private Gender gender;

    @Override
    public void initialize(Gender constraintAnnotation) {
        gender = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null)
            return false;
        String[] values = gender.values();
        for (String temp : values) {
            if (temp.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
