package application;

import models.dao.DaoFactory;
import models.dao.DepartmentDao;
import models.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();


        System.out.println("\n=== TEST 1: Department findAll ====");
        List<Department> list = departmentDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n=== TEST 2: Department findById ====");
        Department dep = departmentDao.findById(4);
        System.out.println(dep);

        System.out.println("\n=== TEST 3: Department delete ====");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deletedById(id);
        System.out.println("Delete completed");

        System.out.println("\n=== TEST 4: department update ====");
        dep.setName("Games");
        dep.setId(2);
        departmentDao.update(dep);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 5: department insert ====");
        Department newDeparment = new Department(null, "Mercado");
        departmentDao.insert(newDeparment);
        System.out.println("Inserted! New id = " + newDeparment.getId());



        sc.close();
    }
}
