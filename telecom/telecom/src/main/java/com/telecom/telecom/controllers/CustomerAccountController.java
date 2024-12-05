package com.telecom.telecom.controllers;

import com.telecom.telecom.mappers.DtoEntityMapper;
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
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    CustomerProfileRepository customerProfileRepository;

    @Autowired
    DtoEntityMapper dtoEntityMapper;

    @Autowired
    private ViewsRepository viewsRepository;
    
    @Autowired
    private FunctionsRepository functionsRepository;
    
    @Autowired
    private ProceduresRepository proceduresRepository;

    @Autowired
    private HelperUtils helperUtils;

    @GetMapping("/servicePlans")
    public ResponseEntity<?> getAllServicePlans(){return ResponseEntity.ok(viewsRepository.getServicePlans());}

    @Transactional
    @PostMapping("/consumption")
    public ResponseEntity<?> getPlanConsumption(@RequestBody Map<String, String> requestParams)
    {

        if(requestParams.isEmpty() || !requestParams.containsKey("startDate") || !requestParams.containsKey("planName") || !requestParams.containsKey("endDate")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        
        String planName = requestParams.get("planName");
        String startDate = requestParams.get("startDate");
        String endDate = requestParams.get("endDate");

        
        if(Strings.isBlank(planName) || Strings.isBlank(startDate) || Strings.isBlank(endDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Plan Name, Start Date and End Date cannot be empty!"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedstartDate = HelperUtils.toDateFormat(startDate, formatter);
        LocalDate formattedendDate = HelperUtils.toDateFormat(endDate, formatter);

        if(Objects.isNull(formattedstartDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Start Date Format!"));
        }

        if(Objects.isNull(formattedendDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid End Date Format!"));
        }

        return ResponseEntity.ok(functionsRepository.getConsumption(planName, formattedstartDate, formattedendDate));
        
    }
    
    @Transactional
    @PostMapping("/unsubscribed")
    public ResponseEntity<?> getUnsubscribedPlans(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(proceduresRepository.unsubscribedPlans(mobileNum));
    }

    @Transactional
    @PostMapping("/monthUsage") 
    public ResponseEntity<?> getMonthUsage(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(HelperUtils.flattenUsageProjections(functionsRepository.getUsagePlanCurrentMonth(mobileNum)));
    }

    @Transactional
    @PostMapping("/cashbacks")
    public ResponseEntity<?> getCashbacks(@RequestBody Map<String, String> requestParams)
    {
        // || !requestParams.containsKey("mobileNum")
        if(requestParams.isEmpty() || !requestParams.containsKey("nId"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String nId = requestParams.get("nId");

        Integer nIdInteger = HelperUtils.toInteger(nId);
        if(Objects.isNull(nIdInteger)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National Id Format!"));
        }
        Integer nCachBacks = functionsRepository.getCashbackWalletCustomer(nIdInteger);
        return ResponseEntity.ok(nCachBacks == null ? 0 : nCachBacks);
    }

    @Transactional
    @GetMapping("/all-benefits")
    public ResponseEntity<?> getAllBenefits() {
        return ResponseEntity.ok(viewsRepository.getAllBenefits());
    }


    @Transactional
    @PostMapping("/unresolved")
    public ResponseEntity<?> getUnresolvedTickets(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("nId") || !requestParams.containsKey("planName") || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String nId = requestParams.get("nId");
        System.out.println(nId);
        Integer nIdInteger = HelperUtils.toInteger(nId);
        if(Objects.isNull(nIdInteger)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National Id Format!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(proceduresRepository.getTicketAccountCustomers(nIdInteger));

    }

    @Transactional
    @PostMapping("/highest-voucher-value")
    public ResponseEntity<?> getHighestVoucherValue(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(proceduresRepository.getAccountHighestVoucher(mobileNum));
    }


    @Transactional
    @PostMapping("/remaining-plan-amount")
    public ResponseEntity<?> getRemainingPlanAmount(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planName") )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        String planName = requestParams.get("planName");

        return ResponseEntity.ok(functionsRepository.getRemainingPlanAmount(mobileNum, planName));

    }

    @Transactional
    @PostMapping("/extra-plan-amount")
    public ResponseEntity<?> getExtraPlanAmount(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planName") )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        String planName = requestParams.get("planName");

        return ResponseEntity.ok(functionsRepository.getExtraPlanAmount(mobileNum, planName));

    }

    @Transactional
    @PostMapping("/top-successful-payments")
    public ResponseEntity<?> getTopSuccessfulPayments(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }


        return ResponseEntity.ok(proceduresRepository.topSuccessfulPayments(mobileNum));

    }

    @Transactional
    @GetMapping("/all-shops")
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(viewsRepository.getAllShops());
    }

    @Transactional
    @PostMapping("/last-5months-plans")
    public ResponseEntity<?> getLast5MSubscribedPlans (@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(functionsRepository.getSubscribedPlans5Months(mobileNum));
    }

    @Transactional
    @PostMapping("/renew-subscription")
    public ResponseEntity<?> renewSubscription(@RequestBody Map<String, String> requestParams){
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("amount")|| !requestParams.containsKey("planID") || !requestParams.containsKey("paymentMethod"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        String amountb4 = requestParams.get("amount");
        String planIDb4 = requestParams.get("planID");
        String paymentMethod = requestParams.get("paymentMethod");

        Integer amountb5 = HelperUtils.toInteger(amountb4);
        if(Objects.isNull(amountb5)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Amount is not in Correct Format"));
        }
        BigDecimal amount = new BigDecimal(amountb5);
        Integer planID = HelperUtils.toInteger(planIDb4);

        if(amountb5 == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Amount cannot be empty!"));
        }


        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        proceduresRepository.initiatePlanPayment(mobileNum,amount,paymentMethod,planID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Initiated Plan Successfully"));
    }

    @Transactional
    @PostMapping("/get-cashback-amount")
    public ResponseEntity<?> getCashbackAmount(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planID") || !requestParams.containsKey("benefitID"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        String planIDb4 = requestParams.get("planID");
        String benefitIDb4 = requestParams.get("benefitID");

        Integer planID = HelperUtils.toInteger(planIDb4);
        Integer benefitID = HelperUtils.toInteger(benefitIDb4);

        /* Parse Zeby to Integer then to String and call String.substring()*/



        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        proceduresRepository.paymentWalletCashback(mobileNum,planID,benefitID);
        return ResponseEntity.status(HttpStatus.OK)
            .body(Map.of("message", "Cashback Updated successfully"));
    }

    @Transactional
    @PostMapping("/balance-recharge")
    public ResponseEntity<?> initiateBalancePayment(@RequestBody Map<String, String> requestParams)
    {
        ;if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("amount") || !requestParams.containsKey("paymentMethod"))
    {
        ;return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Make sure to enter the required info!"));
    }

    ;String amountb4 = requestParams.get("amount")
    ;String planID = requestParams.get("planID")
    ;String paymentMethod = requestParams.get("paymentMethod")
    ;String mobileNum = requestParams.get("mobileNum")


    ;BigDecimal amount = new BigDecimal(Integer.parseInt(amountb4))
    ;if(amount.equals(BigDecimal.ZERO)){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Amount cannot be empty!"))

                ;
    }
        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        proceduresRepository.initiateBalancePayment(mobileNum,amount,planID);
        return ResponseEntity.status(HttpStatus.OK)
            .body(Map.of("message", "Balance Payed"));

    }

    @Transactional
    @PostMapping("/redeem-voucher")
    public ResponseEntity<?> redeemVoucherID(@RequestBody Map<String, String> requestParams)
    {
        ;if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("voucherID"))
    {
        ;return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
    }
    String mobileNum = requestParams.get("mobileNum");
    String voucherIDb4 = requestParams.get("voucherID");
    Integer voucherID = HelperUtils.toInteger(voucherIDb4);

    proceduresRepository.redeemVoucherPoints(mobileNum, voucherID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Voucher Redeemed"));
    }
}


/* //TODO: 
- crosscheck mobile num with nId 



*/


