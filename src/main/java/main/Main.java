package main;

import entity.Employee;
import entity.EmployeeName;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("myPU");
            EntityManager em = null;

            // Populating data
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();

                em.persist(new Employee(new EmployeeName("Scott", "Vogel")));
                em.persist(new Employee(new EmployeeName("Nick", "Jett")));
                em.persist(new Employee(new EmployeeName("Martin", "Stewart")));
                em.persist(new Employee(new EmployeeName("Jordan", "Posner")));
                em.persist(new Employee(new EmployeeName("David", "Wood")));

                em.getTransaction().commit();
            } finally { if (em != null) { em.close(); } }

            System.out.println("<<< Populating done >>>");

            List<EmployeeName> names = new ArrayList<>();
            names.add(new EmployeeName("Scott", "Vogel"));
            names.add(new EmployeeName("Nick", "Jett"));

            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                final List<Employee> employeeNames
                        = em.createQuery("SELECT e FROM Employee e WHERE e.employeeName IN :employeeNames", Employee.class)
                        .setParameter("employeeNames", names)
                        .getResultList();
                em.getTransaction().commit();
                System.out.println(employeeNames);
            } finally { if (em != null) { em.close(); } }
        } finally { if (emf != null) { emf.close(); } }
    }
}
