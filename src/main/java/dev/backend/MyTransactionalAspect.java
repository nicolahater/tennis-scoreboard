package dev.backend;

import dev.backend.service.FinishedMatchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;

@Aspect
@Component
//@ComponentScan("dev.backend")
public class MyTransactionalAspect {
    private final EntityManagerFactory emf;

    public MyTransactionalAspect(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Around("@annotation(MyTransactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint, MyTransactional instance) throws Throwable {
        System.out.println("////////////////////////////////////////////////////////////////");
        long start = System.currentTimeMillis();
//        FinishedMatchService s = (FinishedMatchService) instance;
//        System.out.println("instance: " + s);

        System.out.println("joinPoint.getTarget(): " + joinPoint.getTarget());
        Field f = joinPoint.getTarget().getClass().getDeclaredField("em");
        System.out.println("f: " + f);
        f.set(joinPoint.getTarget(), emf.createEntityManager());

//        Field[] f = joinPoint.getTarget().getClass().getDeclaredFields();
//        for(var ff : f) {
//            System.out.println("f: " + ff);
//        }

//        Field[] f = joinPoint.getThis(
//        for(var ff : f) {
//            System.out.println("f: " + ff);
//        }

//        https://dev.to/anh_trntun_4732cf3d299/dynamic-proxy-in-spring-a-comprehensive-guide-with-examples-and-demos-4kgd
        Object proceed;
        final EntityManager em = emf.createEntityManager();
        // Repository has entityManager field
//        em.
        try {
            em.getTransaction().begin();
            proceed = joinPoint.proceed();
            em.getTransaction().commit();

        } catch(Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();;
            }
            throw e;
        } finally {
            em.close();
        }

        long end = System.currentTimeMillis();
        long executionTime = end - start;
        System.out.println(joinPoint.getSignature() + "executed in " + executionTime);
        return proceed;
    }
}
