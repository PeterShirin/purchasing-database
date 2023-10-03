package com.intellekta.databaseWebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JpaExample implements CommandLineRunner {
    private final SaleRepository saleRepository;

    @Autowired
    public JpaExample(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaExample.class, args);
    }

    public void run(String... args) {
        long rowCount = saleRepository.count();
        System.out.println("Количество записей: " + rowCount);

        int saleId = 1;
        Sale sale = saleRepository.findById(saleId).orElse(null);

        if (sale != null) {
            System.out.println("Информация о продаже с id " + sale.getId());
            System.out.println("Сумма: " + sale.getPrice());
            System.out.println("Дата поступления товара: " + sale.getPurchaseDate());
            System.out.println("Дата продажи: " + sale.getSaleDate());
            System.out.println("Идентификатор товара: " + sale.getProductId());
        } else {
            System.out.println("Продажа с id " + saleId + " не найдена.");
        }

        //addSale(1999.99, null, null, 4);

        List<Sale> sales = saleRepository.findByPriceGreaterThan(100);
        for (Sale s : sales) {
            System.out.println("Продажа с id " + s.getId() + " имеет сумму " + s.getPrice());
        }
    }

    public void addSale(double price, String purchaseDate, String saleDate, int productId) {
        Sale sale = new Sale();
        sale.setPrice(price);
        sale.setPurchaseDate(purchaseDate);
        sale.setSaleDate(saleDate);
        sale.setProductId(productId);

        Sale savedSale = saleRepository.save(sale);

        System.out.println("New sale record added successfully with id: " + savedSale.getId());
    }
}