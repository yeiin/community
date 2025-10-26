package com.ktb.community.controller.term;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/terms")
public class TermController {

    @GetMapping("")
    public String term(Model model) {
        model.addAttribute("serviceName", "아무 말 대잔치");
        model.addAttribute("companyName", "카카오테크 부트캠프 - 클라우드");
        model.addAttribute("tosVersion", "v1.0.0");
        model.addAttribute("effectiveDate", LocalDate.of(2025, 10, 23));
        model.addAttribute("contactEmail", "support@anytalk.kr");
        model.addAttribute("companyAddress", "경기도 성남시 대왕판교로 660 유스페이스1");
        return "term";
    }
}
