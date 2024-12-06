package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.projection.CashBackWalletProjection;
import com.telecom.telecom.entities.Cashback;
import com.telecom.telecom.repositories.*;
import com.telecom.telecom.utils.HelperUtils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/api/customer")
public class CustomerAccountController {
    @Autowired
    private ViewsRepository viewsRepository;

    @Autowired
    private FunctionsRepository functionsRepository;

    @Autowired
    private ProceduresRepository proceduresRepository;

    @Autowired
    private HelperUtils helperUtils;

    private ResponseEntity<?> validateParams(Map<String, String> params, String... requiredKeys) {
        for (String key : requiredKeys) {
            if (!params.containsKey(key) || Strings.isBlank(params.get(key))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", String.format("'%s' is required and cannot be empty!", key)));
            }
        }
        return null;

    }

    private ResponseEntity<?> validateMobileNumber(String mobileNum) {
        if (mobileNum == null || mobileNum.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }
        return null;
    }


    @GetMapping("/servicePlans")
    public ResponseEntity<?> getAllServicePlans() {
        return ResponseEntity.ok(viewsRepository.getServicePlans());
    }

    @Transactional
    @PostMapping("/consumption")
    public ResponseEntity<?> getPlanConsumption(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "startDate", "endDate", "planName");
        if (validation != null) return validation;

        String startDate = requestParams.get("startDate");
        String endDate = requestParams.get("endDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate formattedStartDate = HelperUtils.toDateFormat(startDate, formatter);
        LocalDate formattedEndDate = HelperUtils.toDateFormat(endDate, formatter);

        if (formattedStartDate == null || formattedEndDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid date format! Use 'yyyy-MM-dd'."));
        }

        return ResponseEntity.ok(functionsRepository.getConsumption(requestParams.get("planName"), formattedStartDate, formattedEndDate));
    }

    @Transactional
    @PostMapping("/unsubscribed")
    public ResponseEntity<?> getUnsubscribedPlans(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(proceduresRepository.unsubscribedPlans(mobileNum));
    }

    @Transactional
    @PostMapping("/monthUsage")
    public ResponseEntity<?> getMonthUsage(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(helperUtils.flattenUsageProjections(functionsRepository.getUsagePlanCurrentMonth(mobileNum)));
    }

    @Transactional
    @PostMapping("/cashbacks")
    public ResponseEntity<?> getCashbacks(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "nId","mobileNum");
        if (validation != null) return validation;

        Integer nId = HelperUtils.toInteger(requestParams.get("nId"));
        String mobileNum = requestParams.get("mobileNum");
        if (nId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National ID format!"));
        }

        if (!helperUtils.validateMobileNumberAndNId(mobileNum, nId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile number and National ID mismatch!"));
        }

        List<CashBackWalletProjection> cashbacks = functionsRepository.getCashbackWalletCustomer(nId);
        return ResponseEntity.ok(cashbacks);
    }

    @Transactional
    @GetMapping("/all-benefits")
    public ResponseEntity<?> getAllBenefits() {
        return ResponseEntity.ok(viewsRepository.getAllBenefits());
    }



    @Transactional
    @PostMapping("/unresolved-tickets")
    public ResponseEntity<?> getUnresolvedTickets(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "nId", "mobileNum");
        if (validation != null) return validation;

        Integer nId = HelperUtils.toInteger(requestParams.get("nId"));
        if (nId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National ID format!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        if (!helperUtils.validateMobileNumberAndNId(mobileNum, nId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile number and National ID mismatch!"));
        }

        List<Integer> tickets = proceduresRepository.getTicketAccountCustomers(nId);
        return ResponseEntity.ok(tickets == null || tickets.isEmpty() ? 0 : tickets.get(0));
    }

    @Transactional
    @PostMapping("/highest-voucher-value")
    public ResponseEntity<?> getHighestVoucherValue(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        List<Integer> topVouchers = proceduresRepository.getAccountHighestVoucher(mobileNum);
        return ResponseEntity.ok(topVouchers == null || topVouchers.isEmpty() ? -1 : topVouchers.get(0));
    }

    @Transactional
    @PostMapping("/remaining-plan-amount")
    public ResponseEntity<?> getRemainingPlanAmount(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "planName");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(functionsRepository.getRemainingPlanAmount(mobileNum, requestParams.get("planName")));
    }

