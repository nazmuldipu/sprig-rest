//package com.unololtd.nazmul.springrest.data;
//
//import com.unololtd.nazmul.springrest.entity.Employee;
//import com.unololtd.nazmul.springrest.entity.Order;
//import com.unololtd.nazmul.springrest.entity.Status;
//import com.unololtd.nazmul.springrest.repository.EmployeeRepository;
//import com.unololtd.nazmul.springrest.repository.OrderRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//    @Bean
//    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
//
//        return args -> {
//            employeeRepository.save(new Employee("Bilbo Baggins", "burglar"));
//            employeeRepository.save(new Employee("Frodo Baggins", "thief"));
//
//            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
//
//
//            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
//            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
//
//            orderRepository.findAll().forEach(order -> {
//                log.info("Preloaded " + order);
//            });
//
//        };
//    }
//
//}
