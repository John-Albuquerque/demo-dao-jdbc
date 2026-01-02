package application;

import models.dao.DaoFactory;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);


        System.out.println("\n=== TEST 2: seller findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);


        System.out.println("\n=== TEST 3: seller findAll ====");
        list = sellerDao.findAll();
        list.forEach(System.out::println);


        System.out.println("\n=== TEST 4: seller insert ====");
        Seller newSeller = new Seller(null, "Greg", "greg@dev.com", LocalDate.now(), 4040.00, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());



        System.out.println("\n=== TEST 5: seller update ====");
        seller = sellerDao.findById(1);
        seller.setName("Marta Waine");
        sellerDao.update(seller);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 6: seller delete ====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.deletedById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
