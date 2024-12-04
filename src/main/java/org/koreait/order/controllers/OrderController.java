package org.koreait.order.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

    // 주문 접수일, 주문 종료일로 주문목록에서 검색하는 기능
    @GetMapping("/list")
    public String list(@ModelAttribute @Valid RequestOrder form, Errors errors) {

        log.info(form.toString());
        // RequestOrder(sDate=2024-03-01, eDate=2024-11-18)

        return "order/list";
    }
}