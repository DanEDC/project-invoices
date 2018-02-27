package pl.coderstrust.db.impl.postgreSQL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import pl.coderstrust.db.postgreSQL;
import pl.coderstrust.model.Invoice;

@ConditionalOnProperty(name = "pl.coderstrust.db.impl.postgreSQLImpl", havingValue = "Hibernate")
public class Hibernate implements postgreSQL {

  @Autowired
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {

      return new Configuration().configure().buildSessionFactory();

    } catch (Throwable ex) {

      throw new ExceptionInInitializerError(ex);
    }
  }

  private static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
  public static void shutdown() {

    getSessionFactory().close();
  }

  @Override
  public <S extends Invoice> S save(S s) {
    return null;
  }

  @Override
  public <S extends Invoice> Iterable<S> save(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Invoice findOne(Long aLong) {
    return null;
  }

  @Override
  public boolean exists(Long aLong) {
    return false;
  }

  @Override
  public Iterable<Invoice> findAll() {
    return null;
  }

  @Override
  public Iterable<Invoice> findAll(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void delete(Long aLong) {

  }

  @Override
  public void delete(Invoice invoice) {

  }

  @Override
  public void delete(Iterable<? extends Invoice> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
