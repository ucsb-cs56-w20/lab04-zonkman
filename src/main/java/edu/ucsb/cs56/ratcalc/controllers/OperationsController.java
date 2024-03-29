package edu.ucsb.cs56.ratcalc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.ucsb.cs56.ratcalc.formbeans.RatCalcForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ucsb.cs56.ratcalc.model.Rational;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

interface GenericRationalOp {
    public Rational apply(Rational r1, Rational r2);
}

@Controller
public class OperationsController {

    private Logger logger = LoggerFactory.getLogger(OperationsController.class);

    /**
     * Check for denominator errors.  Sets error message(s) in the
     * form bean if either denominator is zero.
     * 
     * @param ratCalcForm form bean for rational number calculation
     * @return true if there are denominator errors
     */
    public boolean checkDenominatorErrors(RatCalcForm ratCalcForm) {
        String errorMsg = "Denominator should be non-zero";
        boolean result = false;

        if (ratCalcForm.getDenom1() == 0) {
            ratCalcForm.setFrac1Error(errorMsg);
            result = true;
        }

        if (ratCalcForm.getDenom2() == 0) {
            ratCalcForm.setFrac2Error(errorMsg);
            result = true;
        }

        return result;
    }

    public boolean checkDivideByZero(RatCalcForm ratCalcForm) {
        String errorMsg = "Cannot divide by zero";
        if (ratCalcForm.getNum2() == 0) {
            ratCalcForm.setFrac2Error(errorMsg);
            return true;
        }
        return false;
    }

    public void doGenericOperation(@Valid RatCalcForm ratCalcForm, BindingResult bindingResult, GenericRationalOp opFunction, boolean checkSecondTopNonzero) {
        if (!bindingResult.hasErrors() && !checkDenominatorErrors(ratCalcForm)) {
            if ((!checkSecondTopNonzero || !checkDivideByZero(ratCalcForm))) {
                Rational r1 = new Rational(ratCalcForm.getNum1(), ratCalcForm.getDenom1());
                Rational r2 = new Rational(ratCalcForm.getNum2(), ratCalcForm.getDenom2());
                Rational result = opFunction.apply(r1, r2);
                logger.info("r1=" + r1 + " r2=" + r2 + " result=" + result);
                ratCalcForm.setNumResult(result.getNumerator());
                ratCalcForm.setDenomResult(result.getDenominator());
            }
        }
    }

    @GetMapping("/add")
    public String getAdd(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("+");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/add";
    }

    @GetMapping("/add/results")
    public String getAddResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getAddResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("+");
        doGenericOperation(ratCalcForm, bindingResult, (x,y) -> Rational.sum(x,y), false);
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/add";
    }

    @GetMapping("/subtract")
    public String getSubtract(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("-");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/subtract";
    }

    @GetMapping("/subtract/results")
    public String getSubtractResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getSubtractResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("-");
        doGenericOperation(ratCalcForm, bindingResult, (x,y) -> Rational.difference(x,y), false);
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/subtract";
    }

    @GetMapping("/multiply")
    public String getMultiply(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("×");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/multiply";
    }

    @GetMapping("/multiply/results")
    public String getMultiplyResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getMultiplyResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("×");
        doGenericOperation(ratCalcForm, bindingResult, (x,y) -> Rational.product(x,y), false);
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/multiply";
    }

    @GetMapping("/divide")
    public String getDivide(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("÷");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/divide";
    }

    @GetMapping("/divide/results")
    public String getDivideResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getDivideResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("÷");
        doGenericOperation(ratCalcForm, bindingResult, (x,y) -> Rational.quotient(x,y), true);
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/divide";
    }

}
