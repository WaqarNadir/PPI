package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    @GetMapping("/ConfirmOrder")
    public String ConfirmOrder(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ConfirmOrder";
    }
    @GetMapping("/DeptOperation")
    public String DeptOperation(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "DeptOperation";
    }
    @GetMapping("/LossReason")
    public String LossReason(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "LossReason";
    }
    @GetMapping("/NewOrder")
    public String NewOrder(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "NewOrder";
    }
    @GetMapping("/NewProduct")
    public String NewProduct(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "NewProduct";
    }
    @GetMapping("/ViewAllDept")
    public String ViewAllDept(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewAllDept";
    }

    @GetMapping("/ViewAllLossType")
    public String ViewAllLossType(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewAllLossType";
    }
    @GetMapping("/ViewAllOperations")
    public String ViewAllOperations(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewAllOperations";
    }
    @GetMapping("/ViewProducts")
    public String ViewProducts(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewProducts";
    }
    @GetMapping("/ViewRouting")
    public String ViewRouting(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewRouting";
    }
    @GetMapping("/ViewWorkType")
    public String ViewWorkType(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "ViewWorkType";
    }
    @GetMapping("/WorkCenter")
    public String WorkCenter(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "WorkCenter";
    }
    @GetMapping("/worktypes")
    public String WorkType(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "WorkType";
    }
    
    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "DeptOperation";
    }
    @GetMapping("/template")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "template";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "template";
    }

}