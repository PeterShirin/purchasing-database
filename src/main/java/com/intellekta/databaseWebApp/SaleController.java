package com.intellekta.databaseWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SaleController {
    private final SaleRepository saleRepository;
    private String salesmanName;

    public SaleController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/sales")
    public String startSales(@RequestParam("salesmanName") String salesmanName) {
        this.salesmanName = salesmanName;
        return "redirect:/sales";
    }

    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("salesmanName", salesmanName);
        model.addAttribute("sales", saleRepository.findAll());
        return "sales";
    }

    @PostMapping("/sales/add")
    public String addSale(@RequestParam("price") double price,
                          @RequestParam("purchaseDate") String purchaseDate,
                          @RequestParam("saleDate") String saleDate,
                          @RequestParam("productId") int productId) {
        Sale sale = new Sale();
        sale.setPrice(price);
        sale.setPurchaseDate(purchaseDate);
        sale.setSaleDate(saleDate);
        sale.setProductId(productId);
        saleRepository.save(sale);
        return "redirect:/sales";
    }
}
