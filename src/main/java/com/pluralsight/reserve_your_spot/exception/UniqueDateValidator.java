package com.pluralsight.reserve_your_spot.exception;

import com.pluralsight.reserve_your_spot.model.Reservation;
import com.pluralsight.reserve_your_spot.repository.ReservartionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class UniqueDateValidator implements ConstraintValidator<UniqueDate, Date>{

    @Autowired
    private ReservartionRepository reservartionRepository;

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

        Reservation inDB = reservartionRepository.findByDate(value);
        if (inDB == null){
            return true;
        }
        return false;
    }
}
