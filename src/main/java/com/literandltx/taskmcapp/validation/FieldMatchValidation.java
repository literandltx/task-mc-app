package com.literandltx.taskmcapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidation implements ConstraintValidator<FieldMatch, Object> {
    private String first;
    private String second;

    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    public boolean isValid(
            final Object value,
            final ConstraintValidatorContext context
    ) {
        final Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(first);
        final Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(second);

        if (fieldValue == null || fieldMatchValue == null) {
            throw new RuntimeException("Some of object's values are null.");
        }

        return fieldValue.equals(fieldMatchValue);
    }
}