    @Transactional
    @PostMapping("/extra-plan-amount")
    public ResponseEntity<?> getExtraPlanAmount(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "planName");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(functionsRepository.getExtraPlanAmount(mobileNum, requestParams.get("planName")));
    }

    @Transactional
    @PostMapping("/top-successful-payments")
    public ResponseEntity<?> getTopSuccessfulPayments(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(proceduresRepository.topSuccessfulPayments(mobileNum));
    }

    @Transactional
    @GetMapping("/all-shops")
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(viewsRepository.getAllShops());
    }

    @Transactional
    @PostMapping("/last-5months-plans")
    public ResponseEntity<?> getLast5MSubscribedPlans(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        validation = validateMobileNumber(mobileNum);
        if (validation != null) return validation;

        return ResponseEntity.ok(functionsRepository.getSubscribedPlans5Months(mobileNum));
    }

    @Transactional
    @PostMapping("/renew-subscription")
    public ResponseEntity<?> renewSubscription(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "amount", "planID", "paymentMethod");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        String paymentMethod = requestParams.get("paymentMethod");
        BigDecimal amount = HelperUtils.toBigDecimal(requestParams.get("amount"));
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return HelperUtils.badRequest("Amount must be a positive number!");
        }

        Integer planID = HelperUtils.toInteger(requestParams.get("planID"));
        if (planID == null) {
            return HelperUtils.badRequest("Plan ID must be a valid number!");
        }

        ResponseEntity<?> res = validateMobileNumber(mobileNum);
        if (res != null) return res;

        proceduresRepository.initiatePlanPayment(mobileNum, amount, paymentMethod, planID);
        return HelperUtils.success("Initiated Plan Payment Successfully");
    }

    @Transactional
    @PostMapping("/get-cashback-amount")
    public ResponseEntity<?> getCashbackAmount(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "planID", "benefitID");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        Integer planID = HelperUtils.toInteger(requestParams.get("planID"));
        Integer benefitID = HelperUtils.toInteger(requestParams.get("benefitID"));
        if (planID == null || benefitID == null) {
            return HelperUtils.badRequest("Plan ID and Benefit ID must be valid numbers!");
        }

        ResponseEntity<?> res = validateMobileNumber(mobileNum);
        if (res != null) return res;

        proceduresRepository.paymentWalletCashback(mobileNum, planID, benefitID);
        return HelperUtils.success("Cashback Updated Successfully");
    }

    @Transactional
    @PostMapping("/balance-recharge")
    public ResponseEntity<?> initiateBalancePayment(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "amount", "paymentMethod");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        String paymentMethod = requestParams.get("paymentMethod");
        BigDecimal amount = HelperUtils.toBigDecimal(requestParams.get("amount"));
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return HelperUtils.badRequest("Amount must be greater than zero!");
        }

        ResponseEntity<?> res = validateMobileNumber(mobileNum);
        if (res != null) return res;

        try {
            proceduresRepository.initiateBalancePayment(mobileNum, amount, paymentMethod);
            return HelperUtils.success("Balance Successfully Recharged!");
        } catch (Exception e) {
            return HelperUtils.badRequest("An error occurred while processing the payment!");
        }
    }

    @Transactional
    @PostMapping("/redeem-voucher")
    public ResponseEntity<?> redeemVoucherID(@RequestBody Map<String, String> requestParams) {
        ResponseEntity<?> validation = validateParams(requestParams, "mobileNum", "voucherId");
        if (validation != null) return validation;

        String mobileNum = requestParams.get("mobileNum");
        Integer voucherID = HelperUtils.toInteger(requestParams.get("voucherId"));
        if (voucherID == null) {
            return HelperUtils.badRequest("Voucher ID must be a valid number!");
        }
        ResponseEntity<?> eres = validateMobileNumber(mobileNum);
        if (eres != null) return eres;

        List<Integer> res = proceduresRepository.redeemVoucherPoints(mobileNum, voucherID);
        if (res.isEmpty()) return HelperUtils.badRequest("Voucher Redeem Failed");
        if (res.get(0) != 1) return HelperUtils.badRequest("Not Enough Points for Redeem");

        return HelperUtils.success("Voucher Redeemed Successfully");
    }


}

